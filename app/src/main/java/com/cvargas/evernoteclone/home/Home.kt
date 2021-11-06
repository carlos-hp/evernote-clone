package com.cvargas.evernoteclone.home

import com.cvargas.evernoteclone.model.Note
import io.reactivex.Scheduler

interface Home {

    interface Presenter {
        fun loadNotes()

        fun stopActivity()

        fun onClickFabButtonListener()

        fun onClickNoteAdapter(note: Note)
    }

    interface View {
        fun callFormActivity(note: Note? = null)

        fun displayEmptyNotes()

        fun displayNotes(notes: List<Note>)

        fun displayError(customMessage: String, exception: Throwable)

        fun getBackgroundScheduler(): Scheduler?

        fun getForegroundScheduler(): Scheduler?
    }

}