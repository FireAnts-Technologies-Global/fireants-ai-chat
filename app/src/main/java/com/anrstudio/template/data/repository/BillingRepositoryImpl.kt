package com.pegas.aura.aigirlfriend.soul.data.repository

import com.pegas.aura.aigirlfriend.soul.data.network.model.billing.BillingStatusEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.billing.VipProductsEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.billing.toDomain
import com.pegas.aura.aigirlfriend.soul.data.network.service.BillingService
import com.pegas.aura.aigirlfriend.soul.domain.model.billing.BillingStatus
import com.pegas.aura.aigirlfriend.soul.domain.model.billing.VipProduct
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.BillingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepositoryImpl @Inject constructor(
    private val billingService: BillingService
) : BillingRepository {

    private var cachedBillingStatus: BillingStatus? = null
    private var cachedVipProducts: List<VipProduct>? = null

    override fun getCachedBillingStatus(): BillingStatus? = cachedBillingStatus

    override fun getCachedVipProducts(): List<VipProduct>? = cachedVipProducts

    override suspend fun getBillingStatus(forceRefresh: Boolean): AppResult<BillingStatus> {
        if (!forceRefresh) {
            cachedBillingStatus?.let { return AppResult.Success(it) }
        }
        return apiResult("getBillingStatus") {
            billingService.getBillingStatus().requireData().toDomain().also {
                cachedBillingStatus = it
            }
        }
    }

    override suspend fun getVipProducts(forceRefresh: Boolean): AppResult<List<VipProduct>> {
        if (!forceRefresh) {
            cachedVipProducts?.let { return AppResult.Success(it) }
        }
        return apiResult("getVipProducts") {
            billingService.getVipProducts().requireData().map { it.toDomain() }.also {
                cachedVipProducts = it
            }
        }
    }
}

private fun BillingStatusEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing billing status data")

private fun VipProductsEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing VIP products data")
