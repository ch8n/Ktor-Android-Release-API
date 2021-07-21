package com.dev.ch8n.server.controller

import com.dev.ch8n.server.data.models.AndroidRelease
import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.services.logging.Log
import com.dev.ch8n.server.services.logging.Logger
import com.dev.ch8n.server.utils.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class AndroidReleaseController(
    private val repository: AndroidReleaseRepository
) : CoroutineScope {

    private val log: Log = Logger.newInstance(this::class)

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable -> log.e(throwable) }

    suspend fun getAndroidRemoteRelease(): Result<String, Exception> {
        return Result.build {
            val remoteAndroidRelease = repository.getAndroidRemoteRelease()
            val hashKey = repository.saveAndroidRelease(remoteAndroidRelease)
            hashKey
        }
    }

    suspend fun getAndroidLocalRelease(keyHash: String): Result<AndroidRelease, Exception> {
        return Result.build { repository.getAndroidLocalRelease(keyHash) ?: throw NullPointerException() }
    }

}