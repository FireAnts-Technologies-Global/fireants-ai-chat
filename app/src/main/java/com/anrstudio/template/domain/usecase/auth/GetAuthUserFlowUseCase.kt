package com.pegas.aura.aigirlfriend.soul.domain.usecase.auth

import com.pegas.aura.aigirlfriend.soul.domain.model.auth.AuthUser
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthUserFlowUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): StateFlow<AuthUser?> = authRepository.authUser
}
