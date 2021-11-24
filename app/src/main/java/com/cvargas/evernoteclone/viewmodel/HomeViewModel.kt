package com.cvargas.evernoteclone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cvargas.evernoteclone.data.NoteRepository
import com.cvargas.evernoteclone.data.NoteRepositoryImpl
import com.cvargas.evernoteclone.data.model.Note

class HomeViewModel(private val repository: NoteRepository = NoteRepositoryImpl()) : ViewModel() {
    fun getAllNotes(): LiveData<List<Note>?> = repository.getAllNotes()
}