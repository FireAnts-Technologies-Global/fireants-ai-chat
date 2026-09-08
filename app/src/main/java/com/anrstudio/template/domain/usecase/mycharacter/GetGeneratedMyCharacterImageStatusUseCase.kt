package com.anrstudio.template.domain.usecase.mycharacter

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.mycharacter.MyCharacterImageStatus
import com.anrstudio.template.domain.repository.MyCharacterRepository
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
