package com.dev.ch8n.server.routes.cron

import com.dev.ch8n.server.data.repositories.AndroidReleaseController
import com.dev.ch8n.server.services.databaseIndex.ReleaseIndex
import com.dev.ch8n.server.utils.Result
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

fun Route.cronRoutes(controller: AndroidReleaseController) {
    route("/cron") {
        cronAndroid(controller)
    }

}

private inline fun Route.cronAndroid(controller: AndroidReleaseController) {
    get("/android") {
        val keyResult = controller.getAndroidRemoteRelease()
        when(keyResult){
            is Result.Error -> {
                call.respond(status = HttpStatusCode.InternalServerError) { keyResult.error }
            }
            is Result.Success -> {
                ReleaseIndex.androidReleaseKey = keyResult.value
                call.respondText(status = HttpStatusCode.OK, text = keyResult.value)
            }
        }
    }
}