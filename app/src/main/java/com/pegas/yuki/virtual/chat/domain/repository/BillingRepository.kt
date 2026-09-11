package com.pegas.yuki.virtual.chat.domain.repository

import com.pegas.yuki.virtual.chat.domain.model.billing.BillingStatus
import com.pegas.yuki.virtual.chat.domain.model.billing.VipProduct
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult

interface BillingRepository {
    suspend fun getBillingStatus(forceRefresh: Boolean = false): AppResult<BillingStatus>

    suspend fun getVipProducts(forceRefresh: Boolean = false): AppResult<List<VipProduct>>

    fun getCachedBillingStatus(): BillingStatus? = null

    fun getCachedVipProducts(): List<VipProduct>? = null
}
