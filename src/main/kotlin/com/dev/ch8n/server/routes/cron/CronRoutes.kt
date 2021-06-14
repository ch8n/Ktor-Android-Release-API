package com.dev.ch8n.server.routes.cron

import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.services.databaseIndex.ReleaseIndex
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

fun Route.getCronRoutes(releaseRepository: AndroidReleaseRepository) {
    route("/cron") {
        cronAndroid(releaseRepository)
    }

}

private inline fun Route.cronAndroid(releaseRepository: AndroidReleaseRepository) {
    get("/android") {
        val remoteResult = GlobalScope.async {
            releaseRepository.getAndroidRemoteRelease()
        }
        val remoteAndroidRelease = remoteResult.await()
        val hashKey = releaseRepository.saveAndroidRelease(remoteAndroidRelease)
        ReleaseIndex.androidReleaseKey = hashKey
        call.respondText(status = HttpStatusCode.OK, text = hashKey)
    }
}