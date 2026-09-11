package com.pegas.yuki.virtual.chat.domain.usecase.auth

import com.pegas.yuki.virtual.chat.domain.model.auth.AuthUser
import com.pegas.yuki.virtual.chat.domain.model.auth.UpdateMeInput
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateMeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(input: UpdateMeInput): AppResult<AuthUser> =
        authRepository.updateMe(input)
}
