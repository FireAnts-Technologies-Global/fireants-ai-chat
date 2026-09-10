package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.billing.BillingStatus
import com.pegas.aura.aigirlfriend.soul.domain.model.billing.VipProduct
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult

interface BillingRepository {
    suspend fun getBillingStatus(forceRefresh: Boolean = false): AppResult<BillingStatus>

    suspend fun getVipProducts(forceRefresh: Boolean = false): AppResult<List<VipProduct>>

    fun getCachedBillingStatus(): BillingStatus? = null

    fun getCachedVipProducts(): List<VipProduct>? = null
}
