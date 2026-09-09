package com.pegas.aura.aigirlfriend.soul.domain.usecase.auth

import com.pegas.aura.aigirlfriend.soul.domain.model.auth.GuestSession
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import javax.inject.Inject

class GuestLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AppResult<GuestSession> = authRepository.guestLogin()
}
