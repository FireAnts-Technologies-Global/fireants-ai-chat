package com.pegas.yuki.virtual.chat.domain.usecase.coins

import com.pegas.yuki.virtual.chat.domain.model.coins.CoinCheckInClaim
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.AuthRepository
import com.pegas.yuki.virtual.chat.domain.repository.CoinsRepository
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
