package com.pegas.yuki.virtual.chat.domain.usecase.revenuecat

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.revenuecat.RevenueCatCustomer
import com.pegas.yuki.virtual.chat.domain.repository.RevenueCatRepository
import javax.inject.Inject

class GetRevenueCatCustomerInfoUseCase @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(): AppResult<RevenueCatCustomer> =
        revenueCatRepository.getCustomerInfo()
}
