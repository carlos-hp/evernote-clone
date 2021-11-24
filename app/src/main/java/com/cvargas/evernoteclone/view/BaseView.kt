package com.cvargas.evernoteclone.view

import io.reactivex.Scheduler

interface BaseView {
    fun getBackgroundScheduler(): Scheduler?
    fun getForegroundScheduler(): Scheduler?
}
