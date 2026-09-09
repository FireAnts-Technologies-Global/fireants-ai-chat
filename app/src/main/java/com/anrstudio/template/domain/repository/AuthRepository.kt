package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.auth.AuthUser
import com.pegas.aura.aigirlfriend.soul.domain.model.auth.GuestSession
import com.pegas.aura.aigirlfriend.soul.domain.model.auth.TokenSession
import com.pegas.aura.aigirlfriend.soul.domain.model.auth.UpdateMeInput
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
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
