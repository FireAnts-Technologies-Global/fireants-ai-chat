package com.pegas.aura.aigirlfriend.soul.domain.usecase.mycharacter

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.MyCharacterImageStatus
import com.pegas.aura.aigirlfriend.soul.domain.repository.MyCharacterRepository
import javax.inject.Inject

class GetGeneratedMyCharacterImageStatusUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(
        userCharacterId: String,
        imageTaskId: String
    ): AppResult<MyCharacterImageStatus> =
        myCharacterRepository.getGeneratedImageStatus(userCharacterId, imageTaskId)
}
