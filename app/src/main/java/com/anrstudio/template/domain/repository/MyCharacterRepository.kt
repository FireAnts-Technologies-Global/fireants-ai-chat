package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.CreateMyCharacterInput
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.MyCharacter
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.MyCharacterCreationGuide
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.MyCharacterGenerateImageResult
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.MyCharacterImageStatus
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.UpdateMyCharacterInput

interface MyCharacterRepository {
    suspend fun getCreationGuide(): AppResult<MyCharacterCreationGuide>

    suspend fun createMyCharacter(input: CreateMyCharacterInput): AppResult<MyCharacter>

    suspend fun getMyCharacters(): AppResult<List<MyCharacter>>

    suspend fun getMyCharacter(userCharacterId: String): AppResult<MyCharacter>

    suspend fun updateMyCharacter(input: UpdateMyCharacterInput): AppResult<MyCharacter>

    suspend fun generateMyCharacterImage(input: GenerateMyCharacterImageInput): AppResult<MyCharacterGenerateImageResult>

    suspend fun getGeneratedImageStatus(
        userCharacterId: String,
        imageTaskId: String
    ): AppResult<MyCharacterImageStatus>

    suspend fun publishMyCharacter(userCharacterId: String): AppResult<MyCharacter>

    suspend fun deleteMyCharacter(userCharacterId: String): AppResult<Boolean>
}
