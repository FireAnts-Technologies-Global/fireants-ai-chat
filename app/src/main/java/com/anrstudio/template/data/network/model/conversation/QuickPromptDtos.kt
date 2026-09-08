package com.anrstudio.template.data.network.model.conversation

import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.anrstudio.template.data.network.model.base.PageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias QuickPromptsEnvelopeDto = ApiEnvelopeDto<QuickPromptPageDto>
typealias QuickPromptPageDto = PageDto<QuickPromptDto>

@JsonClass(generateAdapter = true)
data class QuickPromptDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "title") val title: String? = null,
    @param:Json(name = "content") val content: String? = null,
    @param:Json(name = "iconUrl") val iconUrl: String? = null,
    @param:Json(name = "icon") val icon: String? = null,
    @param:Json(name = "coinCost") val coinCost: Int? = null,
    @param:Json(name = "sortOrder") val sortOrder: Int? = null
)
