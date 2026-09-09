package com.pegas.aura.aigirlfriend.soul.data.repository

import com.pegas.aura.aigirlfriend.soul.data.network.model.base.requireData
import com.pegas.aura.aigirlfriend.soul.data.network.model.character.toDomain
import com.pegas.aura.aigirlfriend.soul.data.network.service.CharacterService
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackgroundsData
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategory
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategoryPage
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterPage
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterProgress
import com.pegas.aura.aigirlfriend.soul.domain.model.character.PurchaseBackgroundResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery
import com.pegas.aura.aigirlfriend.soul.domain.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService
) : CharacterRepository {

    override suspend fun getCharacterCategories(query: PaginationQuery): AppResult<CharacterCategoryPage> =
        apiResult("getCharacterCategories") {
            characterService.getCharacterCategories(query.page, query.limit).requireData().toDomain()
        }

    override suspend fun getCharacterCategoryBySlug(slug: String): AppResult<CharacterCategory> =
        apiResult("getCharacterCategoryBySlug") {
            characterService.getCharacterCategoryBySlug(slug).requireData().toDomain()
        }

    override suspend fun getCharacters(query: PaginationQuery): AppResult<CharacterPage> =
        apiResult("getCharacters") {
            characterService.getCharacters(query.page, query.limit).requireData().toDomain()
        }

    override suspend fun getCharactersByCategoryQuery(
        categorySlug: String,
        query: PaginationQuery
    ): AppResult<CharacterPage> = apiResult("getCharactersByCategoryQuery") {
        characterService.getCharactersByCategoryQuery(query.page, query.limit, categorySlug).requireData().toDomain()
    }

    override suspend fun getCharactersByCategoryPath(
        categorySlug: String,
        query: PaginationQuery
    ): AppResult<CharacterPage> = apiResult("getCharactersByCategoryPath") {
        characterService.getCharactersByCategoryPath(categorySlug, query.page, query.limit).requireData().toDomain()
    }

    override suspend fun getCharacterBySlug(slug: String): AppResult<Character> =
        apiResult("getCharacterBySlug") {
            characterService.getCharacterBySlug(slug).requireData().toDomain()
        }

    override suspend fun getCharacterBackgrounds(characterId: String): AppResult<CharacterBackgroundsData> =
        apiResult("getCharacterBackgrounds") {
            characterService.getCharacterBackgrounds(characterId).requireData().toDomain()
        }

    override suspend fun getCharacterProgress(characterId: String): AppResult<CharacterProgress> =
        apiResult("getCharacterProgress") {
            characterService.getCharacterProgress(characterId).requireData().toDomain()
        }

    override suspend fun purchaseCharacterBackground(
        characterId: String,
        backgroundId: String
    ): AppResult<PurchaseBackgroundResult> = apiResult("purchaseCharacterBackground") {
        characterService.purchaseCharacterBackground(characterId, backgroundId).requireData()
            .toDomain()
    }
}

