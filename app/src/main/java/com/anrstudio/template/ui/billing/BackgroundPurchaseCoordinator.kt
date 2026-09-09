package com.pegas.aura.aigirlfriend.soul.ui.billing

import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackground
import com.pegas.aura.aigirlfriend.soul.domain.model.character.PurchaseBackgroundResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.domain.usecase.character.PurchaseCharacterBackgroundUseCase
import com.pegas.aura.aigirlfriend.soul.utils.PurchaseTracking
import javax.inject.Inject

sealed interface BackgroundPurchaseResult {
    data class Success(val result: PurchaseBackgroundResult) : BackgroundPurchaseResult
    data class Failure(val error: PublicError) : BackgroundPurchaseResult
}

class BackgroundPurchaseCoordinator @Inject constructor(
    private val purchaseCharacterBackgroundUseCase: PurchaseCharacterBackgroundUseCase
) {
    suspend fun purchase(
        screen: String,
        characterId: String,
        background: CharacterBackground,
        coinBalance: Int
    ): BackgroundPurchaseResult {
        PurchaseTracking.backgroundPurchaseIntent(
            screen = screen,
            characterId = characterId,
            background = background,
            coinBalance = coinBalance
        )

        return when (val result = purchaseCharacterBackgroundUseCase(characterId, background.id)) {
            is AppResult.Success -> {
                val purchasedBackground = result.data.background ?: background
                PurchaseTracking.backgroundPurchaseSuccess(
                    screen = screen,
                    characterId = characterId,
                    background = purchasedBackground,
                    coinBalance = result.data.coinBalance
                )
                BackgroundPurchaseResult.Success(result.data)
            }

            is AppResult.Failure -> {
                PurchaseTracking.backgroundPurchaseFail(
                    screen = screen,
                    characterId = characterId,
                    background = background,
                    coinBalance = coinBalance,
                    error = result.error
                )
                BackgroundPurchaseResult.Failure(result.error)
            }
        }
    }
}
