package com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter

data class MyCharacterQuota(
    val charactersUsed: Int,
    val characterCreateCost: Int,
    val imageGenCoinCost: Int,
    val charactersRemaining: Int,
    val requireImageToPublish: Boolean,
    val imageGenDailyLimit: Int
)
