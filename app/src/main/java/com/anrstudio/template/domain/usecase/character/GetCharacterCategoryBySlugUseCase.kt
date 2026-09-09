package com.pegas.aura.aigirlfriend.soul.domain.usecase.character

import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategory
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterCategoryBySlugUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(slug: String): AppResult<CharacterCategory> =
        characterRepository.getCharacterCategoryBySlug(slug)
}
