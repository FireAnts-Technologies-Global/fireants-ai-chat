package com.pegas.yuki.virtual.chat.data.network.model.auth

import com.pegas.yuki.virtual.chat.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias AuthSessionEnvelopeDto = ApiEnvelopeDto<AuthSessionDto>
typealias AuthUserEnvelopeDto = ApiEnvelopeDto<AuthUserDto>
typealias LogoutEnvelopeDto = ApiEnvelopeDto<LogoutDto>

@JsonClass(generateAdapter = true)
data class AuthSessionDto(
    @Json(name = "accessToken") val accessToken: String? = null,
    @Json(name = "refreshToken") val refreshToken: String? = null,
    @Json(name = "resumeGuestToken") val resumeGuestToken: String? = null,
    @Json(name = "user") val user: AuthUserDto? = null,
    @Json(name = "session") val session: AuthSessionInfoDto? = null,
    @Json(name = "expiresIn") val expiresIn: String? = null,
    @Json(name = "refreshExpiresIn") val refreshExpiresIn: String? = null,
    @Json(name = "tokenType") val tokenType: String? = null
)

@JsonClass(generateAdapter = true)
data class AuthUserDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "appConfigId") val appConfigId: String? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "displayName") val displayName: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "email") val email: String? = null,
    @Json(name = "avatar") val avatar: String? = null,
    @Json(name = "resumeGuestToken") val resumeGuestToken: String? = null,
    @Json(name = "coinBalance") val coinBalance: Int? = null,
    @Json(name = "createdAt") val createdAt: String? = null
)

@JsonClass(generateAdapter = true)
data class AuthSessionInfoDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "ipAddress") val ipAddress: String? = null,
    @Json(name = "userAgent") val userAgent: String? = null,
    @Json(name = "expiresAt") val expiresAt: String? = null
)

@JsonClass(generateAdapter = true)
data class UpdateMeRequestDto(
    @Json(name = "displayName") val displayName: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "email") val email: String? = null,
    @Json(name = "avatar") val avatar: String? = null
)

@JsonClass(generateAdapter = true)
data class LogoutDto(
    @Json(name = "success") val success: Boolean? = null
)
