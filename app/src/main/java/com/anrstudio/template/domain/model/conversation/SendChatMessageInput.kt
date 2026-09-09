package com.pegas.aura.aigirlfriend.soul.domain.model.conversation

data class SendChatMessageInput(
    val conversationId: String,
    val content: String? = null,
    val quickPromptId: String? = null
)
