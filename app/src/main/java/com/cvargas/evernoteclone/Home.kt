package com.cvargas.evernoteclone

import androidx.lifecycle.LiveData
import com.cvargas.evernoteclone.data.model.Note
import com.github.terrakok.cicerone.Screen

interface Home {
    interface View {
        fun displayNotes(notes: List<Note>)
        fun displayError(message: String)
    }

    interface Presenter {
        fun onStart()
        fun addNoteClick()
    }

    interface Interactor {
        fun getAllNotes()
        fun loadNoteList(): LiveData<List<Note>?>
    }

    interface InteractorOutput {
        fun onQuerySuccess(data: List<Note>)
        fun onQueryError()
    }
}