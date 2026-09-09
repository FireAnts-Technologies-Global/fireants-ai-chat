package com.pegas.aura.aigirlfriend.soul.domain.usecase.revenuecat

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatCustomer
import com.pegas.aura.aigirlfriend.soul.domain.repository.RevenueCatRepository
import javax.inject.Inject

class RestoreRevenueCatPurchasesUseCase @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(): AppResult<RevenueCatCustomer> =
        revenueCatRepository.restorePurchases()
}
