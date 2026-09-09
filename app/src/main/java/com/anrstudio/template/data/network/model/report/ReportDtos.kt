package com.pegas.aura.aigirlfriend.soul.data.network.model.report

import com.pegas.aura.aigirlfriend.soul.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias ReportResponseEnvelopeDto = ApiEnvelopeDto<ReportResponseDto>

@JsonClass(generateAdapter = true)
data class CreateReportRequestDto(
    @Json(name = "reason") val reason: String,
    @Json(name = "description") val description: String,
    @Json(name = "clientMeta") val clientMeta: ClientMetaDto,
    @Json(name = "conversationId") val conversationId: String? = null,
    @Json(name = "messageId") val messageId: String? = null
)

@JsonClass(generateAdapter = true)
data class ClientMetaDto(
    @Json(name = "appVersion") val appVersion: String,
    @Json(name = "platform") val platform: String,
    @Json(name = "screen") val screen: String
)

@JsonClass(generateAdapter = true)
data class ReportResponseDto(
    @Json(name = "id") val id: String,
    @Json(name = "reason") val reason: String,
    @Json(name = "status") val status: String,
    @Json(name = "createdAt") val createdAt: String
)
