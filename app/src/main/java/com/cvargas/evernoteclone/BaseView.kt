package com.cvargas.evernoteclone

import io.reactivex.Scheduler

interface BaseView {
    fun getBackgroundScheduler(): Scheduler?
    fun getForegroundScheduler(): Scheduler?
}
