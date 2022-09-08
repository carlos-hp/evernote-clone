package com.cvargas.evernoteclone.presenter

import androidx.lifecycle.Observer
import com.cvargas.evernoteclone.Home
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.view.activities.FormActivity
import com.cvargas.evernoteclone.view.activities.HomeActivity
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

class HomePresenter(
    private var view: Home.View,
    private var interactor: Home.Interactor,
    private val router: Router
) : Home.Presenter, Home.InteractorOutput {
    override fun onStart() {
        interactor.loadNoteList().observe((view as HomeActivity), Observer { list ->
            if (list == null) {
                onQueryError()
                return@Observer
            }
            onQuerySuccess(list)
        })
    }

    override fun addNoteClick() {
        router.navigateTo(object : Screen {
            override val screenKey: String
                get() = FormActivity.TAG
        })
    }

    override fun onQuerySuccess(data: List<Note>) {
        view.displayNotes(data)
    }

    override fun onQueryError() {
        view.displayError("Erro ao carregar os dados.")
    }

}
