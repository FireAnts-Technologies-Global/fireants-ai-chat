package com.anrstudio.template.domain.usecase.revenuecat

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.revenuecat.RevenueCatOffering
import com.anrstudio.template.domain.repository.RevenueCatRepository
import javax.inject.Inject

class GetRevenueCatOfferingsUseCase @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(): AppResult<List<RevenueCatOffering>> =
        revenueCatRepository.getOfferings()
}
