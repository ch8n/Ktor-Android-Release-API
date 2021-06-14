package com.dev.ch8n.server.data.models

import kotlinx.serialization.Serializable
import org.kodein.db.model.Id

@Serializable
data class AndroidRelease(
    @Id val uid: String,
    val releaseNotes: List<ReleaseNote>
)

@Serializable
data class ReleaseNote(
    val releaseItems: List<ReleaseItem>,
    val id: String,
    val updatedAt: String
)

@Serializable
data class ReleaseItem(
    val id: String,
    val title: String,
    val link: String
)