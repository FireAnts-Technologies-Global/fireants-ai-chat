package com.pegas.aura.aigirlfriend.soul.domain.usecase.character

import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterProgress
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterProgressUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: String): AppResult<CharacterProgress> =
        characterRepository.getCharacterProgress(characterId)
}
