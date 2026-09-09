package com.pegas.aura.aigirlfriend.soul.domain.model.character

data class CharacterBackground(
    val id: String,
    val characterId: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val priceCoins: Int,
    val unlockLevel: Int,
    val isDefault: Boolean,
    val isLocked: Boolean,
    val isUnlocked: Boolean
)
