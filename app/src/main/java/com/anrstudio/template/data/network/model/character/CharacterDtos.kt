package com.anrstudio.template.data.network.model.character

import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.anrstudio.template.data.network.model.base.PageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias CharactersEnvelopeDto = ApiEnvelopeDto<CharacterPageDto>
typealias CharacterEnvelopeDto = ApiEnvelopeDto<CharacterDto>
typealias CharacterProgressEnvelopeDto = ApiEnvelopeDto<CharacterProgressDto>
typealias CharacterBackgroundsEnvelopeDto = ApiEnvelopeDto<CharacterBackgroundsDataDto>
typealias PurchaseBackgroundEnvelopeDto = ApiEnvelopeDto<PurchaseBackgroundDataDto>
typealias CharacterPageDto = PageDto<CharacterDto>

@JsonClass(generateAdapter = true)
data class CharacterDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "slug") val slug: String? = null,
    @param:Json(name = "image") val image: String? = null,
    @param:Json(name = "description") val description: String? = null,
    @param:Json(name = "task") val task: String? = null,
    @param:Json(name = "tags") val tags: List<String>? = null,
    @param:Json(name = "age") val age: Int? = null,
    @param:Json(name = "gender") val gender: String? = null,
    @param:Json(name = "categoryId") val categoryId: String? = null,
    @param:Json(name = "category") val category: CharacterCategorySummaryDto? = null,
    @param:Json(name = "sort") val sort: Int? = null,
    @param:Json(name = "isHot") val isHot: Boolean? = null,
    @param:Json(name = "likes") val likes: Int? = null,
    @param:Json(name = "ratingStars") val ratingStars: Double? = null,
    @param:Json(name = "ratingCount") val ratingCount: Int? = null,
    @param:Json(name = "createdAt") val createdAt: String? = null,
    @param:Json(name = "updatedAt") val updatedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class CharacterCategorySummaryDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "slug") val slug: String? = null,
    @param:Json(name = "sort") val sort: Int? = null
)

@JsonClass(generateAdapter = true)
data class CharacterProgressDto(
    @param:Json(name = "characterId") val characterId: String? = null,
    @param:Json(name = "xp") val xp: Int? = null,
    @param:Json(name = "level") val level: Int? = null,
    @param:Json(name = "maxLevel") val maxLevel: Int? = null,
    @param:Json(name = "xpPerChat") val xpPerChat: Int? = null,
    @param:Json(name = "xpPerLevel") val xpPerLevel: Int? = null,
    @param:Json(name = "xpToNextLevel") val xpToNextLevel: Int? = null,
    @param:Json(name = "leveledUp") val leveledUp: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class CharacterBackgroundsDataDto(
    @param:Json(name = "progress") val progress: CharacterProgressDto? = null,
    @param:Json(name = "backgrounds") val backgrounds: List<CharacterBackgroundDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CharacterBackgroundDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "characterId") val characterId: String? = null,
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "url") val url: String? = null,
    @param:Json(name = "description") val description: String? = null,
    @param:Json(name = "coinPrice") val coinPrice: Int? = null,
    @param:Json(name = "unlockLevel") val unlockLevel: Int? = null,
    @param:Json(name = "isDefault") val isDefault: Boolean? = null,
    @param:Json(name = "owned") val owned: Boolean? = null,
    @param:Json(name = "unlockBy") val unlockBy: String? = null
)

@JsonClass(generateAdapter = true)
data class PurchaseBackgroundDataDto(
    @param:Json(name = "success") val success: Boolean? = null,
    @param:Json(name = "balance") val balance: Int? = null,
    @param:Json(name = "background") val background: CharacterBackgroundDto? = null
)
