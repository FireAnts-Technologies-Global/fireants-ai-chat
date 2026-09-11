package com.pegas.yuki.virtual.chat.domain.model.mycharacter

data class MyCharacterPersonaDraftInput(
    val personalityTraits: List<String> = emptyList(),
    val speakingStyle: String? = null,
    val scenario: String? = null,
    val customTrait: String? = null
)
