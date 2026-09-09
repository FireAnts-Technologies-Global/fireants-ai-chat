package com.pegas.aura.aigirlfriend.soul.domain.usecase.character

import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterBySlugUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(slug: String): AppResult<Character> =
        characterRepository.getCharacterBySlug(slug)
}
