package com.pegas.aura.aigirlfriend.soul.domain.usecase.mycharacter

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.MyCharacterGenerateImageResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.MyCharacterRepository
import javax.inject.Inject

class GenerateMyCharacterImageUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(input: GenerateMyCharacterImageInput): AppResult<MyCharacterGenerateImageResult> =
        myCharacterRepository.generateMyCharacterImage(input)
}
