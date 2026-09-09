package com.pegas.aura.aigirlfriend.soul.domain.usecase.revenuecat

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatStoreProduct
import com.pegas.aura.aigirlfriend.soul.domain.repository.RevenueCatRepository
import javax.inject.Inject

class GetRevenueCatProductsUseCase @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(productIds: List<String>): AppResult<List<RevenueCatStoreProduct>> =
        revenueCatRepository.getProducts(productIds)
}
