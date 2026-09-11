package com.pegas.yuki.virtual.chat.domain.usecase.character

import com.pegas.yuki.virtual.chat.domain.model.character.CharacterCategory
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterCategoryBySlugUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(slug: String): AppResult<CharacterCategory> =
        characterRepository.getCharacterCategoryBySlug(slug)
}
