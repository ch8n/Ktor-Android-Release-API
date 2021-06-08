package com.dev.ch8n.server.data.local.database.sources

import com.dev.ch8n.server.data.local.database.config.DBConfig
import com.dev.ch8n.server.data.models.AndroidRelease
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.CoroutineClient

interface AndroidReleaseLocalService {
    suspend fun getAndroidRelease(): AndroidRelease?
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

    override suspend fun getAndroidRelease(): AndroidRelease? {
        val database = dbClient.getDatabase(DBConfig.DB_NAME_ANDROID_RELEASE)
        val collection = database.getCollection<AndroidRelease>()
        return collection.findOne()
    }
}