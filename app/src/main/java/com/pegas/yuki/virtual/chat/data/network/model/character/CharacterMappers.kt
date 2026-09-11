package com.pegas.yuki.virtual.chat.data.network.model.character

import com.pegas.yuki.virtual.chat.data.network.model.base.toDomain
import com.pegas.yuki.virtual.chat.domain.model.character.Character
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterBackground
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterBackgroundsData
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterCategorySummary
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterPage
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterProgress
import com.pegas.yuki.virtual.chat.domain.model.character.PurchaseBackgroundResult

fun CharacterPageDto.toDomain(): CharacterPage = toDomain { it.toDomain() }

fun CharacterDto.toDomain(): Character = Character(
    id = id.orEmpty(),
    name = name.orEmpty(),
    slug = slug.orEmpty(),
    image = image,
    description = description.orEmpty(),
    task = task.orEmpty(),
    tags = tags ?: emptyList(),
    age = age,
    gender = gender,
    categoryId = categoryId.orEmpty(),
    category = category?.toDomain(),
    sort = sort ?: 0,
    isHot = isHot == true,
    likes = likes ?: 0,
    ratingStars = ratingStars,
    ratingCount = ratingCount ?: 0,
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty()
)

private fun CharacterCategorySummaryDto.toDomain(): CharacterCategorySummary = CharacterCategorySummary(
    id = id.orEmpty(),
    name = name.orEmpty(),
    slug = slug.orEmpty(),
    sort = sort ?: 0
)

fun CharacterProgressDto.toDomain(): CharacterProgress = CharacterProgress(
    characterId = characterId.orEmpty(),
    xp = xp ?: 0,
    level = level ?: 1,
    maxLevel = maxLevel ?: 50,
    xpPerChat = xpPerChat ?: 1,
    xpPerLevel = xpPerLevel ?: 10,
    xpToNextLevel = xpToNextLevel ?: 10,
    leveledUp = leveledUp == true
)

fun CharacterBackgroundDto.toDomain(): CharacterBackground = CharacterBackground(
    id = id.orEmpty(),
    characterId = characterId.orEmpty(),
    name = name.orEmpty(),
    imageUrl = url.orEmpty(),
    description = description.orEmpty(),
    priceCoins = coinPrice ?: 0,
    unlockLevel = unlockLevel ?: 1,
    isDefault = isDefault == true,
    isLocked = owned != true && isDefault != true,
    isUnlocked = owned == true || isDefault == true
)

fun CharacterBackgroundsDataDto.toDomain(): CharacterBackgroundsData = CharacterBackgroundsData(
    progress = progress?.toDomain(),
    backgrounds = backgrounds.map { it.toDomain() }
)

fun PurchaseBackgroundDataDto.toDomain(): PurchaseBackgroundResult = PurchaseBackgroundResult(
    success = success == true,
    coinBalance = balance ?: 0,
    background = background?.toDomain()
)
