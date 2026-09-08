package com.anrstudio.template.data.network.model.conversation


import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.anrstudio.template.data.network.model.character.CharacterBackgroundDto
import com.anrstudio.template.data.network.model.character.CharacterDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias ConversationsEnvelopeDto = ApiEnvelopeDto<List<ConversationDto>>
typealias ConversationEnvelopeDto = ApiEnvelopeDto<ConversationDto>
typealias DeleteConversationEnvelopeDto = ApiEnvelopeDto<DeleteConversationDto>
typealias ConversationCharacterDto = CharacterDto

@JsonClass(generateAdapter = true)
data class ConversationDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "userId") val userId: String? = null,
    @param:Json(name = "characterId") val characterId: String? = null,
    @param:Json(name = "backgroundId") val backgroundId: String? = null,
    @param:Json(name = "title") val title: String? = null,
    @param:Json(name = "lastMessageAt") val lastMessageAt: String? = null,
    @param:Json(name = "createdAt") val createdAt: String? = null,
    @param:Json(name = "updatedAt") val updatedAt: String? = null,
    @param:Json(name = "character") val character: CharacterDto? = null,
    @param:Json(name = "background") val background: CharacterBackgroundDto? = null
)

@JsonClass(generateAdapter = true)
data class DeleteConversationDto(
    @param:Json(name = "deleted") val deleted: Boolean? = null
)
