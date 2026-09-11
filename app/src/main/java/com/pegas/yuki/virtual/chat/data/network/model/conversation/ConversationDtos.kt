package com.pegas.yuki.virtual.chat.data.network.model.conversation

import com.pegas.yuki.virtual.chat.data.network.model.base.ApiEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.character.CharacterBackgroundDto
import com.pegas.yuki.virtual.chat.data.network.model.character.CharacterDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias ConversationsEnvelopeDto = ApiEnvelopeDto<List<ConversationDto>>
typealias ConversationEnvelopeDto = ApiEnvelopeDto<ConversationDto>
typealias DeleteConversationEnvelopeDto = ApiEnvelopeDto<DeleteConversationDto>
typealias ConversationCharacterDto = CharacterDto

@JsonClass(generateAdapter = true)
data class ConversationDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "userId") val userId: String? = null,
    @Json(name = "characterId") val characterId: String? = null,
    @Json(name = "backgroundId") val backgroundId: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "lastMessageAt") val lastMessageAt: String? = null,
    @Json(name = "createdAt") val createdAt: String? = null,
    @Json(name = "updatedAt") val updatedAt: String? = null,
    @Json(name = "character") val character: CharacterDto? = null,
    @Json(name = "background") val background: CharacterBackgroundDto? = null
)

@JsonClass(generateAdapter = true)
data class DeleteConversationDto(
    @Json(name = "deleted") val deleted: Boolean? = null
)
