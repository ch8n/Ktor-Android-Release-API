package com.dev.ch8n.server.routes.androidRelease


import com.dev.ch8n.server.data.repositories.AndroidReleaseController
import com.dev.ch8n.server.services.databaseIndex.ReleaseIndex
import com.dev.ch8n.server.utils.Result
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

fun Route.androidReleaseRoutes(controller: AndroidReleaseController) {
    route("/android") {
        getRelease(controller)
    }
}

private inline fun Route.getRelease(controller: AndroidReleaseController) {
    get {
        val hashKey = ReleaseIndex.androidReleaseKey
        val result = controller.getAndroidLocalRelease(hashKey)
        when (result) {
            is Result.Error -> {
                call.respond(status = HttpStatusCode.InternalServerError) { result.error }
            }
            is Result.Success -> {
                call.respond(status = HttpStatusCode.OK, message = result.value)
            }
        }
    }
}

