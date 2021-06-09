package com.dev.ch8n.server.routes

import com.dev.ch8n.server.data.di.Injector
import com.dev.ch8n.server.routes.androidRelease.androidReleaseRoutes
import com.dev.ch8n.server.routes.cron.getCronRoutes
import com.dev.ch8n.server.routes.greet.greetingRoutes
import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerRouters() {
    routing {
        androidReleaseRoutes(Injector.androidReleaseRepository)
        getCronRoutes(Injector.androidReleaseRepository)
        greetingRoutes(Injector.greetingRepository)
    }
}