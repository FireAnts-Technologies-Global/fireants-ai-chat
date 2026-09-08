package com.anrstudio.template.data.network.service

import com.anrstudio.template.data.network.model.mycharacter.CreateMyCharacterRequestDto
import com.anrstudio.template.data.network.model.mycharacter.GenerateMyCharacterImageRequestDto
import com.anrstudio.template.data.network.model.mycharacter.MyCharacterEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.MyCharacterGenerateImageEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.MyCharacterImageStatusEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.MyCharactersEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.UpdateMyCharacterRequestDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MyCharacterService {

    @GET("me/characters/guide")
    suspend fun getCreationGuide(): com.anrstudio.template.data.network.model.mycharacter.MyCharacterGuideEnvelopeDto

    @Headers("Content-Type: application/json")
    @POST("me/characters")
    suspend fun createMyCharacter(
        @Body body: CreateMyCharacterRequestDto
    ): MyCharacterEnvelopeDto

    @GET("me/characters")
    suspend fun getMyCharacters(): MyCharactersEnvelopeDto

    @GET("me/characters/{userCharacterId}")
    suspend fun getMyCharacter(
        @Path("userCharacterId") userCharacterId: String
    ): MyCharacterEnvelopeDto

    @Headers("Content-Type: application/json")
    @PATCH("me/characters/{userCharacterId}")
    suspend fun updateMyCharacter(
        @Path("userCharacterId") userCharacterId: String,
        @Body body: UpdateMyCharacterRequestDto
    ): MyCharacterEnvelopeDto

    @Headers("Content-Type: application/json")
    @POST("me/characters/{userCharacterId}/generate-image")
    suspend fun generateMyCharacterImage(
        @Path("userCharacterId") userCharacterId: String,
        @Body body: GenerateMyCharacterImageRequestDto
    ): MyCharacterGenerateImageEnvelopeDto

    @GET("me/characters/{userCharacterId}/generate-image/{imageTaskId}")
    suspend fun getGeneratedImageStatus(
        @Path("userCharacterId") userCharacterId: String,
        @Path("imageTaskId") imageTaskId: String
    ): MyCharacterImageStatusEnvelopeDto

    @POST("me/characters/{userCharacterId}/publish")
    suspend fun publishMyCharacter(
        @Path("userCharacterId") userCharacterId: String
    ): MyCharacterEnvelopeDto

    @DELETE("me/characters/{userCharacterId}")
    suspend fun deleteMyCharacter(
        @Path("userCharacterId") userCharacterId: String
    ): ResponseBody
}
