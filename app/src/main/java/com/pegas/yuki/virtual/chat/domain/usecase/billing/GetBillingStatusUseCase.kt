package com.pegas.yuki.virtual.chat.domain.usecase.billing

import com.pegas.yuki.virtual.chat.domain.model.billing.BillingStatus
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.BillingRepository
import javax.inject.Inject

class GetBillingStatusUseCase @Inject constructor(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): AppResult<BillingStatus> =
        billingRepository.getBillingStatus(forceRefresh)
}
