package com.cvargas.evernoteclone.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cvargas.evernoteclone.data.NoteRepository
import com.cvargas.evernoteclone.data.NoteRepositoryImpl
import com.cvargas.evernoteclone.data.model.Note

class FormViewModel(private val repository: NoteRepository = NoteRepositoryImpl()) : ViewModel() {
    private val _saved = MutableLiveData<Pair<Boolean, String>>()
    val saved: LiveData<Pair<Boolean, String>>
        get() = _saved

    var title: ObservableField<String> = ObservableField("")
    var body: ObservableField<String> = ObservableField("")

    private var noteId: Int? = null
    private var noteIsChanged: Boolean = false

    fun getNote(noteId: Int): LiveData<Note?> = repository.getNote(noteId)

    fun createNote() {
        if (title.get().isNullOrBlank()) {
            _saved.value = false to "O Titulo não pode ser em vazio!"
            return
        }
        if (body.get().isNullOrBlank()) {
            _saved.value = false to "O Corpo não pode ser em vazio!"
            return
        }
        val result = repository.createNote(
            Note(
                title = title.get(),
                body = body.get()
            )
        )
        _saved.value = true to ""
    }

    fun isMustBeCreateNote(): Boolean {
        return noteIsChanged && noteId == null
    }

    fun setNoteIsChanged() {
        noteIsChanged = true
    }

    fun setNoteId(noteId: Int) {
        this.noteId = noteId;
    }
}