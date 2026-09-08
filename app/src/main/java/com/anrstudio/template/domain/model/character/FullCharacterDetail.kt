package com.anrstudio.template.domain.model.character

data class FullCharacterDetail(
    val character: Character,
    val backgroundsData: CharacterBackgroundsData? = null
)
