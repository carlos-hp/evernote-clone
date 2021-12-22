package com.cvargas.evernoteclone.data

import androidx.lifecycle.LiveData
import com.cvargas.evernoteclone.data.model.Note

interface NoteRepository {
    fun getAllNotes(): LiveData<List<Note>?>

    fun getNote(noteId: Int): LiveData<Note?>

    fun createNote(note: Note): LiveData<Note>
}