package com.anrstudio.template.data.network.model.conversation

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateConversationRequestDto(
    @param:Json(name = "characterId") val characterId: String,
    @param:Json(name = "backgroundId") val backgroundId: String? = null,
    @param:Json(name = "title") val title: String? = null
)

@JsonClass(generateAdapter = true)
data class UpdateConversationRequestDto(
    @param:Json(name = "backgroundId") val backgroundId: String? = null,
    @param:Json(name = "title") val title: String? = null
)

@JsonClass(generateAdapter = true)
data class SendChatMessageRequestDto(
    @param:Json(name = "content") val content: String? = null,
    @param:Json(name = "quickPromptId") val quickPromptId: String? = null
)
