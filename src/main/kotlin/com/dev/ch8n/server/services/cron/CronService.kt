package com.dev.ch8n.server.services.cron

import com.dev.ch8n.server.AppConfig
import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.services.databaseIndex.ReleaseIndex
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

object CronService {

    private val refreshAndroidReleaseCron = flow {
        while (true) {
            delay(AppConfig.CRON_REFRESH_TIME)
            emit(true)
        }
    }

    fun observeAndroidReleaseCron(releaseRepository: AndroidReleaseRepository) {
        GlobalScope.launch(Dispatchers.IO) {
            refreshAndroidReleaseCron.collect { refreshAndroidRelease ->
                if (refreshAndroidRelease) {
                    println("Cron job executed: observeAndroidReleaseCron")
                    val remoteResult = GlobalScope.async {
                        releaseRepository.getAndroidRemoteRelease()
                    }
                    val remoteAndroidRelease = remoteResult.await()
                    ReleaseIndex.androidReleaseKey = releaseRepository.saveAndroidRelease(remoteAndroidRelease)
                    println("Cron job completed: ${ReleaseIndex.androidReleaseKey}")
                }

            }
        }
    }
}