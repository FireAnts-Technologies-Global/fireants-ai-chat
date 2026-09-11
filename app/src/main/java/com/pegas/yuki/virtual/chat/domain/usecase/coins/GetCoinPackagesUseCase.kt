package com.pegas.yuki.virtual.chat.domain.usecase.coins

import com.pegas.yuki.virtual.chat.domain.model.coins.CoinPackage
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.CoinsRepository
import javax.inject.Inject

class GetCoinPackagesUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): AppResult<List<CoinPackage>> =
        coinsRepository.getPackages(forceRefresh)
}
