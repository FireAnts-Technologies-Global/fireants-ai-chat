package com.pegas.yuki.virtual.chat.domain.model.conversation

data class UpdateConversationInput(
    val conversationId: String,
    val backgroundId: String? = null,
    val title: String? = null
)
