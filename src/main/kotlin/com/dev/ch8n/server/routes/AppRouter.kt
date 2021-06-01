package com.dev.ch8n.server.routes

import com.dev.ch8n.server.data.di.Injector
import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerRouters() {
    routing {
        greetingRoutes(Injector.greetingRepository)
    }
}