package com.dev.ch8n.server.data.local.database.config

import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object DBConfig {
    const val DB_NAME_ANDROID_RELEASE = "ch8n_server_android_release"

    val dbClient by lazy {
        KMongo.createClient().coroutine
    }

}