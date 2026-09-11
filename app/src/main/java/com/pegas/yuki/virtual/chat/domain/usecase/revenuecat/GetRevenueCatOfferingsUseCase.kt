package com.pegas.yuki.virtual.chat.domain.usecase.revenuecat

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.revenuecat.RevenueCatOffering
import com.pegas.yuki.virtual.chat.domain.repository.RevenueCatRepository
import javax.inject.Inject

class GetRevenueCatOfferingsUseCase @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(): AppResult<List<RevenueCatOffering>> =
        revenueCatRepository.getOfferings()
}
