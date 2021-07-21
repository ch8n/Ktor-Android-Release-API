package com.dev.ch8n.server.utils

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val data: T,
    val error: String = ""
)