package com.dev.ch8n.server.routes

import com.dev.ch8n.server.data.repositories.GreetingRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

const val GET_PARAM_NAME = "name"

fun Route.greetingRoutes(greetingRepository: GreetingRepository) {
    route("/hello") {
        greet(greetingRepository)
        greetWithNameParam(greetingRepository)
    }
}

private inline fun Route.greetWithNameParam(greetingRepository: GreetingRepository) {
    get("{$GET_PARAM_NAME}") {
        val name = call.parameters.get(GET_PARAM_NAME)

        if (name.isNullOrEmpty()) {
            call.respondText(status = HttpStatusCode.BadRequest) {
                "Tell me your name Stranger!"
            }
            return@get
        }

        call.respondText(status = HttpStatusCode.OK) {
            greetingRepository.getGreeting(name)
        }
    }
}

private inline fun Route.greet(greetingRepository: GreetingRepository) {
    get {
        call.respondText(status = HttpStatusCode.OK) {
            greetingRepository.getGreeting()
        }
    }
}

