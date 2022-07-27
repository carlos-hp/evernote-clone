package com.cvargas.evernoteclone.network

import com.cvargas.evernoteclone.model.Note
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EvernoteAPI {

    @GET("/")
    fun listNotes(): Maybe<List<Note>>

    @GET("/{id}")
    fun getNote(@Path("id") id: Int): Single<Note>

    @POST("/create")
    fun createNote(@Body note: Note): Completable

}