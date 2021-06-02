package com.dev.ch8n.server.routes

import com.dev.ch8n.server.data.repositories.GreetingRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

private const val GET_PARAM_NAME = "name"
private const val GET_PARAM_TIMES = "times"

fun Route.greetingRoutes(greetingRepository: GreetingRepository) {
    route("/hello") {
        greet(greetingRepository)
        greetWithNameParam(greetingRepository)
        greetWithNameParamTimes(greetingRepository)
    }
}

private inline fun Route.greetWithNameParamTimes(greetingRepository: GreetingRepository) {
    get("{$GET_PARAM_NAME}/{$GET_PARAM_TIMES}") {
        val name = call.parameters.get(GET_PARAM_NAME)
        val times = call.parameters.get(GET_PARAM_TIMES)?.toIntOrNull()

        if (name.isNullOrEmpty()) {
            call.respondText(status = HttpStatusCode.BadRequest) {
                "Tell me your name Stranger!"
            }
            return@get
        }

        if (times == null) {
            call.respondText(status = HttpStatusCode.BadRequest) {
                "Tell me how many times $name!"
            }
            return@get
        }

        call.respondText(status = HttpStatusCode.OK) {
            val result = StringBuilder()
            repeat(times) {
                result.append(greetingRepository.getGreeting(name))
            }
            result.toString()
        }
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

