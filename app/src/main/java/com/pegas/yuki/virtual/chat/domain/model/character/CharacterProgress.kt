package com.pegas.yuki.virtual.chat.domain.model.character

data class CharacterProgress(
    val characterId: String,
    val xp: Int,
    val level: Int,
    val maxLevel: Int,
    val xpPerChat: Int,
    val xpPerLevel: Int,
    val xpToNextLevel: Int,
    val leveledUp: Boolean
) {
    val relationshipTitle: String
        get() = when {
            level < 10 -> "Stranger"
            level < 20 -> "Acquaintance"
            level < 30 -> "Friend"
            level < 40 -> "Close Friend"
            level < 50 -> "Lover"
            else -> "Soulmate"
        }
}
