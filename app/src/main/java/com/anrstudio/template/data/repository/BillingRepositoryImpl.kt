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

    override suspend fun getBillingStatus(): AppResult<BillingStatus> =
        apiResult("getBillingStatus") {
            billingService.getBillingStatus().requireData().toDomain()
        }

    override suspend fun getVipProducts(): AppResult<List<VipProduct>> =
        apiResult("getVipProducts") {
            billingService.getVipProducts().requireData().map { it.toDomain() }
        }
}

private fun BillingStatusEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing billing status data")

private fun VipProductsEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing VIP products data")
