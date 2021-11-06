package com.cvargas.evernoteclone.add

import io.reactivex.Scheduler

interface Add {
    interface Presenter {
        fun getNoteDetail()
        fun createNote(title: String, body: String)
        fun onStopActivity()
        fun setNoteIsChanged()
        fun isMustBeCreateNote(): Boolean
    }

    interface View {
        fun displayNote(title: String, body: String)
        fun displayError(customMessage: String, throwable: Throwable? = null)
        fun returnToHome()
        fun getBackgroundSchedulers(): Scheduler?
        fun getForegroundSchedulers(): Scheduler?
    }
}