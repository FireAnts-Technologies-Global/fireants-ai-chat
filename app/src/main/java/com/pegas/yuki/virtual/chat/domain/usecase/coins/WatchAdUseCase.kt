package com.pegas.yuki.virtual.chat.domain.usecase.coins

import com.pegas.yuki.virtual.chat.domain.model.coins.WatchAdInput
import com.pegas.yuki.virtual.chat.domain.model.coins.WatchAdResult
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.AuthRepository
import com.pegas.yuki.virtual.chat.domain.repository.CoinsRepository
import javax.inject.Inject

class WatchAdUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(input: WatchAdInput): AppResult<WatchAdResult> {
        val result = coinsRepository.watchAd(input)
        if (result is AppResult.Success) {
            authRepository.updateLocalCoinBalance(result.data.balance)
        }
        return result
    }
}
