package com.pegas.yuki.virtual.chat.data.network.model.auth

import com.pegas.yuki.virtual.chat.domain.model.auth.AuthUser
import com.pegas.yuki.virtual.chat.domain.model.auth.GuestSession
import com.pegas.yuki.virtual.chat.domain.model.auth.TokenSession
import com.pegas.yuki.virtual.chat.domain.model.auth.UpdateMeInput

fun AuthSessionDto.toGuestSession(): GuestSession = GuestSession(
    user = user?.toDomain(),
    accessToken = accessToken.orEmpty(),
    refreshToken = refreshToken.orEmpty(),
    resumeGuestToken = resumeGuestToken.orEmpty(),
    expiresIn = expiresIn.orEmpty(),
    refreshExpiresIn = refreshExpiresIn.orEmpty(),
    tokenType = tokenType.orEmpty()
)

fun AuthSessionDto.toTokenSession(): TokenSession = TokenSession(
    accessToken = accessToken.orEmpty(),
    refreshToken = refreshToken.orEmpty(),
    resumeGuestToken = resumeGuestToken.orEmpty(),
    expiresIn = expiresIn.orEmpty(),
    refreshExpiresIn = refreshExpiresIn.orEmpty(),
    tokenType = tokenType.orEmpty()
)

fun AuthUserDto.toDomain(): AuthUser = AuthUser(
    id = id.orEmpty(),
    appConfigId = appConfigId.orEmpty(),
    type = type.orEmpty(),
    displayName = displayName.orEmpty(),
    name = name,
    email = email,
    avatar = avatar,
    resumeGuestToken = resumeGuestToken.orEmpty(),
    coinBalance = coinBalance ?: 0,
    createdAt = createdAt,
    isGuest = type.equals("GUEST", ignoreCase = true)
)

fun UpdateMeInput.toRequestDto(): UpdateMeRequestDto = UpdateMeRequestDto(
    displayName = displayName,
    name = name,
    email = email,
    avatar = avatar
)
