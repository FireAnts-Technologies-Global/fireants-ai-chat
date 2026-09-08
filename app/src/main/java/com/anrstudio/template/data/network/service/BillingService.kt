package com.anrstudio.template.data.network.service

import com.anrstudio.template.data.network.model.billing.BillingStatusEnvelopeDto
import com.anrstudio.template.data.network.model.billing.VipProductsEnvelopeDto
import retrofit2.http.GET

interface BillingService {

    @GET("billing/status")
    suspend fun getBillingStatus(): BillingStatusEnvelopeDto

    @GET("billing/vip-products")
    suspend fun getVipProducts(): VipProductsEnvelopeDto
}
