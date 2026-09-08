package com.anrstudio.template.domain.usecase.coins

import com.anrstudio.template.domain.model.coins.CoinCheckInClaim
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.AuthRepository
import com.anrstudio.template.domain.repository.CoinsRepository
import javax.inject.Inject

class ClaimCheckInUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AppResult<CoinCheckInClaim> {
        val result = coinsRepository.claimCheckIn()
        if (result is AppResult.Success) {
            authRepository.updateLocalCoinBalance(result.data.balance)
        }
        return result
    }
}
