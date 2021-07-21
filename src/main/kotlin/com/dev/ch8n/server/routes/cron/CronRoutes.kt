package com.dev.ch8n.server.routes.cron

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

fun Route.cronRoutes(controller: AndroidReleaseController) {
    val log = Logger.newInstance("Android-Route")
    route("/cron") {
        cronAndroid(controller, log)
    }

}

private inline fun Route.cronAndroid(controller: AndroidReleaseController, log: Log) {
    get("/android") {
        val keyResult = controller.getAndroidRemoteRelease()
        when (keyResult) {
            is Result.Error -> {
                log.e(keyResult.error)
                val response = NetworkResponse<Unit>(
                    error = Messages.Failure.SOMETHING_WENT_WRONG,
                    data = Unit
                )
                call.respond(status = HttpStatusCode.InternalServerError) { response }
            }
            is Result.Success -> {
                ReleaseIndex.androidReleaseKey = keyResult.value
                val response = NetworkResponse(
                    data = keyResult.value
                )
                call.respond(status = HttpStatusCode.OK) { response }
            }
        }
    }
}