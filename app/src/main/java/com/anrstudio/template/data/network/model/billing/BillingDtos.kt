package com.anrstudio.template.data.network.model.billing

import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias BillingStatusEnvelopeDto = ApiEnvelopeDto<BillingStatusDto>
typealias VipProductsEnvelopeDto = ApiEnvelopeDto<List<VipProductDto>>

@JsonClass(generateAdapter = true)
data class BillingStatusDto(
    @param:Json(name = "vip") val vip: Map<String, Any?>? = null,
    @param:Json(name = "iapEnabled") val iapEnabled: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class VipProductDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "code") val code: String? = null,
    @param:Json(name = "storeProductId") val storeProductId: String? = null,
    @param:Json(name = "platform") val platform: String? = null,
    @param:Json(name = "period") val period: String? = null,
    @param:Json(name = "displayName") val displayName: String? = null,
    @param:Json(name = "badge") val badge: String? = null,
    @param:Json(name = "sortOrder") val sortOrder: Int? = null
)
