package com.anrstudio.template.data.repository

import com.anrstudio.template.data.network.model.mycharacter.MyCharacterEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.MyCharacterGenerateImageEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.MyCharacterGuideEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.MyCharacterImageStatusEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.MyCharactersEnvelopeDto
import com.anrstudio.template.data.network.model.mycharacter.toDomain
import com.anrstudio.template.data.network.model.mycharacter.toRequestDto
import com.anrstudio.template.data.network.service.MyCharacterService
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.mycharacter.CreateMyCharacterInput
import com.anrstudio.template.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.anrstudio.template.domain.model.mycharacter.MyCharacter
import com.anrstudio.template.domain.model.mycharacter.MyCharacterCreationGuide
import com.anrstudio.template.domain.model.mycharacter.MyCharacterGenerateImageResult
import com.anrstudio.template.domain.model.mycharacter.MyCharacterImageStatus
import com.anrstudio.template.domain.model.mycharacter.UpdateMyCharacterInput
import com.anrstudio.template.domain.repository.AuthRepository
import com.anrstudio.template.domain.repository.MyCharacterRepository
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
