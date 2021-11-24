package com.cvargas.evernoteclone.view.activities.add

import com.cvargas.evernoteclone.view.BaseView

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
        fun displayError(customMessage: String)
        fun returnToHome()
    }
}