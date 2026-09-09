package com.pegas.aura.aigirlfriend.soul.domain.usecase.mycharacter

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.CreateMyCharacterInput
import com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter.MyCharacter
import com.pegas.aura.aigirlfriend.soul.domain.repository.MyCharacterRepository
import javax.inject.Inject

class CreateMyCharacterUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(input: CreateMyCharacterInput): AppResult<MyCharacter> =
        myCharacterRepository.createMyCharacter(input)
}
