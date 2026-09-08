package com.anrstudio.template.domain.usecase.auth

import com.anrstudio.template.domain.model.auth.AuthUser
import com.anrstudio.template.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthUserFlowUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): StateFlow<AuthUser?> = authRepository.authUser
}
