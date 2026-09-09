package com.pegas.aura.aigirlfriend.soul.domain.model.conversation

import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterProgress

data class ConversationMessage(
    val id: String,
    val conversationId: String,
    val role: ConversationMessageRole,
    val content: String,
    val tokenCount: Int,
    val promptTokens: Int,
    val completionTokens: Int,
    val provider: String?,
    val model: String?,
    val providerRequestId: String? = null,
    val estimatedCostUsd: Double,
    val createdAt: String
)

enum class ConversationMessageRole {
    USER,
    ASSISTANT,
    SYSTEM,
    UNKNOWN
}

data class SendChatResult(
    val conversationId: String,
    val userMessage: ConversationMessage,
    val assistantMessage: ConversationMessage,
    val coinBalanceAfter: Int?,
    val progress: CharacterProgress? = null
)
