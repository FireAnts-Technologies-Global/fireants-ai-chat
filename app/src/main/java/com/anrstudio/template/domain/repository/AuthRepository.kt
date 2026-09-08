package com.anrstudio.template.domain.repository

import com.anrstudio.template.domain.model.auth.AuthUser
import com.anrstudio.template.domain.model.auth.GuestSession
import com.anrstudio.template.domain.model.auth.TokenSession
import com.anrstudio.template.domain.model.auth.UpdateMeInput
import com.anrstudio.template.domain.model.common.AppResult
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
