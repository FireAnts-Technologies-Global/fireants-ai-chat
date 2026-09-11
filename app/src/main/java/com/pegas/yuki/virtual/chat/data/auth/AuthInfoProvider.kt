package com.pegas.yuki.virtual.chat.data.auth

interface AuthInfoProvider {
    val packageName: String
    val appVersion: String
    val deviceName: String
    val accessToken: String
    val refreshToken: String
    val resumeGuestToken: String
    val userId: String
    val clientId: String
    val pushToken: String
    val deviceId: String

    fun persistGuestSession(
        accessToken: String,
        refreshToken: String,
        resumeGuestToken: String,
        userId: String
    )

    fun persistRefreshedTokens(
        accessToken: String,
        refreshToken: String,
        resumeGuestToken: String
    )

    fun clearAuth(clearResumeGuestToken: Boolean = true)
}
