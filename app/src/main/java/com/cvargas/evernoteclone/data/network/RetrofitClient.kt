package com.cvargas.evernoteclone.data.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private const val EVERNOTE_BASE_URL = "https://myevernote.glitch.me/"

    val evernoteApi: EvernoteAPI by lazy {
        Retrofit.Builder()
            .baseUrl(EVERNOTE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(EvernoteAPI::class.java)
    }
}
