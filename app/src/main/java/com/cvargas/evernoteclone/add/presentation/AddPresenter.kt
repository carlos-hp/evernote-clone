package com.cvargas.evernoteclone.add.presentation

import android.util.Log
import com.cvargas.evernoteclone.add.Add
import com.cvargas.evernoteclone.model.Note
import com.cvargas.evernoteclone.model.RemoteDataSource
import io.reactivex.disposables.CompositeDisposable

class AddPresenter(
    private val noteId: Int? = null,
    private val view: Add.View,
    private val dataSource: RemoteDataSource,
) : Add.Presenter {

    private var noteIsChanged: Boolean = false

    private val compositeDisposable = CompositeDisposable()

    override fun onStopActivity() {
        compositeDisposable.clear()
    }

    override fun setNoteIsChanged() {
        noteIsChanged = true
    }

    override fun isMustBeCreateNote(): Boolean {
        return noteIsChanged && noteId == null
    }

    override fun getNoteDetail() {
        if (noteId == null)
            return

        val disposable = dataSource.getNote(noteId)
            .subscribeOn(view.getBackgroundSchedulers())
            .observeOn(view.getForegroundSchedulers())
            .subscribe(
                { note ->
                    view.displayNote(note.title ?: "", note.body ?: "")
                },
                { throwable ->
                    throwable.message?.let { Log.e(TAG, it) }
                    view.displayError("Erro ao carregar nota", throwable)
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun createNote(title: String, body: String) {
        // TODO Mudar as validações e a criação da nota para dentro do Observable
        if (title.isBlank()) {
            view.displayError("O título deve ser informado!")
            return
        }
        if (body.isBlank()) {
            view.displayError("O corpo deve ser informado!")
            return
        }
        val note = Note(title = title, body = body)
        val disposable = dataSource.createNote(note)
            .subscribeOn(view.getBackgroundSchedulers())
            .observeOn(view.getForegroundSchedulers())
            .subscribe(
                {
                    view.returnToHome()
                },
                { throwable ->
                    throwable.message?.let { Log.e(TAG, it) }
                    view.displayError("Erro ao criar nota", throwable)
                }
            )
        compositeDisposable.add(disposable)
    }

    companion object {
        const val TAG: String = "AddPresenter"
    }
}