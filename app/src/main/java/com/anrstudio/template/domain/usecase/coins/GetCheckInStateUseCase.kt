package com.pegas.aura.aigirlfriend.soul.domain.usecase.coins

import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinCheckInState
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.CoinsRepository
import javax.inject.Inject

class GetCheckInStateUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {
    suspend operator fun invoke(): AppResult<CoinCheckInState> = coinsRepository.getCheckInState()
}
