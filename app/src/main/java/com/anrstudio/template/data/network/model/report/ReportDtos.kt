package com.anrstudio.template.data.network.model.report

import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias ReportResponseEnvelopeDto = ApiEnvelopeDto<ReportResponseDto>

@JsonClass(generateAdapter = true)
data class CreateReportRequestDto(
    @param:Json(name = "reason") val reason: String,
    @param:Json(name = "description") val description: String,
    @param:Json(name = "clientMeta") val clientMeta: ClientMetaDto,
    @param:Json(name = "conversationId") val conversationId: String? = null,
    @param:Json(name = "messageId") val messageId: String? = null
)

@JsonClass(generateAdapter = true)
data class ClientMetaDto(
    @param:Json(name = "appVersion") val appVersion: String,
    @param:Json(name = "platform") val platform: String,
    @param:Json(name = "screen") val screen: String
)

@JsonClass(generateAdapter = true)
data class ReportResponseDto(
    @param:Json(name = "id") val id: String,
    @param:Json(name = "reason") val reason: String,
    @param:Json(name = "status") val status: String,
    @param:Json(name = "createdAt") val createdAt: String
)
