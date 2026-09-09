package com.pegas.aura.aigirlfriend.soul.domain.usecase.coins

import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinBalance
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.CoinsRepository
import javax.inject.Inject

class GetCoinBalanceUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AppResult<CoinBalance> {
        val result = coinsRepository.getBalance()
        if (result is AppResult.Success) {
            authRepository.updateLocalCoinBalance(result.data.balance)
        }
        return result
    }
}
