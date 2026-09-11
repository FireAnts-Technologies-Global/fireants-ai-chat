package com.pegas.yuki.virtual.chat.domain.usecase.character

import com.pegas.yuki.virtual.chat.domain.model.character.CharacterBackgroundsData
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterBackgroundsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: String): AppResult<CharacterBackgroundsData> =
        characterRepository.getCharacterBackgrounds(characterId)
}
