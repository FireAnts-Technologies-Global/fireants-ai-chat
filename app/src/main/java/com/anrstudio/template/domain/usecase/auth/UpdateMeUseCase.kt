package com.anrstudio.template.domain.usecase.auth

import com.anrstudio.template.domain.model.auth.AuthUser
import com.anrstudio.template.domain.model.auth.UpdateMeInput
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateMeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(input: UpdateMeInput): AppResult<AuthUser> =
        authRepository.updateMe(input)
}
