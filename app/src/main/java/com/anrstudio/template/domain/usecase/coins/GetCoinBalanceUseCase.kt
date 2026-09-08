package com.anrstudio.template.domain.usecase.coins

import com.anrstudio.template.domain.model.coins.CoinBalance
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.AuthRepository
import com.anrstudio.template.domain.repository.CoinsRepository
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
