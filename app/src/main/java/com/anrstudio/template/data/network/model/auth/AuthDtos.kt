package com.anrstudio.template.data.network.model.auth

import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias AuthSessionEnvelopeDto = ApiEnvelopeDto<AuthSessionDto>
typealias AuthUserEnvelopeDto = ApiEnvelopeDto<AuthUserDto>
typealias LogoutEnvelopeDto = ApiEnvelopeDto<LogoutDto>

@JsonClass(generateAdapter = true)
data class AuthSessionDto(
    @param:Json(name = "accessToken") val accessToken: String? = null,
    @param:Json(name = "refreshToken") val refreshToken: String? = null,
    @param:Json(name = "resumeGuestToken") val resumeGuestToken: String? = null,
    @param:Json(name = "user") val user: AuthUserDto? = null,
    @param:Json(name = "session") val session: AuthSessionInfoDto? = null,
    @param:Json(name = "expiresIn") val expiresIn: String? = null,
    @param:Json(name = "refreshExpiresIn") val refreshExpiresIn: String? = null,
    @param:Json(name = "tokenType") val tokenType: String? = null
)

@JsonClass(generateAdapter = true)
data class AuthUserDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "appConfigId") val appConfigId: String? = null,
    @param:Json(name = "type") val type: String? = null,
    @param:Json(name = "displayName") val displayName: String? = null,
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "email") val email: String? = null,
    @param:Json(name = "avatar") val avatar: String? = null,
    @param:Json(name = "resumeGuestToken") val resumeGuestToken: String? = null,
    @param:Json(name = "coinBalance") val coinBalance: Int? = null,
    @param:Json(name = "createdAt") val createdAt: String? = null
)

@JsonClass(generateAdapter = true)
data class AuthSessionInfoDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "ipAddress") val ipAddress: String? = null,
    @param:Json(name = "userAgent") val userAgent: String? = null,
    @param:Json(name = "expiresAt") val expiresAt: String? = null
)

@JsonClass(generateAdapter = true)
data class UpdateMeRequestDto(
    @param:Json(name = "displayName") val displayName: String? = null,
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "email") val email: String? = null,
    @param:Json(name = "avatar") val avatar: String? = null
)

@JsonClass(generateAdapter = true)
data class LogoutDto(
    @param:Json(name = "success") val success: Boolean? = null
)
