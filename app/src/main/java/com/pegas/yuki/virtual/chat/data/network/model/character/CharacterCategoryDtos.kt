package com.pegas.yuki.virtual.chat.data.network.model.character

import com.pegas.yuki.virtual.chat.data.network.model.base.ApiEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.base.PageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias CharacterCategoriesEnvelopeDto = ApiEnvelopeDto<CharacterCategoryPageDto>
typealias CharacterCategoryEnvelopeDto = ApiEnvelopeDto<CharacterCategoryDto>
typealias CharacterCategoryPageDto = PageDto<CharacterCategoryDto>

@JsonClass(generateAdapter = true)
data class CharacterCategoryDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "slug") val slug: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "sort") val sort: Int? = null,
    @Json(name = "isActive") val isActive: Boolean? = null,
    @Json(name = "createdAt") val createdAt: String? = null,
    @Json(name = "updatedAt") val updatedAt: String? = null
)
