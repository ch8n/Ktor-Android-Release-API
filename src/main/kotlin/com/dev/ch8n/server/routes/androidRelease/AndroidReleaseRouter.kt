package com.dev.ch8n.server.routes.androidRelease


import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.utils.Result
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

fun Route.androidReleaseRoutes(releaseRepository: AndroidReleaseRepository) {
    route("/android") {
        getRelease(releaseRepository)
    }
}

private inline fun Route.getRelease(releaseRepository: AndroidReleaseRepository) {
    get("/{hashKey}") {
        val hashKeyParam = call.parameters.get("hashKey").toString()
        val resultDeferred = GlobalScope.async {

            releaseRepository.getAndroidLocalRelease(hashKeyParam)
        }
        val result = Result.build { resultDeferred.await() }
        when (result) {
            is Result.Error -> {
                println("============ call error =============")
                call.respond(status = HttpStatusCode.InternalServerError) {
                    result.error
                }
            }
            is Result.Success -> {
                println("============ call success =============")
                if (result.value != null) {
                    call.respond(status = HttpStatusCode.OK, message = result.value)
                } else {
                    call.respond(status = HttpStatusCode.InternalServerError, message = "")
                }
            }
        }
    }
}

