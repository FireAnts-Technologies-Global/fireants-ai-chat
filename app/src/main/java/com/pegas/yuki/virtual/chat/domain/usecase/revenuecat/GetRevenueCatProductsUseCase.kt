package com.pegas.yuki.virtual.chat.domain.usecase.revenuecat

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.revenuecat.RevenueCatStoreProduct
import com.pegas.yuki.virtual.chat.domain.repository.RevenueCatRepository
import javax.inject.Inject

class GetRevenueCatProductsUseCase @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(productIds: List<String>): AppResult<List<RevenueCatStoreProduct>> =
        revenueCatRepository.getProducts(productIds)
}
