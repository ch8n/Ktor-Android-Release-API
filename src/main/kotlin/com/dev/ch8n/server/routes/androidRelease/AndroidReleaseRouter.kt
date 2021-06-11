package com.dev.ch8n.server.routes.androidRelease


import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.utils.Result
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

private const val GET_PARAM_ANDROID_HASHKEY = "hashKey"

fun Route.androidReleaseRoutes(releaseRepository: AndroidReleaseRepository) {
    route("/android") {
        getRelease(releaseRepository)
    }
}

private inline fun Route.getRelease(releaseRepository: AndroidReleaseRepository) {
    get("/$GET_PARAM_ANDROID_HASHKEY") {
        val hashKeyParam = call.parameters.get(GET_PARAM_ANDROID_HASHKEY).toString()
        val resultDeferred = GlobalScope.async {
            runCatching {
                releaseRepository.getAndroidLocalRelease(hashKeyParam)
            }
        }

        val result = resultDeferred.await()
        val value = result.getOrNull()

        when{
            result.isSuccess && value!=null ->{
                call.respond(status = HttpStatusCode.OK, message = value)
            }
            else ->{
                call.respond(status = HttpStatusCode.InternalServerError) {
                    result.exceptionOrNull()?.message ?: "Something went wrong"
                }
            }
        }
    }
}

