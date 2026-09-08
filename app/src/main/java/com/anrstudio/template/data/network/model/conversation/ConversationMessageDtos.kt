package com.anrstudio.template.data.network.model.conversation

import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.anrstudio.template.data.network.model.character.CharacterProgressDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias ConversationMessagesEnvelopeDto = ApiEnvelopeDto<List<ConversationMessageDto>>
typealias SendChatEnvelopeDto = ApiEnvelopeDto<SendChatResponseDto>

@JsonClass(generateAdapter = true)
data class SendChatResponseDto(
    @param:Json(name = "conversationId") val conversationId: String? = null,
    @param:Json(name = "backgroundId") val backgroundId: String? = null,
    @param:Json(name = "userMessage") val userMessage: ConversationMessageDto? = null,
    @param:Json(name = "assistantMessage") val assistantMessage: ConversationMessageDto? = null,
    @param:Json(name = "meta") val meta: SendChatMetaDto? = null
)

@JsonClass(generateAdapter = true)
data class SendChatMetaDto(
    @param:Json(name = "cached") val cached: Boolean? = null,
    @param:Json(name = "contextMode") val contextMode: String? = null,
    @param:Json(name = "provider") val provider: String? = null,
    @param:Json(name = "model") val model: String? = null,
    @param:Json(name = "providerRequestId") val providerRequestId: String? = null,
    @param:Json(name = "promptTokens") val promptTokens: Int? = null,
    @param:Json(name = "completionTokens") val completionTokens: Int? = null,
    @param:Json(name = "estimatedCostUsd") val estimatedCostUsd: Double? = null,
    @param:Json(name = "currency") val currency: String? = null,
    @param:Json(name = "contextTokensEstimate") val contextTokensEstimate: Int? = null,
    @param:Json(name = "windowSize") val windowSize: Int? = null,
    @param:Json(name = "memoriesUsed") val memoriesUsed: Int? = null,
    @param:Json(name = "hasSummary") val hasSummary: Boolean? = null,
    @param:Json(name = "coinsSpent") val coinsSpent: Int? = null,
    @param:Json(name = "coinBalanceAfter") val coinBalanceAfter: Int? = null,
    @param:Json(name = "coinDebitSkipped") val coinDebitSkipped: Boolean? = null,
    @param:Json(name = "progress") val progress: CharacterProgressDto? = null,
    @param:Json(name = "levelProgressAfter") val levelProgressAfter: CharacterProgressDto? = null,
    @param:Json(name = "abuse") val abuse: SendChatAbuseDto? = null
)

@JsonClass(generateAdapter = true)
data class SendChatAbuseDto(
    @param:Json(name = "status") val status: String? = null,
    @param:Json(name = "warned") val warned: Boolean? = null,
    @param:Json(name = "blocked") val blocked: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class ConversationMessageDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "conversationId") val conversationId: String? = null,
    @param:Json(name = "role") val role: String? = null,
    @param:Json(name = "content") val content: String? = null,
    @param:Json(name = "tokenCount") val tokenCount: Int? = null,
    @param:Json(name = "promptTokens") val promptTokens: Int? = null,
    @param:Json(name = "completionTokens") val completionTokens: Int? = null,
    @param:Json(name = "provider") val provider: String? = null,
    @param:Json(name = "model") val model: String? = null,
    @param:Json(name = "providerRequestId") val providerRequestId: String? = null,
    @param:Json(name = "estimatedCostUsd") val estimatedCostUsd: Double? = null,
    @param:Json(name = "createdAt") val createdAt: String? = null
)
