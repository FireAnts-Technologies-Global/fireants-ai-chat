package com.anrstudio.template.domain.usecase.mycharacter

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.mycharacter.MyCharacterCreationGuide
import com.anrstudio.template.domain.repository.MyCharacterRepository
import javax.inject.Inject

class GetMyCharacterCreationGuideUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(): AppResult<MyCharacterCreationGuide> =
        myCharacterRepository.getCreationGuide()
}
