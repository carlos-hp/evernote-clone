package com.cvargas.evernoteclone.view.activities.add.presentation

import com.cvargas.evernoteclone.view.activities.add.Add
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.data.model.RemoteDataSource
import io.reactivex.Observable
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
            .subscribeOn(view.getBackgroundScheduler())
            .observeOn(view.getForegroundScheduler())
            .subscribe(
                { note ->
                    view.displayNote(note.title ?: "", note.body ?: "")
                },
                { throwable ->
                    //throwable.message?.let { Log.e(TAG, it) }
                    view.displayError(throwable.message!!)
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun createNote(title: String, body: String) {
        val disposable = createNoteObservable(title, body)
            .flatMapCompletable { dataSource.createNote(it)  }
            .subscribeOn(view.getBackgroundScheduler())
            .observeOn(view.getForegroundScheduler())
            .subscribe(
                {
                    view.returnToHome()
                },
                { throwable ->
                    //throwable.message?.let { Log.e(TAG, it) }
                    view.displayError(throwable.message!!)
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun createNoteObservable(title: String, body: String): Observable<Note> {
        return Observable.create { emitter ->
            when {
                title.isBlank() -> emitter.onError(Exception("O título deve ser informado!"))
                body.isBlank() -> emitter.onError(Exception("O corpo deve ser informado!"))
                else -> {
                    emitter.onNext(Note(title = title, body = body))
                }
            }
            emitter.onComplete()
        }
    }

    companion object {
        const val TAG: String = "AddPresenter"
    }
}