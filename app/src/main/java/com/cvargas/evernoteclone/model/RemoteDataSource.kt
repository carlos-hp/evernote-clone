package com.cvargas.evernoteclone.model

import com.cvargas.evernoteclone.network.RetrofitClient
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 *
 * Setembro, 24 2019
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
class RemoteDataSource {

    fun listNotes(): Maybe<List<Note>> = RetrofitClient.evernoteApi.listNotes()

    fun getNote(noteId: Int): Single<Note> = RetrofitClient.evernoteApi.getNote(noteId)

    fun createNote(note: Note): Completable = RetrofitClient.evernoteApi.createNote(note)
}