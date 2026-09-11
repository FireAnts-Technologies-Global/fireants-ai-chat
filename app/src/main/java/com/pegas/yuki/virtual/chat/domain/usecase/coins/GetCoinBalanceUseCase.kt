package com.pegas.yuki.virtual.chat.domain.usecase.coins

import com.pegas.yuki.virtual.chat.domain.model.coins.CoinBalance
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.AuthRepository
import com.pegas.yuki.virtual.chat.domain.repository.CoinsRepository
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
