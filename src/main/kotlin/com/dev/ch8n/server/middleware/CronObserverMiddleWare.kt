package com.dev.ch8n.server.middleware

import com.dev.ch8n.server.data.di.Injector
import com.dev.ch8n.server.services.cron.CronService
import com.dev.ch8n.server.services.logging.Logger
import io.ktor.application.*

fun Application.cronObservers() {
    val repository = Injector.androidReleaseRepository
    CronService.observeAndroidReleaseCron(repository)
    CronService.observeLogCleanCron()
}