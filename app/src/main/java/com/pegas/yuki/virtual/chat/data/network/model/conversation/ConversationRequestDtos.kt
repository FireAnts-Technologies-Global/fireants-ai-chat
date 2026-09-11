package com.pegas.yuki.virtual.chat.data.network.model.conversation

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateConversationRequestDto(
    @Json(name = "characterId") val characterId: String,
    @Json(name = "backgroundId") val backgroundId: String? = null,
    @Json(name = "title") val title: String? = null
)

@JsonClass(generateAdapter = true)
data class UpdateConversationRequestDto(
    @Json(name = "backgroundId") val backgroundId: String? = null,
    @Json(name = "title") val title: String? = null
)

@JsonClass(generateAdapter = true)
data class SendChatMessageRequestDto(
    @Json(name = "content") val content: String? = null,
    @Json(name = "quickPromptId") val quickPromptId: String? = null
)
