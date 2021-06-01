package com.dev.ch8n.server.middleware

import io.ktor.application.*

fun Application.registerMiddleware() {
    acceptJson()
}