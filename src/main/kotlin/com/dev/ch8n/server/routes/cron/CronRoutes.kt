package com.dev.ch8n.server.routes.cron

import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import io.ktor.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

fun Route.getCronRoutes(releaseRepository: AndroidReleaseRepository) {
    route("/cron"){
        get("/android"){
            val remoteResult = GlobalScope.async {
                releaseRepository.getAndroidRemoteRelease()
            }

            val remoteAndroidRelease = remoteResult.await()

            releaseRepository.saveAndroidRelease(remoteAndroidRelease)


        }
    }

}