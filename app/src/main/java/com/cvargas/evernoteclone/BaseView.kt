package com.cvargas.evernoteclone

import io.reactivex.Scheduler

interface BaseView {
    fun getBackgroundSchedulers(): Scheduler?
    fun getForegroundSchedulers(): Scheduler?
}
