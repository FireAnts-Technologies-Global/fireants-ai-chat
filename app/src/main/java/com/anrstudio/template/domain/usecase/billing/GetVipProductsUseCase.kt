package com.anrstudio.template.domain.usecase.billing

import com.anrstudio.template.domain.model.billing.VipProduct
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.BillingRepository
import javax.inject.Inject

class GetVipProductsUseCase @Inject constructor(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(): AppResult<List<VipProduct>> = billingRepository.getVipProducts()
}
