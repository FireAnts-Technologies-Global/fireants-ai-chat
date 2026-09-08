package com.anrstudio.template.data.repository

import com.anrstudio.template.data.network.model.billing.BillingStatusEnvelopeDto
import com.anrstudio.template.data.network.model.billing.VipProductsEnvelopeDto
import com.anrstudio.template.data.network.model.billing.toDomain
import com.anrstudio.template.data.network.service.BillingService
import com.anrstudio.template.domain.model.billing.BillingStatus
import com.anrstudio.template.domain.model.billing.VipProduct
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.BillingRepository
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
