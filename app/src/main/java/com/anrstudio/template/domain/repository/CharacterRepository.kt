package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackgroundsData
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategory
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategoryPage
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterPage
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterProgress
import com.pegas.aura.aigirlfriend.soul.domain.model.character.PurchaseBackgroundResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery

interface CharacterRepository {
    suspend fun getCharacterCategories(query: PaginationQuery): AppResult<CharacterCategoryPage>

    suspend fun getCharacterCategoryBySlug(slug: String): AppResult<CharacterCategory>

    suspend fun getCharacters(query: PaginationQuery): AppResult<CharacterPage>

    suspend fun getCharactersByCategoryQuery(categorySlug: String, query: PaginationQuery): AppResult<CharacterPage>

    suspend fun getCharactersByCategoryPath(categorySlug: String, query: PaginationQuery): AppResult<CharacterPage>

    suspend fun getCharacterBySlug(slug: String): AppResult<Character>

    suspend fun getCharacterBackgrounds(characterId: String): AppResult<CharacterBackgroundsData>

    suspend fun getCharacterProgress(characterId: String): AppResult<CharacterProgress>

    suspend fun purchaseCharacterBackground(
        characterId: String,
        backgroundId: String
    ): AppResult<PurchaseBackgroundResult>
}
