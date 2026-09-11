package com.pegas.yuki.virtual.chat.domain.usecase.mycharacter

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterCreationGuide
import com.pegas.yuki.virtual.chat.domain.repository.MyCharacterRepository
import javax.inject.Inject

class GetMyCharacterCreationGuideUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(): AppResult<MyCharacterCreationGuide> =
        myCharacterRepository.getCreationGuide()
}
