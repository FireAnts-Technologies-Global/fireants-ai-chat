package com.pegas.aura.aigirlfriend.soul.domain.model.conversation

data class UpdateConversationInput(
    val conversationId: String,
    val backgroundId: String? = null,
    val title: String? = null
)
