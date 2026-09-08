package com.anrstudio.template.domain.usecase.billing

import com.anrstudio.template.domain.model.billing.BillingStatus
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.BillingRepository
import javax.inject.Inject

class GetBillingStatusUseCase @Inject constructor(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(): AppResult<BillingStatus> = billingRepository.getBillingStatus()
}
