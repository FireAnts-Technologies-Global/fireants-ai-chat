package com.pegas.aura.aigirlfriend.soul.domain.usecase.character

import com.pegas.aura.aigirlfriend.soul.domain.model.character.PurchaseBackgroundResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.CharacterRepository
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
