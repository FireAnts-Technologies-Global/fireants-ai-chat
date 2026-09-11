package com.pegas.yuki.virtual.chat.domain.usecase.mycharacter

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.CreateMyCharacterInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacter
import com.pegas.yuki.virtual.chat.domain.repository.MyCharacterRepository
import javax.inject.Inject

class CreateMyCharacterUseCase @Inject constructor(
    private val myCharacterRepository: MyCharacterRepository
) {
    suspend operator fun invoke(input: CreateMyCharacterInput): AppResult<MyCharacter> =
        myCharacterRepository.createMyCharacter(input)
}
