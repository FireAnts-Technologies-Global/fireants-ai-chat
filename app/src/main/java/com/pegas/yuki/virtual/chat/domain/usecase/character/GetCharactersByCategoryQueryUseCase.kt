package com.pegas.yuki.virtual.chat.domain.usecase.character

import com.pegas.yuki.virtual.chat.domain.model.character.CharacterPage
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery
import com.pegas.yuki.virtual.chat.domain.repository.CharacterRepository
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
