package com.pegas.yuki.virtual.chat.data.network.model.conversation

import com.pegas.yuki.virtual.chat.data.network.model.base.ApiEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.base.PageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias QuickPromptsEnvelopeDto = ApiEnvelopeDto<QuickPromptPageDto>
typealias QuickPromptPageDto = PageDto<QuickPromptDto>

@JsonClass(generateAdapter = true)
data class QuickPromptDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "content") val content: String? = null,
    @Json(name = "iconUrl") val iconUrl: String? = null,
    @Json(name = "icon") val icon: String? = null,
    @Json(name = "coinCost") val coinCost: Int? = null,
    @Json(name = "sortOrder") val sortOrder: Int? = null
)
