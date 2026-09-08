package com.anrstudio.template.domain.usecase.auth

import com.anrstudio.template.domain.model.auth.AuthUser
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.AuthRepository
import javax.inject.Inject

class GetMeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AppResult<AuthUser> = authRepository.me()
}
