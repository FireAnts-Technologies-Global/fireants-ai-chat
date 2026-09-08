package com.anrstudio.template.domain.model.mycharacter

data class MyCharacterPersonaDraft(
    val personalityTraits: List<String>,
    val speakingStyle: String?,
    val scenario: String?,
    val customTrait: String?
)
