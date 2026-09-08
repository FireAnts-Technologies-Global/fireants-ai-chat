package com.anrstudio.template.domain.model.mycharacter

data class UpdateMyCharacterInput(
    val userCharacterId: String,
    val name: String? = null,
    val personality: String? = null,
    val gender: String? = null,
    val age: Int? = null,
    val tags: List<String>? = null
)
