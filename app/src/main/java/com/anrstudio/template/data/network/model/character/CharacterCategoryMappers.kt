package com.anrstudio.template.data.network.model.character

import com.anrstudio.template.data.network.model.base.toDomain
import com.anrstudio.template.domain.model.character.CharacterCategory
import com.anrstudio.template.domain.model.character.CharacterCategoryPage

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
