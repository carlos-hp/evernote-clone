package com.cvargas.evernoteclone.network

import android.os.Build
import androidx.core.os.BuildCompat
import com.cvargas.evernoteclone.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    val evernoteApi: EvernoteAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(EvernoteAPI::class.java)
    }
}
