package com.dev.ch8n.server.data.repositories

import com.dev.ch8n.server.data.local.database.sources.AndroidReleaseLocalService
import com.dev.ch8n.server.data.models.AndroidRelease
import com.dev.ch8n.server.data.remote.api.source.android_release.AndroidReleaseRemoteService
import com.dev.ch8n.server.data.remote.api.source.android_release.toAndroidRelease
import com.dev.ch8n.server.services.logging.Log
import com.dev.ch8n.server.services.logging.Logger
import com.dev.ch8n.server.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AndroidReleaseRepository(
    private val releaseRemoteService: AndroidReleaseRemoteService,
    private val releaseLocalService: AndroidReleaseLocalService,
) {
    suspend fun getAndroidRemoteRelease(): AndroidRelease = withContext(Dispatchers.IO) {
        val releaseDto = releaseRemoteService.getAndroidRelease()
        releaseDto.toAndroidRelease()
    }

    suspend fun getAndroidLocalRelease(keyHash: String): AndroidRelease? = withContext(Dispatchers.IO) {
        releaseLocalService.getAndroidRelease(keyHash)
    }

    suspend fun saveAndroidRelease(remoteAndroidRelease: AndroidRelease): String = withContext(Dispatchers.IO) {
        releaseLocalService.saveAndroidRelease(remoteAndroidRelease)
    }
}

