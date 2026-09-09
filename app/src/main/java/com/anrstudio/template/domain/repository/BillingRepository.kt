package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.billing.BillingStatus
import com.pegas.aura.aigirlfriend.soul.domain.model.billing.VipProduct
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult

interface BillingRepository {
    suspend fun getBillingStatus(): AppResult<BillingStatus>

    suspend fun getVipProducts(): AppResult<List<VipProduct>>
}
