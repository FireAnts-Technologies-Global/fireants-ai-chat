package com.pegas.yuki.virtual.chat.data.repository

import com.pegas.yuki.virtual.chat.data.network.model.mycharacter.MyCharacterEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.mycharacter.MyCharacterGenerateImageEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.mycharacter.MyCharacterGuideEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.mycharacter.MyCharacterImageStatusEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.mycharacter.MyCharactersEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.mycharacter.toDomain
import com.pegas.yuki.virtual.chat.data.network.model.mycharacter.toRequestDto
import com.pegas.yuki.virtual.chat.data.network.service.MyCharacterService
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.CreateMyCharacterInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacter
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterCreationGuide
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterGenerateImageResult
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterImageStatus
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.UpdateMyCharacterInput
import com.pegas.yuki.virtual.chat.domain.repository.AuthRepository
import com.pegas.yuki.virtual.chat.domain.repository.MyCharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyCharacterRepositoryImpl @Inject constructor(
    private val myCharacterService: MyCharacterService,
    private val authRepository: AuthRepository
) : MyCharacterRepository {

    override suspend fun getCreationGuide(): AppResult<MyCharacterCreationGuide> =
        apiResult("getCreationGuide") {
            myCharacterService.getCreationGuide().requireData().toDomain()
        }

    override suspend fun createMyCharacter(input: CreateMyCharacterInput): AppResult<MyCharacter> =
        apiResult("createMyCharacter") {
            myCharacterService.createMyCharacter(input.toRequestDto()).requireData().toDomain()
        }

    override suspend fun getMyCharacters(): AppResult<List<MyCharacter>> =
        apiResult("getMyCharacters") {
            myCharacterService.getMyCharacters().requireData().map { it.toDomain() }
        }

    override suspend fun getMyCharacter(userCharacterId: String): AppResult<MyCharacter> =
        apiResult("getMyCharacter") {
            myCharacterService.getMyCharacter(userCharacterId).requireData().toDomain()
        }

    override suspend fun updateMyCharacter(input: UpdateMyCharacterInput): AppResult<MyCharacter> =
        apiResult("updateMyCharacter") {
            myCharacterService.updateMyCharacter(
                userCharacterId = input.userCharacterId,
                body = input.toRequestDto()
            ).requireData().toDomain()
        }

    override suspend fun generateMyCharacterImage(input: GenerateMyCharacterImageInput): AppResult<MyCharacterGenerateImageResult> =
        apiResult("generateMyCharacterImage") {
            myCharacterService.generateMyCharacterImage(
                userCharacterId = input.userCharacterId,
                body = input.toRequestDto()
            ).requireData().toDomain().also { result ->
                result.balanceAfter?.let(authRepository::updateLocalCoinBalance)
            }
        }

    override suspend fun getGeneratedImageStatus(
        userCharacterId: String,
        imageTaskId: String
    ): AppResult<MyCharacterImageStatus> =
        apiResult("getGeneratedImageStatus") {
            myCharacterService.getGeneratedImageStatus(
                userCharacterId = userCharacterId,
                imageTaskId = imageTaskId
            ).requireData().toDomain()
        }

    override suspend fun publishMyCharacter(userCharacterId: String): AppResult<MyCharacter> =
        apiResult("publishMyCharacter") {
            myCharacterService.publishMyCharacter(userCharacterId).requireData().toDomain()
        }

    override suspend fun deleteMyCharacter(userCharacterId: String): AppResult<Boolean> =
        apiResult("deleteMyCharacter") {
            myCharacterService.deleteMyCharacter(userCharacterId).close()
            true
        }
}

private fun MyCharacterGuideEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing my character guide data")

private fun MyCharacterEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing my character data")

private fun MyCharactersEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing my characters data")

private fun MyCharacterGenerateImageEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing generated image data")

private fun MyCharacterImageStatusEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing generated image status data")
