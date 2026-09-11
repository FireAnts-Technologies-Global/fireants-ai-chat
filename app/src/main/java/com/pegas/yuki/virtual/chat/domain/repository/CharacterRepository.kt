package com.pegas.yuki.virtual.chat.domain.repository

import com.pegas.yuki.virtual.chat.domain.model.character.Character
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterBackgroundsData
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterCategory
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterCategoryPage
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterPage
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterProgress
import com.pegas.yuki.virtual.chat.domain.model.character.PurchaseBackgroundResult
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery

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
