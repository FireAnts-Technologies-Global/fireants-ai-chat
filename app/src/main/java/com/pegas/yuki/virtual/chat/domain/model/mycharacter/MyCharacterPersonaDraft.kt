package com.pegas.yuki.virtual.chat.domain.model.mycharacter

data class MyCharacterPersonaDraft(
    val personalityTraits: List<String>,
    val speakingStyle: String?,
    val scenario: String?,
    val customTrait: String?
)
