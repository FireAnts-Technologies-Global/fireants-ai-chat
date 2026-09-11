package com.pegas.yuki.virtual.chat.domain.model.character

data class FullCharacterDetail(
    val character: Character,
    val backgroundsData: CharacterBackgroundsData? = null
)
