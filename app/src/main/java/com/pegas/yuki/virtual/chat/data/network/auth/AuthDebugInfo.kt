package com.pegas.yuki.virtual.chat.data.network.auth

data class AuthDebugInfo(
    val payloadJson: String?,
    val signature: String?,
    val bearerLength: Int?,
    val bearerToken: String? = null
)
