package com.pegas.aura.aigirlfriend.soul.data.local.conversation

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "conversations",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["userId", "characterId"]),
        Index(value = ["userId", "updatedAt"])
    ]
)
data class ConversationEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val characterId: String,
    val backgroundId: String?,
    val title: String?,
    val lastMessagePreview: String?,
    val lastMessageAt: String?,
    val createdAt: String,
    val updatedAt: String,
    val characterRefId: String?,
    val characterSlug: String?,
    val characterName: String?,
    val characterImage: String?,
    val characterDescription: String?,
    val characterTask: String?,
    val characterAge: Int?
)
