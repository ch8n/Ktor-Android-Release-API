package com.dev.ch8n.server.data.di

import com.dev.ch8n.server.data.local.database.config.DBConfig
import com.dev.ch8n.server.data.local.database.sources.AndroidReleaseLocalService
import com.dev.ch8n.server.data.local.database.sources.AndroidReleaseLocalSource
import com.dev.ch8n.server.data.remote.api.config.ApiConfig
import com.dev.ch8n.server.data.remote.api.source.android_release.AndroidReleaseRemoteService
import com.dev.ch8n.server.data.remote.api.source.android_release.AndroidReleaseRemoteSource
import com.dev.ch8n.server.data.repositories.AndroidReleaseController
import com.dev.ch8n.server.data.repositories.AndroidReleaseRepository

object Injector {

    private val httpClient by lazy { ApiConfig.httpClient }
    private val dbClient by lazy { DBConfig.dbClient }
    private val androidReleaseRemoteService: AndroidReleaseRemoteService by lazy { AndroidReleaseRemoteSource(httpClient) }
    private val androidReleaseLocalService: AndroidReleaseLocalService by lazy { AndroidReleaseLocalSource(dbClient) }
    private val androidReleaseRepository by lazy { AndroidReleaseRepository(androidReleaseRemoteService, androidReleaseLocalService) }
    val androidReleaseCollection  by lazy { AndroidReleaseController(androidReleaseRepository) }
}