package com.cvargas.evernoteclone.home.presentation

import android.util.Log
import com.cvargas.evernoteclone.add.presentation.AddPresenter
import com.cvargas.evernoteclone.home.Home
import com.cvargas.evernoteclone.model.Note
import com.cvargas.evernoteclone.model.RemoteDataSource
import io.reactivex.disposables.CompositeDisposable

class HomePresenter(
    private val view: Home.View,
    private val dataSource: RemoteDataSource
) : Home.Presenter {
    private val compositeDisposable = CompositeDisposable()

    override fun loadNotes() {
        val disposable = dataSource.listNotes()
            .subscribeOn(view.getBackgroundScheduler())
            .observeOn(view.getForegroundScheduler())
            .subscribe(
                { notes ->
                    view.displayNotes(notes)
                },
                { throwable ->
                    throwable.message?.let { Log.e(AddPresenter.TAG, it) }
                    view.displayError("Erro ao carregar notas", throwable)
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun stopActivity() {
        compositeDisposable.clear()
    }

    override fun onClickFabButtonListener() {
        view.callFormActivity()
    }

    override fun onClickNoteAdapter(note: Note) {
        view.callFormActivity(note)
    }

    companion object {
        const val TAG: String = "HomePresenter"
    }
}