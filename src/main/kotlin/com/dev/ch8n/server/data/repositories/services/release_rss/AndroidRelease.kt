package com.dev.ch8n.server.data.repositories.services.release_rss

import com.dev.ch8n.server.data.models.AndroidRelease
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AndroidReleaseDto(
    @SerialName("feed")
    val feed: Feed? = null
)

@Serializable
data class Author(
    @SerialName("name")
    val name: String? = null
)

@Serializable
data class Content(
    @SerialName("__cdata")
    val cdata: String? = null,
    @SerialName("_type")
    val type: String? = null
)

@Serializable
data class Entry(
    @SerialName("content")
    val content: Content? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("link")
    val link: Link? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("updated")
    val updated: String? = null
)

@Serializable
data class Feed(
    @SerialName("author")
    val author: Author? = null,
    @SerialName("entry")
    val entry: List<Entry>? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("link")
    val link: LinkX? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("updated")
    val updated: String? = null,
    @SerialName("_xmlns")
    val xmlns: String? = null
)

@Serializable
data class Link(
    @SerialName("_href")
    val href: String? = null,
    @SerialName("_rel")
    val rel: String? = null
)

@Serializable
data class LinkX(
    @SerialName("_href")
    val href: String? = null,
    @SerialName("_rel")
    val rel: String? = null
)

fun AndroidReleaseDto.toAndroidRelease(): AndroidRelease {
    val releaseNotes = mutableListOf<ReleaseNote>
    return this.feed.entry.forEach { entry ->
        releaseNotes.add(
            ReleaseNote(
                updatedAt = entry.updated,
                authorName = this.feed.author,
                id = entry.id
            )
        )
    }
}