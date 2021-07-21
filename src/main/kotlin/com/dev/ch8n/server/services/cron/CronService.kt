package com.dev.ch8n.server.services.cron

import com.dev.ch8n.server.AppConfig
import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.services.databaseIndex.ReleaseIndex
import com.dev.ch8n.server.services.logging.Log
import com.dev.ch8n.server.services.logging.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import org.kodein.log.toLocalString
import kotlin.coroutines.CoroutineContext

object CronService : CoroutineScope {

    private val log: Log = Logger.newInstance(this::class)

    private val onceDayTrigger = flow {
        while (true) {
            delay(AppConfig.CRON_REFRESH_TIME)
            val cronTime = Clock.System.now().toLocalString()
            log.d("Refresh Cron emitted $cronTime")
            emit(cronTime)
        }
    }

    fun observeLogCleanCron() = launch {
        onceDayTrigger.collect { cronTime ->
            log.d("limiting logs from cron $cronTime")
            Logger.instance.limitLogFiles()
            log.d("limiting logs Complete")
        }
    }


    fun observeAndroidReleaseCron(releaseRepository: AndroidReleaseRepository) = launch {
        onceDayTrigger.collect { cronTime ->
            log.d("Refreshing Android-Release-Data from cron $cronTime")
            val remoteResult = withContext(Dispatchers.IO) { releaseRepository.getAndroidRemoteRelease() }
            ReleaseIndex.androidReleaseKey = releaseRepository.saveAndroidRelease(remoteResult)
            log.d("Refreshing Complete Android-Release-Data from cron ${ReleaseIndex.androidReleaseKey}")
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable -> log.e(throwable) }
}