package com.dev.ch8n.server.routes.androidRelease


import com.dev.ch8n.server.data.models.AndroidRelease
import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.utils.Result
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val GET_PARAM_NAME = "name"
private const val GET_PARAM_TIMES = "times"

fun Route.androidReleaseRoutes(releaseRepository: AndroidReleaseRepository) {
    route("/android") {
        getRelease(releaseRepository)
    }
}

private inline fun Route.getRelease(releaseRepository: AndroidReleaseRepository) {
    get {
        runBlocking {
            println("============ call started =============")
            val result = Result.build { releaseRepository.getAndroidRelease() }
            when (result) {
                is Result.Error -> {
                    println("============ call error =============")
                    call.respond(status = HttpStatusCode.InternalServerError) {
                        result.error
                    }
                }
                is Result.Success -> {
                    println("============ call success =============")
                    call.respond(status = HttpStatusCode.OK,message = result.value)
                }
            }
        }
    }
}

