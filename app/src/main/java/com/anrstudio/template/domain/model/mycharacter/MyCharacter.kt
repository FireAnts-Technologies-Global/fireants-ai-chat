package com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter

data class MyCharacter(
    val id: String,
    val name: String,
    val description: String,
    val image: String?,
    val status: String,
    val gender: String?,
    val age: Int?,
    val tags: List<String>,
    val appearancePrompt: String?,
    val personaDraft: MyCharacterPersonaDraft,
    val createdAt: String,
    val updatedAt: String
)
