package com.dev.ch8n.server.data.local.database.sources

import com.dev.ch8n.server.data.local.database.config.DBConfig
import com.dev.ch8n.server.data.models.AndroidRelease
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.CoroutineClient

interface AndroidReleaseLocalService {
    suspend fun getAndroidRelease(): AndroidRelease?
    suspend fun saveAndroidRelease(remoteAndroidRelease: AndroidRelease)
}

fun main(){
    runBlocking {
        val result = AndroidReleaseLocalSource(DBConfig.dbClient).getAndroidRelease()
        assert(result == null)
    }
}

class AndroidReleaseLocalSource(
    private val dbClient: CoroutineClient
) : AndroidReleaseLocalService {

    private val database by lazy {
        dbClient.getDatabase(DBConfig.DB_NAME_ANDROID_RELEASE)
    }

    override suspend fun getAndroidRelease(): AndroidRelease? {
        val collection = database.getCollection<AndroidRelease>()
        return collection.findOne()
    }

    override suspend fun saveAndroidRelease(remoteAndroidRelease: AndroidRelease) {
        val collection = database.getCollection<AndroidRelease>()
        collection.insertOne(remoteAndroidRelease)
    }
}