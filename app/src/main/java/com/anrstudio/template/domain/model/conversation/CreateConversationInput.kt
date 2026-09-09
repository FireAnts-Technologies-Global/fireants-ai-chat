package com.pegas.aura.aigirlfriend.soul.domain.model.conversation

data class CreateConversationInput(
    val characterId: String,
    val backgroundId: String? = null,
    val title: String? = null
)
