package com.pegas.aura.aigirlfriend.soul.domain.model.character

data class CharacterCategory(
    val id: String,
    val name: String,
    val slug: String,
    val description: String,
    val sort: Int,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String
)
