package com.pegas.yuki.virtual.chat.domain.model.conversation

data class CreateConversationInput(
    val characterId: String,
    val backgroundId: String? = null,
    val title: String? = null
)
