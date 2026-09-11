package com.pegas.yuki.virtual.chat.domain.usecase.coins

import com.pegas.yuki.virtual.chat.domain.model.coins.AdsReward
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.CoinsRepository
import javax.inject.Inject

class GetAdsRewardUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {
    suspend operator fun invoke(): AppResult<AdsReward> {
        return coinsRepository.getAdsReward()
    }
}
