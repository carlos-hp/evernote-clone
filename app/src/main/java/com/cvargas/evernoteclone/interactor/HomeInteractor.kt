package com.cvargas.evernoteclone.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.cvargas.evernoteclone.Home
import com.cvargas.evernoteclone.data.NoteRepositoryImpl
import com.cvargas.evernoteclone.data.model.Note

class HomeInteractor : Home.Interactor {
    private val noteList = MediatorLiveData<List<Note>?>()
    private val repository = NoteRepositoryImpl()

    init {
        getAllNotes()
    }

    override fun getAllNotes() {
        noteList.addSource(repository.getAllNotes()) { list ->
            noteList.postValue(list)
        }
    }

    override fun loadNoteList(): LiveData<List<Note>?> = noteList
}