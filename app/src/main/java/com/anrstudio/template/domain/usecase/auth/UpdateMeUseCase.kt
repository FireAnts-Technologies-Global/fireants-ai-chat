package com.pegas.aura.aigirlfriend.soul.domain.usecase.auth

import com.pegas.aura.aigirlfriend.soul.domain.model.auth.AuthUser
import com.pegas.aura.aigirlfriend.soul.domain.model.auth.UpdateMeInput
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateMeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(input: UpdateMeInput): AppResult<AuthUser> =
        authRepository.updateMe(input)
}
