package com.dev.ch8n.server.data.remote.api.source.android_release

import com.dev.ch8n.server.data.remote.api.config.ApiConfig
import com.dev.ch8n.server.data.remote.api.config.ApiConfig.Url
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.XML

interface AndroidReleaseService {
    suspend fun getAndroidReleaseRss(): String
    suspend fun getAndroidRelease(): AndroidReleaseDto
}

// instant test
fun main() {
    val rss = AndroidReleaseSource(ApiConfig.httpClient)
    runBlocking {
        val result = rss.getAndroidRelease().toAndroidRelease()
        println(result)
    }
}

class AndroidReleaseSource(
    private val httpClient: HttpClient
) : AndroidReleaseService {

    override suspend fun getAndroidReleaseRss(): String {
        val response = httpClient.use { client ->
            println("============ api called =============")
            client.request<HttpResponse>(Url.GET_ANDROID_RELEASE_RSS) {
                method = HttpMethod.Get
            }
        }
        val statusCode = response.status.value
        println("============ Status code : $statusCode =============")
        val result = when {
            statusCode in 300..399 -> throw Exception(response.readText())
            statusCode in 400..499 -> throw Exception(response.readText())
            statusCode in 500..599 -> throw Exception(response.readText())
            statusCode == 200 -> response.readText()
            else -> throw Exception(response.readText())
        }
        return result
    }

    override suspend fun getAndroidRelease(): AndroidReleaseDto {
        val rssString = getAndroidReleaseRss()
        println("============ RSS COMPLETED =============")
        println(rssString)
        // todo fix direct xml to pojo
        val jsonString = XML.toJSONObject(rssString).toString()
        println("============ RSS->JSON COMPLETED =============")
        println(jsonString)
        val androidReleaseDto = Json.decodeFromString<AndroidReleaseDto>(jsonString)
        androidReleaseDto.feed?.entry?.onEach {
            val contentXml = it.content?.cdata ?: ""
            val contentJson = XML.toJSONObject(contentXml).toString()
            //todo fix type api different types
            val releaseContentDtoV1 = kotlin.runCatching { Json.decodeFromString<ReleaseContentDto>(contentJson) }.getOrNull()
            val releaseContentDtoV2 = kotlin.runCatching { Json.decodeFromString<ReleaseContentDtoV2>(contentJson) }.getOrNull()
            it.content?.contentV1 = releaseContentDtoV1
            it.content?.contentV2 = releaseContentDtoV2
        }
        println("============ JSON->DATA COMPLETED =============")
        println(androidReleaseDto.feed?.entry?.size)
        return androidReleaseDto
    }
}