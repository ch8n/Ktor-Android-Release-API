package com.dev.ch8n.server.data.remote.api.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*


object ApiConfig {

    val httpClient by lazy {
        HttpClient(CIO) {
            configHttpLogging()
            configEngine()
        }
    }

    object Url {
        const val GET_ANDROID_RELEASE_RSS = "https://developer.android.com/feeds/androidx-release-notes.xml"
    }
}

private inline fun HttpClientConfig<CIOEngineConfig>.configHttpLogging() {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.BODY
    }
}

private inline fun HttpClientConfig<CIOEngineConfig>.configEngine() {
    engine {
        maxConnectionsCount = 1000
        endpoint {
            maxConnectionsPerRoute = 100
            pipelineMaxSize = 20
            keepAliveTime = 5000
            connectTimeout = 5000
            connectAttempts = 5
        }
    }
}