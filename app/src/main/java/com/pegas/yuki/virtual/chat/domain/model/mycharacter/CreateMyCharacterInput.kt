package com.pegas.yuki.virtual.chat.domain.model.mycharacter

data class CreateMyCharacterInput(
    val name: String,
    val personality: String,
    val gender: String? = null,
    val age: Int? = null,
    val tags: List<String> = emptyList()
)
