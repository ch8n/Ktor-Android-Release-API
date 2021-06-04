package com.dev.ch8n.server.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AndroidRelease(
    val releaseNotes: List<ReleaseNote>
)

@Serializable
data class ReleaseNote(
    val date: String,
    val releaseItems: List<ReleaseItem>,
    val id: String,
    val updatedAt: String,
    val authorName: String
)

@Serializable
data class ReleaseItem(
    val id: String,
    val title: String,
    val link: String
)