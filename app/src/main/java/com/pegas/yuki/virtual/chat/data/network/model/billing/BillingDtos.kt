package com.pegas.yuki.virtual.chat.data.network.model.billing

import com.pegas.yuki.virtual.chat.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias BillingStatusEnvelopeDto = ApiEnvelopeDto<BillingStatusDto>
typealias VipProductsEnvelopeDto = ApiEnvelopeDto<List<VipProductDto>>

@JsonClass(generateAdapter = true)
data class BillingStatusDto(
    @Json(name = "vip") val vip: Map<String, Any?>? = null,
    @Json(name = "iapEnabled") val iapEnabled: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class VipProductDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "code") val code: String? = null,
    @Json(name = "storeProductId") val storeProductId: String? = null,
    @Json(name = "platform") val platform: String? = null,
    @Json(name = "period") val period: String? = null,
    @Json(name = "displayName") val displayName: String? = null,
    @Json(name = "badge") val badge: String? = null,
    @Json(name = "sortOrder") val sortOrder: Int? = null
)
