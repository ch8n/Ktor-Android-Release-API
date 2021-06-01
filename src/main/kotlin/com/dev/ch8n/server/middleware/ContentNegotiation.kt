package com.dev.ch8n.server.middleware

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*

fun Application.acceptJson() {
    install(ContentNegotiation) {
        json()
    }
}
