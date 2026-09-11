package com.pegas.yuki.virtual.chat.domain.usecase.character

import com.pegas.yuki.virtual.chat.domain.model.character.PurchaseBackgroundResult
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.AuthRepository
import com.pegas.yuki.virtual.chat.domain.repository.CharacterRepository
import javax.inject.Inject

class PurchaseCharacterBackgroundUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        characterId: String,
        backgroundId: String
    ): AppResult<PurchaseBackgroundResult> {
        val result = characterRepository.purchaseCharacterBackground(characterId, backgroundId)
        if (result is AppResult.Success) {
            authRepository.updateLocalCoinBalance(result.data.coinBalance)
        }
        return result
    }
}
