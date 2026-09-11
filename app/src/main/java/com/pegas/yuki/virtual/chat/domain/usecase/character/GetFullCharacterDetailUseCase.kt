package com.pegas.yuki.virtual.chat.domain.usecase.character

import com.pegas.yuki.virtual.chat.domain.model.character.FullCharacterDetail
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.repository.CharacterRepository
import javax.inject.Inject

class GetFullCharacterDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(slug: String): AppResult<FullCharacterDetail> {
        return when (val characterResult = characterRepository.getCharacterBySlug(slug)) {
            is AppResult.Failure -> characterResult
            is AppResult.Success -> {
                val character = characterResult.data
                val bgResult = characterRepository.getCharacterBackgrounds(character.id)
                val backgroundsData = if (bgResult is AppResult.Success) bgResult.data else null
                AppResult.Success(
                    FullCharacterDetail(
                        character = character,
                        backgroundsData = backgroundsData
                    )
                )
            }
        }
    }
}
