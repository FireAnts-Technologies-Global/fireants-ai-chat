package com.anrstudio.template.domain.usecase.character

import com.anrstudio.template.domain.model.character.PurchaseBackgroundResult
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.AuthRepository
import com.anrstudio.template.domain.repository.CharacterRepository
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
