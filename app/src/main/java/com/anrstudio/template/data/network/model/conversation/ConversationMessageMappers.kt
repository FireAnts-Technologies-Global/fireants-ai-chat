package com.pegas.aura.aigirlfriend.soul.data.network.model.conversation

import com.pegas.aura.aigirlfriend.soul.data.network.model.character.toDomain
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationMessage
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationMessageRole
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.SendChatResult

fun ConversationMessageDto.toDomain(): ConversationMessage = ConversationMessage(
    id = id.orEmpty(),
    conversationId = conversationId.orEmpty(),
    role = role.toConversationMessageRole(),
    content = content.orEmpty(),
    tokenCount = tokenCount ?: 0,
    promptTokens = promptTokens ?: 0,
    completionTokens = completionTokens ?: 0,
    provider = provider,
    model = model,
    providerRequestId = providerRequestId,
    estimatedCostUsd = estimatedCostUsd ?: 0.0,
    createdAt = createdAt.orEmpty()
)

fun SendChatResponseDto.toDomain(): SendChatResult = SendChatResult(
    conversationId = conversationId ?: userMessage?.conversationId
    ?: assistantMessage?.conversationId.orEmpty(),
    userMessage = userMessage?.toDomain() ?: error("Missing user message"),
    assistantMessage = assistantMessage?.toDomain() ?: error("Missing assistant message"),
    coinBalanceAfter = meta?.coinBalanceAfter,
    progress = (meta?.progress ?: meta?.levelProgressAfter)?.toDomain()
)

private fun String?.toConversationMessageRole(): ConversationMessageRole = when (this) {
    "USER" -> ConversationMessageRole.USER
    "ASSISTANT" -> ConversationMessageRole.ASSISTANT
    "SYSTEM" -> ConversationMessageRole.SYSTEM
    else -> ConversationMessageRole.UNKNOWN
}
