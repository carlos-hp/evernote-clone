package com.cvargas.evernoteclone.add

import com.cvargas.evernoteclone.BaseView

interface Add {
    interface Presenter {
        fun getNoteDetail()
        fun createNote(title: String, body: String)
        fun onStopActivity()
        fun setNoteIsChanged()
        fun isMustBeCreateNote(): Boolean
    }

    interface View : BaseView {
        fun displayNote(title: String, body: String)
        fun displayError(customMessage: String, throwable: Throwable? = null)
        fun returnToHome()
    }
}