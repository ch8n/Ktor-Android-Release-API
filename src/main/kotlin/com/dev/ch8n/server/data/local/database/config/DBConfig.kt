package com.dev.ch8n.server.data.local.database.config

import com.dev.ch8n.server.data.local.database.sources.DBClient
import com.dev.ch8n.server.data.models.AndroidRelease
import org.kodein.db.OpenPolicy
import org.kodein.db.impl.open
import org.kodein.db.orm.kotlinx.KotlinxSerializer

object DBConfig {
    private const val DatabasePath = "./"

    val dbClient by lazy {
        DBClient.open(DatabasePath, OpenPolicy.Create, KotlinxSerializer {
            +AndroidRelease.serializer()
        })
    }

}