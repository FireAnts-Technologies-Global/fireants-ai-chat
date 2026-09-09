package com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter

data class CreateMyCharacterInput(
    val name: String,
    val personality: String,
    val gender: String? = null,
    val age: Int? = null,
    val tags: List<String> = emptyList()
)
