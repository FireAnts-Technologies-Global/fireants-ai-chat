package com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter

data class MyCharacterLimits(
    val nameMax: Int,
    val personalityMax: Int,
    val appearancePromptMax: Int,
    val maxTags: Int,
    val maxPerUser: Int,
    val requireImageToPublish: Boolean
)
