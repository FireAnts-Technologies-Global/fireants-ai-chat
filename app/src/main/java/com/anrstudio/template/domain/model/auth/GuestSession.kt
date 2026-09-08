package com.anrstudio.template.domain.model.auth

data class GuestSession(
    val user: AuthUser?,
    val accessToken: String,
    val refreshToken: String,
    val resumeGuestToken: String,
    val expiresIn: String,
    val refreshExpiresIn: String,
    val tokenType: String
)
