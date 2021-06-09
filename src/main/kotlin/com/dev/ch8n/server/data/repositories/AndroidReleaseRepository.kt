package com.dev.ch8n.server.data.repositories

import com.dev.ch8n.server.data.local.database.sources.AndroidReleaseLocalService
import com.dev.ch8n.server.data.models.AndroidRelease
import com.dev.ch8n.server.data.remote.api.source.android_release.AndroidReleaseRemoteService
import com.dev.ch8n.server.data.remote.api.source.android_release.toAndroidRelease
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidReleaseRepository(
    private val releaseRemoteService: AndroidReleaseRemoteService,
    private val releaseLocalService: AndroidReleaseLocalService,

    ) {
    suspend fun getAndroidRemoteRelease(): AndroidRelease = withContext(Dispatchers.IO) {
        val releaseDto = releaseRemoteService.getAndroidRelease()
        releaseDto.toAndroidRelease()
    }

    suspend fun getAndroidLocalRelease(): AndroidRelease? = withContext(Dispatchers.IO) {
        releaseLocalService.getAndroidRelease()
    }

    suspend fun saveAndroidRelease(remoteAndroidRelease: AndroidRelease) = withContext(Dispatchers.IO){
        releaseLocalService.saveAndroidRelease(remoteAndroidRelease)
    }
}