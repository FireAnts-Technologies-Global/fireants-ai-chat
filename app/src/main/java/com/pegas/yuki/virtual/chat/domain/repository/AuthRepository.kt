package com.pegas.yuki.virtual.chat.domain.repository

import com.pegas.yuki.virtual.chat.domain.model.auth.AuthUser
import com.pegas.yuki.virtual.chat.domain.model.auth.GuestSession
import com.pegas.yuki.virtual.chat.domain.model.auth.TokenSession
import com.pegas.yuki.virtual.chat.domain.model.auth.UpdateMeInput
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authUser: StateFlow<AuthUser?>

    suspend fun guestLogin(): AppResult<GuestSession>

    suspend fun refreshToken(): AppResult<TokenSession>

    suspend fun me(): AppResult<AuthUser>

    suspend fun updateMe(input: UpdateMeInput): AppResult<AuthUser>

    suspend fun logout(): AppResult<Unit>

    fun updateLocalCoinBalance(balance: Int)
}
