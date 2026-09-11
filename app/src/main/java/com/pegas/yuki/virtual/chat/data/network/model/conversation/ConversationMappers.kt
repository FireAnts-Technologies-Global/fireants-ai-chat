package com.pegas.yuki.virtual.chat.data.network.model.conversation

import com.pegas.yuki.virtual.chat.data.network.model.character.toDomain
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationCharacterSummary
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationSummary

fun ConversationDto.toDomain(): ConversationSummary = ConversationSummary(
    id = id.orEmpty(),
    userId = userId.orEmpty(),
    characterId = characterId.orEmpty(),
    backgroundId = backgroundId,
    title = title,
    lastMessagePreview = null,
    lastMessageAt = lastMessageAt,
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty(),
    character = character?.toDomain(),
    background = background?.toDomain()
)

fun ConversationSummary.withCharacterFallback(
    characterId: String,
    title: String?
): ConversationSummary = copy(
    characterId = this.characterId.ifBlank { characterId },
    title = this.title ?: title
)

private fun ConversationCharacterDto.toDomain(): ConversationCharacterSummary = ConversationCharacterSummary(
    id = id.orEmpty(),
    slug = slug.orEmpty(),
    name = name.orEmpty(),
    image = image,
    description = description.orEmpty(),
    task = task.orEmpty(),
    tags = tags.orEmpty(),
    age = age,
    gender = gender,
    categoryId = categoryId.orEmpty(),
    isHot = isHot == true
)
