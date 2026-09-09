package com.pegas.aura.aigirlfriend.soul.domain.usecase.character

import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterPage
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery
import com.pegas.aura.aigirlfriend.soul.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(query: PaginationQuery): AppResult<CharacterPage> =
        characterRepository.getCharacters(query)
}
