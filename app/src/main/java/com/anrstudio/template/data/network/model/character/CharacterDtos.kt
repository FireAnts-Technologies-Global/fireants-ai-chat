package com.pegas.aura.aigirlfriend.soul.data.network.model.character

import com.pegas.aura.aigirlfriend.soul.data.network.model.base.ApiEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.base.PageDto
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
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "slug") val slug: String? = null,
    @Json(name = "image") val image: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "task") val task: String? = null,
    @Json(name = "tags") val tags: List<String>? = null,
    @Json(name = "age") val age: Int? = null,
    @Json(name = "gender") val gender: String? = null,
    @Json(name = "categoryId") val categoryId: String? = null,
    @Json(name = "category") val category: CharacterCategorySummaryDto? = null,
    @Json(name = "sort") val sort: Int? = null,
    @Json(name = "isHot") val isHot: Boolean? = null,
    @Json(name = "likes") val likes: Int? = null,
    @Json(name = "ratingStars") val ratingStars: Double? = null,
    @Json(name = "ratingCount") val ratingCount: Int? = null,
    @Json(name = "createdAt") val createdAt: String? = null,
    @Json(name = "updatedAt") val updatedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class CharacterCategorySummaryDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "slug") val slug: String? = null,
    @Json(name = "sort") val sort: Int? = null
)

@JsonClass(generateAdapter = true)
data class CharacterProgressDto(
    @Json(name = "characterId") val characterId: String? = null,
    @Json(name = "xp") val xp: Int? = null,
    @Json(name = "level") val level: Int? = null,
    @Json(name = "maxLevel") val maxLevel: Int? = null,
    @Json(name = "xpPerChat") val xpPerChat: Int? = null,
    @Json(name = "xpPerLevel") val xpPerLevel: Int? = null,
    @Json(name = "xpToNextLevel") val xpToNextLevel: Int? = null,
    @Json(name = "leveledUp") val leveledUp: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class CharacterBackgroundsDataDto(
    @Json(name = "progress") val progress: CharacterProgressDto? = null,
    @Json(name = "backgrounds") val backgrounds: List<CharacterBackgroundDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CharacterBackgroundDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "characterId") val characterId: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "coinPrice") val coinPrice: Int? = null,
    @Json(name = "unlockLevel") val unlockLevel: Int? = null,
    @Json(name = "isDefault") val isDefault: Boolean? = null,
    @Json(name = "owned") val owned: Boolean? = null,
    @Json(name = "unlockBy") val unlockBy: String? = null
)

@JsonClass(generateAdapter = true)
data class PurchaseBackgroundDataDto(
    @Json(name = "success") val success: Boolean? = null,
    @Json(name = "balance") val balance: Int? = null,
    @Json(name = "background") val background: CharacterBackgroundDto? = null
)
