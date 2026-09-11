package com.pegas.yuki.virtual.chat.domain.model.auth

data class TokenSession(
    val accessToken: String,
    val refreshToken: String,
    val resumeGuestToken: String,
    val expiresIn: String,
    val refreshExpiresIn: String,
    val tokenType: String
)
