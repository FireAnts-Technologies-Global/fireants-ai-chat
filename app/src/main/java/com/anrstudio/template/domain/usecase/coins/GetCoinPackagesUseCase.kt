package com.anrstudio.template.domain.usecase.coins

import com.anrstudio.template.domain.model.coins.CoinPackage
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.CoinsRepository
import javax.inject.Inject

class GetCoinPackagesUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {
    suspend operator fun invoke(): AppResult<List<CoinPackage>> = coinsRepository.getPackages()
}
