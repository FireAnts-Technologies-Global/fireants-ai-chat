package com.pegas.aura.aigirlfriend.soul.domain.usecase.billing

import com.pegas.aura.aigirlfriend.soul.domain.model.billing.VipProduct
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.BillingRepository
import javax.inject.Inject

class GetVipProductsUseCase @Inject constructor(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(): AppResult<List<VipProduct>> = billingRepository.getVipProducts()
}
