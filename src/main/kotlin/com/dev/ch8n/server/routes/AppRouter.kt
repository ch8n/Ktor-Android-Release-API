package com.dev.ch8n.server.routes

import com.dev.ch8n.server.data.di.Injector
import com.dev.ch8n.server.routes.androidRelease.androidReleaseRoutes
import com.dev.ch8n.server.routes.cron.cronRoutes
import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerRouters() {
    routing {
        cronRoutes(Injector.androidReleaseCollection)
        androidReleaseRoutes(Injector.androidReleaseCollection)
    }
}