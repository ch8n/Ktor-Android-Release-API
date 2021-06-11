package com.dev.ch8n.server.data.local.database.sources

import com.dev.ch8n.server.data.models.AndroidRelease
import org.kodein.db.DB


typealias DBClient = DB

interface AndroidReleaseLocalService {
    suspend fun getAndroidRelease(keyHash: String): AndroidRelease?
    suspend fun saveAndroidRelease(remoteAndroidRelease: AndroidRelease): String
}


class AndroidReleaseLocalSource(
    private val dbClient: DBClient
) : AndroidReleaseLocalService {

    override suspend fun getAndroidRelease(keyHash: String): AndroidRelease? {
        val key = dbClient.keyFromB64(AndroidRelease::class, keyHash);
        return dbClient.get(AndroidRelease::class, key)
    }

    override suspend fun saveAndroidRelease(remoteAndroidRelease: AndroidRelease): String {
        return dbClient.put(remoteAndroidRelease).toBase64()
    }
}