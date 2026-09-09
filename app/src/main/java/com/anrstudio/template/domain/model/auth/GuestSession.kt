package com.pegas.aura.aigirlfriend.soul.domain.model.auth

data class GuestSession(
    val user: AuthUser?,
    val accessToken: String,
    val refreshToken: String,
    val resumeGuestToken: String,
    val expiresIn: String,
    val refreshExpiresIn: String,
    val tokenType: String
)
