package com.anrstudio.template.domain.model.conversation

import com.anrstudio.template.domain.model.character.Character
import com.anrstudio.template.domain.model.character.CharacterBackground

typealias ConversationCharacterSummary = Character

data class ConversationSummary(
    val id: String,
    val userId: String,
    val characterId: String,
    val backgroundId: String? = null,
    val title: String?,
    val lastMessagePreview: String? = null,
    val lastMessageAt: String?,
    val createdAt: String,
    val updatedAt: String,
    val character: Character? = null,
    val background: CharacterBackground? = null
)
