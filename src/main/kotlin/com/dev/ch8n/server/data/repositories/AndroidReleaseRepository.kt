package com.dev.ch8n.server.data.repositories

import com.dev.ch8n.server.data.models.AndroidRelease
import com.dev.ch8n.server.data.remote.api.source.android_release.AndroidReleaseService
import com.dev.ch8n.server.data.remote.api.source.android_release.toAndroidRelease
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidReleaseRepository(
    private val releaseService: AndroidReleaseService
) {
    suspend fun getAndroidRelease(): AndroidRelease = withContext(Dispatchers.IO) {
        val releaseDto = releaseService.getAndroidRelease()
        releaseDto.toAndroidRelease()
        throw IllegalAccessError("pagal pn")
    }
}