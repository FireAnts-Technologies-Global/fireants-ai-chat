package com.anrstudio.template.domain.repository

import com.anrstudio.template.domain.model.character.Character
import com.anrstudio.template.domain.model.character.CharacterBackgroundsData
import com.anrstudio.template.domain.model.character.CharacterCategory
import com.anrstudio.template.domain.model.character.CharacterCategoryPage
import com.anrstudio.template.domain.model.character.CharacterPage
import com.anrstudio.template.domain.model.character.CharacterProgress
import com.anrstudio.template.domain.model.character.PurchaseBackgroundResult
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PaginationQuery

interface CharacterRepository {
    suspend fun getCharacterCategories(query: PaginationQuery): AppResult<CharacterCategoryPage>

    suspend fun getCharacterCategoryBySlug(slug: String): AppResult<CharacterCategory>

    suspend fun getCharacters(query: PaginationQuery): AppResult<CharacterPage>

    suspend fun getCharactersByCategoryQuery(
        categorySlug: String,
        query: PaginationQuery
    ): AppResult<CharacterPage>

    suspend fun getCharactersByCategoryPath(
        categorySlug: String,
        query: PaginationQuery
    ): AppResult<CharacterPage>

    suspend fun getCharacterBySlug(slug: String): AppResult<Character>

    suspend fun getCharacterBackgrounds(characterId: String): AppResult<CharacterBackgroundsData>

    suspend fun getCharacterProgress(characterId: String): AppResult<CharacterProgress>

    suspend fun purchaseCharacterBackground(
        characterId: String,
        backgroundId: String
    ): AppResult<PurchaseBackgroundResult>
}
