package com.anrstudio.template.domain.usecase.coins

import com.anrstudio.template.domain.model.coins.WatchAdInput
import com.anrstudio.template.domain.model.coins.WatchAdResult
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.AuthRepository
import com.anrstudio.template.domain.repository.CoinsRepository
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
