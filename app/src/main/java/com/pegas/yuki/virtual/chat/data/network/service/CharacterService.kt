package com.pegas.yuki.virtual.chat.data.network.service

import com.pegas.yuki.virtual.chat.data.network.model.character.CharacterBackgroundsEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.character.CharacterCategoriesEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.character.CharacterCategoryEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.character.CharacterEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.character.CharacterProgressEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.character.CharactersEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.character.PurchaseBackgroundEnvelopeDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("character-categories")
    suspend fun getCharacterCategories(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): CharacterCategoriesEnvelopeDto

    @GET("character-categories/{slug}")
    suspend fun getCharacterCategoryBySlug(
        @Path("slug") slug: String
    ): CharacterCategoryEnvelopeDto

    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): CharactersEnvelopeDto

    @GET("characters")
    suspend fun getCharactersByCategoryQuery(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("categorySlug") categorySlug: String
    ): CharactersEnvelopeDto

    @GET("character-categories/{slug}/characters")
    suspend fun getCharactersByCategoryPath(
        @Path("slug") slug: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): CharactersEnvelopeDto

    @GET("characters/{slug}")
    suspend fun getCharacterBySlug(
        @Path("slug") slug: String
    ): CharacterEnvelopeDto

    @GET("characters/{characterId}/backgrounds")
    suspend fun getCharacterBackgrounds(
        @Path("characterId") characterId: String
    ): CharacterBackgroundsEnvelopeDto

    @GET("characters/{characterId}/progress")
    suspend fun getCharacterProgress(
        @Path("characterId") characterId: String
    ): CharacterProgressEnvelopeDto

    @POST("characters/{characterId}/backgrounds/{backgroundId}/purchase")
    suspend fun purchaseCharacterBackground(
        @Path("characterId") characterId: String,
        @Path("backgroundId") backgroundId: String
    ): PurchaseBackgroundEnvelopeDto
}
