package com.pegas.yuki.virtual.chat.domain.model.conversation

data class SendChatMessageInput(
    val conversationId: String,
    val content: String? = null,
    val quickPromptId: String? = null
)
