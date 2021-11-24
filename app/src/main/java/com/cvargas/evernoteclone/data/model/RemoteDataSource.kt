package com.cvargas.evernoteclone.data.model

import com.cvargas.evernoteclone.data.network.RetrofitClient
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class RemoteDataSource {

    fun listNotes(): Maybe<List<Note>> = RetrofitClient.evernoteApi.listNotes()

    fun getNote(noteId: Int): Single<Note> = RetrofitClient.evernoteApi.getNote(noteId)

    fun createNote(note: Note): Completable = RetrofitClient.evernoteApi.createNote(note)
}