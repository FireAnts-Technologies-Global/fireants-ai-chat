package com.pegas.aura.aigirlfriend.soul.domain.usecase.mycharacter

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.MyCharacter
import com.pegas.aura.aigirlfriend.soul.domain.repository.MyCharacterRepository
import javax.inject.Inject

class PublishMyCharacterUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(userCharacterId: String): AppResult<MyCharacter> =
        myCharacterRepository.publishMyCharacter(userCharacterId)
}
