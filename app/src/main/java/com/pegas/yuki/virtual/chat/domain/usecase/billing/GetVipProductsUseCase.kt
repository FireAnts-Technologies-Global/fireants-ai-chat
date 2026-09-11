package com.pegas.yuki.virtual.chat.domain.usecase.billing

import com.pegas.yuki.virtual.chat.domain.model.billing.VipProduct
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.BillingRepository
import javax.inject.Inject

class GetVipProductsUseCase @Inject constructor(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): AppResult<List<VipProduct>> =
        billingRepository.getVipProducts(forceRefresh)
}
