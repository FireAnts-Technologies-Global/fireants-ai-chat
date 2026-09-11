package com.pegas.yuki.virtual.chat.data.network.model.character

import com.pegas.yuki.virtual.chat.data.network.model.base.toDomain
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterCategory
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterCategoryPage

fun CharacterCategoryPageDto.toDomain(): CharacterCategoryPage = toDomain { it.toDomain() }

fun CharacterCategoryDto.toDomain(): CharacterCategory = CharacterCategory(
    id = id.orEmpty(),
    name = name.orEmpty(),
    slug = slug.orEmpty(),
    description = description.orEmpty(),
    sort = sort ?: 0,
    isActive = isActive == true,
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty()
)
