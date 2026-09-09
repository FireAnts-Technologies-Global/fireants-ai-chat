package com.pegas.aura.aigirlfriend.soul.domain.usecase.revenuecat

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatOffering
import com.pegas.aura.aigirlfriend.soul.domain.repository.RevenueCatRepository
import javax.inject.Inject

class GetRevenueCatOfferingsUseCase @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(): AppResult<List<RevenueCatOffering>> =
        revenueCatRepository.getOfferings()
}
