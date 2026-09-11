package com.pegas.yuki.virtual.chat.domain.model.mycharacter

data class MyCharacterGenerateImageResult(
    val taskId: String,
    val status: String,
    val balanceAfter: Int?
)
