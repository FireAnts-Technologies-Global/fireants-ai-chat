package com.pegas.yuki.virtual.chat.domain.usecase.mycharacter

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterGenerateImageResult
import com.pegas.yuki.virtual.chat.domain.repository.MyCharacterRepository
import javax.inject.Inject

class GenerateMyCharacterImageUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(input: GenerateMyCharacterImageInput): AppResult<MyCharacterGenerateImageResult> =
        myCharacterRepository.generateMyCharacterImage(input)
}
