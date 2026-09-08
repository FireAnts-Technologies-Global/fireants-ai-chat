package com.anrstudio.template.domain.usecase.revenuecat

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.revenuecat.RevenueCatCustomer
import com.anrstudio.template.domain.repository.RevenueCatRepository
import javax.inject.Inject

class GetRevenueCatCustomerInfoUseCase @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(): AppResult<RevenueCatCustomer> =
        revenueCatRepository.getCustomerInfo()
}
