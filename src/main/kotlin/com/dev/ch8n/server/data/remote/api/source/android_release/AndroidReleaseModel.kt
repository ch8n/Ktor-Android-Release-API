package com.dev.ch8n.server.data.remote.api.source.android_release

import com.dev.ch8n.server.data.models.AndroidRelease
import com.dev.ch8n.server.data.models.ReleaseItem
import com.dev.ch8n.server.data.models.ReleaseNote
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
    @SerialName("content")
    val cdata: String? = null,
    @SerialName("type")
    val type: String? = null,
) {
    @Transient
    var contentV1: ReleaseContentDto? = null
    @Transient
    var contentV2: ReleaseContentDtoV2? = null
}

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
    @SerialName("xmlns")
    val xmlns: String? = null
)

@Serializable
data class Link(
    @SerialName("href")
    val href: String? = null,
    @SerialName("rel")
    val rel: String? = null
)

@Serializable
data class LinkX(
    @SerialName("href")
    val href: String? = null,
    @SerialName("rel")
    val rel: String? = null
)

@Serializable
data class ReleaseContentDto(
    @SerialName("ul")
    val ul: Ul? = null
)

@Serializable
data class Ul(
    @SerialName("li")
    val li: List<Li>? = null
)


@Serializable
data class ReleaseContentDtoV2(
    @SerialName("ul")
    val ul: UlV2? = null
)

@Serializable
data class UlV2(
    @SerialName("li")
    val li: Li? = null
)


@Serializable
data class Li(
    @SerialName("a")
    val a: A? = null
)

@Serializable
data class A(
    @SerialName("content")
    val content: String? = null,
    @SerialName("href")
    val href: String? = null
)

