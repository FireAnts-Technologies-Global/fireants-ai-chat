package com.anrstudio.template.domain.usecase.character

import com.anrstudio.template.domain.model.character.CharacterBackgroundsData
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterBackgroundsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: String): AppResult<CharacterBackgroundsData> =
        characterRepository.getCharacterBackgrounds(characterId)
}
