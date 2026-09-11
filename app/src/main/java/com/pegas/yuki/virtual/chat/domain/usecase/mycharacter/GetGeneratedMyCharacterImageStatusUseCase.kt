package com.pegas.yuki.virtual.chat.domain.usecase.mycharacter

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterImageStatus
import com.pegas.yuki.virtual.chat.domain.repository.MyCharacterRepository
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
