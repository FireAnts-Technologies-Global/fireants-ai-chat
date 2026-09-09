package com.pegas.aura.aigirlfriend.soul.domain.model.auth

data class AuthUser(
    val id: String,
    val appConfigId: String,
    val type: String,
    val displayName: String,
    val name: String?,
    val email: String?,
    val avatar: String?,
    val resumeGuestToken: String,
    val coinBalance: Int,
    val createdAt: String?,
    val isGuest: Boolean
)
