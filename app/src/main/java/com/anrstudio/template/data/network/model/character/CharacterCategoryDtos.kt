package com.anrstudio.template.data.network.model.character

import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.anrstudio.template.data.network.model.base.PageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias CharacterCategoriesEnvelopeDto = ApiEnvelopeDto<CharacterCategoryPageDto>
typealias CharacterCategoryEnvelopeDto = ApiEnvelopeDto<CharacterCategoryDto>
typealias CharacterCategoryPageDto = PageDto<CharacterCategoryDto>

@JsonClass(generateAdapter = true)
data class CharacterCategoryDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "slug") val slug: String? = null,
    @param:Json(name = "description") val description: String? = null,
    @param:Json(name = "sort") val sort: Int? = null,
    @param:Json(name = "isActive") val isActive: Boolean? = null,
    @param:Json(name = "createdAt") val createdAt: String? = null,
    @param:Json(name = "updatedAt") val updatedAt: String? = null
)
