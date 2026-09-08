package com.anrstudio.template.domain.model.character

data class PurchaseBackgroundResult(
    val success: Boolean,
    val coinBalance: Int,
    val background: CharacterBackground?
)
