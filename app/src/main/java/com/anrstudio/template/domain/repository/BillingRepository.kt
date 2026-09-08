package com.anrstudio.template.domain.repository

import com.anrstudio.template.domain.model.billing.BillingStatus
import com.anrstudio.template.domain.model.billing.VipProduct
import com.anrstudio.template.domain.model.common.AppResult

interface BillingRepository {
    suspend fun getBillingStatus(): AppResult<BillingStatus>

    suspend fun getVipProducts(): AppResult<List<VipProduct>>
}
