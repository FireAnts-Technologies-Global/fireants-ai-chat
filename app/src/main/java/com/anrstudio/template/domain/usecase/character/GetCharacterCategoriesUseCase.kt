package com.anrstudio.template.domain.usecase.character

import com.anrstudio.template.domain.model.character.CharacterCategoryPage
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PaginationQuery
import com.anrstudio.template.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterCategoriesUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(query: PaginationQuery): AppResult<CharacterCategoryPage> =
        characterRepository.getCharacterCategories(query)
}
