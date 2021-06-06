package com.dev.ch8n.server.data.di

import com.dev.ch8n.server.data.remote.api.config.ApiConfig
import com.dev.ch8n.server.data.remote.api.source.android_release.AndroidReleaseService
import com.dev.ch8n.server.data.remote.api.source.android_release.AndroidReleaseSource
import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository
import com.dev.ch8n.server.data.repositories.GreetingRepository

object Injector {
    val greetingRepository by lazy { GreetingRepository() }

    private val httpClient by lazy { ApiConfig.httpClient }
    private val androidReleaseService: AndroidReleaseService by lazy { AndroidReleaseSource(httpClient) }
    val androidReleaseRepository by lazy { AndroidReleaseRepository(androidReleaseService) }
}