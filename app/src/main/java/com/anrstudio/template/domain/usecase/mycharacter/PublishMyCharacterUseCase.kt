package com.anrstudio.template.domain.usecase.mycharacter

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.mycharacter.MyCharacter
import com.anrstudio.template.domain.repository.MyCharacterRepository
import javax.inject.Inject

class PublishMyCharacterUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(userCharacterId: String): AppResult<MyCharacter> =
        myCharacterRepository.publishMyCharacter(userCharacterId)
}
