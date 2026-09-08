package com.anrstudio.template.domain.usecase.character

import com.anrstudio.template.domain.model.character.CharacterPage
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PaginationQuery
import com.anrstudio.template.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersByCategoryQueryUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(
        categorySlug: String,
        query: PaginationQuery
    ): AppResult<CharacterPage> =
        characterRepository.getCharactersByCategoryQuery(categorySlug, query)
}
