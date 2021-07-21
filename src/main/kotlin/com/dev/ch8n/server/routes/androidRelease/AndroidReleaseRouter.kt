package com.dev.ch8n.server.routes.androidRelease


import com.dev.ch8n.server.data.repositories.AndroidReleaseController
import com.dev.ch8n.server.services.databaseIndex.ReleaseIndex
import com.dev.ch8n.server.services.logging.Log
import com.dev.ch8n.server.services.logging.Logger
import com.dev.ch8n.server.utils.Messages
import com.dev.ch8n.server.utils.NetworkResponse
import com.dev.ch8n.server.utils.Result
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.androidReleaseRoutes(controller: AndroidReleaseController) {
    val log = Logger.newInstance("Android-Route")
    route("/android") {
        getRelease(controller, log)
    }
}

private inline fun Route.getRelease(controller: AndroidReleaseController, log: Log) {
    get {
        val hashKey = ReleaseIndex.androidReleaseKey
        val result = controller.getAndroidLocalRelease(hashKey)
        when (result) {
            is Result.Error -> {
                log.e(result.error)
                val response = NetworkResponse<Unit>(
                    error = Messages.Failure.SOMETHING_WENT_WRONG,
                    data = Unit
                )
                call.respond(status = HttpStatusCode.InternalServerError) { response }
            }
            is Result.Success -> {
                val response = NetworkResponse(data = result.value)
                call.respond(status = HttpStatusCode.OK, message = response)
            }
        }
    }
}

