package com.dev.ch8n.server.routes.androidRelease


import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.utils.Result
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun Route.androidReleaseRoutes(releaseRepository: AndroidReleaseRepository) {
    route("/android") {
        getRelease(releaseRepository)
    }
}

private inline fun Route.getRelease(releaseRepository: AndroidReleaseRepository) {
    get {
        val resultDeferred = GlobalScope.async {
            releaseRepository.getAndroidRelease()
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
                call.respond(status = HttpStatusCode.OK,message = result.value)
            }
        }
    }
}

