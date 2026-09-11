package com.pegas.yuki.virtual.chat.data.network.model.promo

import com.pegas.yuki.virtual.chat.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias RedeemPromoCodeEnvelopeDto = ApiEnvelopeDto<RedeemPromoCodeDto>

@JsonClass(generateAdapter = true)
data class RedeemPromoCodeRequestDto(
    @Json(name = "code") val code: String,
    @Json(name = "deviceId") val deviceId: String,
    @Json(name = "appVersion") val appVersion: String
)

@JsonClass(generateAdapter = true)
data class RedeemPromoCodeDto(
    @Json(name = "success") val success: Boolean? = null,
    @Json(name = "coinsAwarded") val coinsAwarded: Int? = null,
    @Json(name = "newBalance") val newBalance: Int? = null,
    @Json(name = "vipStatus") val vipStatus: Map<String, Any?>? = null
)
