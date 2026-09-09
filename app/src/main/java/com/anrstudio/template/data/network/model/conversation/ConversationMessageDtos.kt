package com.pegas.aura.aigirlfriend.soul.data.network.model.conversation

import com.pegas.aura.aigirlfriend.soul.data.network.model.base.ApiEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.character.CharacterProgressDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias ConversationMessagesEnvelopeDto = ApiEnvelopeDto<List<ConversationMessageDto>>
typealias SendChatEnvelopeDto = ApiEnvelopeDto<SendChatResponseDto>

@JsonClass(generateAdapter = true)
data class SendChatResponseDto(
    @Json(name = "conversationId") val conversationId: String? = null,
    @Json(name = "backgroundId") val backgroundId: String? = null,
    @Json(name = "userMessage") val userMessage: ConversationMessageDto? = null,
    @Json(name = "assistantMessage") val assistantMessage: ConversationMessageDto? = null,
    @Json(name = "meta") val meta: SendChatMetaDto? = null
)

@JsonClass(generateAdapter = true)
data class SendChatMetaDto(
    @Json(name = "cached") val cached: Boolean? = null,
    @Json(name = "contextMode") val contextMode: String? = null,
    @Json(name = "provider") val provider: String? = null,
    @Json(name = "model") val model: String? = null,
    @Json(name = "providerRequestId") val providerRequestId: String? = null,
    @Json(name = "promptTokens") val promptTokens: Int? = null,
    @Json(name = "completionTokens") val completionTokens: Int? = null,
    @Json(name = "estimatedCostUsd") val estimatedCostUsd: Double? = null,
    @Json(name = "currency") val currency: String? = null,
    @Json(name = "contextTokensEstimate") val contextTokensEstimate: Int? = null,
    @Json(name = "windowSize") val windowSize: Int? = null,
    @Json(name = "memoriesUsed") val memoriesUsed: Int? = null,
    @Json(name = "hasSummary") val hasSummary: Boolean? = null,
    @Json(name = "coinsSpent") val coinsSpent: Int? = null,
    @Json(name = "coinBalanceAfter") val coinBalanceAfter: Int? = null,
    @Json(name = "coinDebitSkipped") val coinDebitSkipped: Boolean? = null,
    @Json(name = "progress") val progress: CharacterProgressDto? = null,
    @Json(name = "levelProgressAfter") val levelProgressAfter: CharacterProgressDto? = null,
    @Json(name = "abuse") val abuse: SendChatAbuseDto? = null
)

@JsonClass(generateAdapter = true)
data class SendChatAbuseDto(
    @Json(name = "status") val status: String? = null,
    @Json(name = "warned") val warned: Boolean? = null,
    @Json(name = "blocked") val blocked: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class ConversationMessageDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "conversationId") val conversationId: String? = null,
    @Json(name = "role") val role: String? = null,
    @Json(name = "content") val content: String? = null,
    @Json(name = "tokenCount") val tokenCount: Int? = null,
    @Json(name = "promptTokens") val promptTokens: Int? = null,
    @Json(name = "completionTokens") val completionTokens: Int? = null,
    @Json(name = "provider") val provider: String? = null,
    @Json(name = "model") val model: String? = null,
    @Json(name = "providerRequestId") val providerRequestId: String? = null,
    @Json(name = "estimatedCostUsd") val estimatedCostUsd: Double? = null,
    @Json(name = "createdAt") val createdAt: String? = null
)
