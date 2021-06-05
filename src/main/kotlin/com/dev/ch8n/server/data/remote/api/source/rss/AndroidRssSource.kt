package com.dev.ch8n.server.data.remote.api.source.rss

import com.dev.ch8n.server.data.remote.api.config.ApiConfig
import com.dev.ch8n.server.data.remote.api.config.ApiConfig.Url
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

interface AndroidRssService {
    suspend fun getAndroidReleaseRss(): String
}

// instant test
fun main(){
    val rss = AndroidRssSource(ApiConfig.httpClient)
    runBlocking {
        val result = rss.getAndroidReleaseRss()
        println(result)
    }
}

class AndroidRssSource(
    private val httpClient: HttpClient
) : AndroidRssService {

    override suspend fun getAndroidReleaseRss(): String {
        val response = httpClient.use { client ->
            client.request<HttpResponse>(Url.GET_ANDROID_RELEASE_RSS) {
                method = HttpMethod.Get
            }
        }
        val statusCode = response.status.value
        println("Status code : $statusCode")
        val result = when {
            statusCode in 300..399 -> throw Exception(response.readText())
            statusCode in 400..499 -> throw Exception(response.readText())
            statusCode in 500..599 -> throw Exception(response.readText())
            statusCode == 200 -> response.readText()
            else -> throw Exception(response.readText())
        }
        return result
    }
}