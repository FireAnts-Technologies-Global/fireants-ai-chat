package com.pegas.aura.aigirlfriend.soul.data.network.service

import com.pegas.aura.aigirlfriend.soul.data.network.model.billing.BillingStatusEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.billing.VipProductsEnvelopeDto
import retrofit2.http.GET

interface BillingService {

    @GET("billing/status")
    suspend fun getBillingStatus(): BillingStatusEnvelopeDto

    @GET("billing/vip-products")
    suspend fun getVipProducts(): VipProductsEnvelopeDto
}
