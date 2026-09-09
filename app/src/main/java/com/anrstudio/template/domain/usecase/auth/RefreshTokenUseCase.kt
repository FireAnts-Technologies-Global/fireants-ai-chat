package com.pegas.aura.aigirlfriend.soul.domain.usecase.auth

import com.pegas.aura.aigirlfriend.soul.domain.model.auth.TokenSession
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AppResult<TokenSession> = authRepository.refreshToken()
}
