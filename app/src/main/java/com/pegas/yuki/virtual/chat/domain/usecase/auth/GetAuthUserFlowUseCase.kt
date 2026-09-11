package com.pegas.yuki.virtual.chat.domain.usecase.auth

import com.pegas.yuki.virtual.chat.domain.model.auth.AuthUser
import com.pegas.yuki.virtual.chat.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthUserFlowUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): StateFlow<AuthUser?> = authRepository.authUser
}
