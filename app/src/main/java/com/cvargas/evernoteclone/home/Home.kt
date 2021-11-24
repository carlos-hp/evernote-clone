package com.cvargas.evernoteclone.home

import com.cvargas.evernoteclone.BaseView
import com.cvargas.evernoteclone.model.Note

interface Home {

    interface Presenter {
        fun loadNotes()

        fun stopActivity()

        fun onClickFabButtonListener()

        fun onClickNoteAdapter(note: Note)
    }

    interface View : BaseView {
        fun callFormActivity(note: Note? = null)

        fun displayEmptyNotes()

        fun displayNotes(notes: List<Note>)

        fun displayError(customMessage: String, exception: Throwable)
    }

}