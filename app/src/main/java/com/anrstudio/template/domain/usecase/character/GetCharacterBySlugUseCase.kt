package com.anrstudio.template.domain.usecase.character

import com.anrstudio.template.domain.model.character.Character
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterBySlugUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(slug: String): AppResult<Character> =
        characterRepository.getCharacterBySlug(slug)
}
