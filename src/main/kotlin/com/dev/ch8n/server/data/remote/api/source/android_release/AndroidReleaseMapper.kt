package com.dev.ch8n.server.data.remote.api.source.android_release

import com.dev.ch8n.server.data.models.AndroidRelease
import com.dev.ch8n.server.data.models.ReleaseItem
import com.dev.ch8n.server.data.models.ReleaseNote
import java.util.*

fun AndroidReleaseDto.toAndroidRelease(): AndroidRelease {
    val entries = this.feed?.entry ?: emptyList()
    val releaseNotes = entries.map { entry: Entry ->
        ReleaseNote(
            id = entry.id ?: UUID.randomUUID().toString(),
            releaseItems = entry.getReleaseItems(),
            updatedAt = entry.updated ?: "",
        )
    }
    return AndroidRelease(UUID.randomUUID().toString(), releaseNotes)
}

private inline fun Entry.getReleaseItems(): List<ReleaseItem> {
    val contentV1 = content?.contentV1
    val contentV2 = content?.contentV2
    return when {
        contentV1 != null -> {
            contentV1.ul?.li?.map { li ->
                ReleaseItem(
                    id = UUID.randomUUID().toString(),
                    title = li.a?.content ?: "",
                    link = li.a?.href ?: ""
                )
            } ?: emptyList()
        }
        else -> {
            listOf(
                ReleaseItem(
                    id = UUID.randomUUID().toString(),
                    title = contentV2?.ul?.li?.a?.content ?: "",
                    link = contentV2?.ul?.li?.a?.href ?: ""
                )
            )
        }
    }
}