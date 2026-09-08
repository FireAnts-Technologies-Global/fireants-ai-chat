package com.anrstudio.template.domain.usecase.mycharacter

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.anrstudio.template.domain.model.mycharacter.MyCharacterGenerateImageResult
import com.anrstudio.template.domain.repository.MyCharacterRepository
import javax.inject.Inject

class GenerateMyCharacterImageUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(input: GenerateMyCharacterImageInput): AppResult<MyCharacterGenerateImageResult> =
        myCharacterRepository.generateMyCharacterImage(input)
}
