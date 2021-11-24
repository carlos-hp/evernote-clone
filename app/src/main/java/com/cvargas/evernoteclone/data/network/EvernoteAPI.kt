package com.cvargas.evernoteclone.data.network

import com.cvargas.evernoteclone.data.model.Note
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *
 * Setembro, 24 2019
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */

interface EvernoteAPI {

    @GET("/")
    fun listNotes(): Maybe<List<Note>>

    @GET("/{id}")
    fun getNote(@Path("id") id: Int): Single<Note>

    @POST("/create")
    fun createNote(@Body note: Note): Completable

}