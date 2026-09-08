package com.anrstudio.template.domain.usecase.character

import com.anrstudio.template.domain.model.character.CharacterCategory
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterCategoryBySlugUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(slug: String): AppResult<CharacterCategory> =
        characterRepository.getCharacterCategoryBySlug(slug)
}
