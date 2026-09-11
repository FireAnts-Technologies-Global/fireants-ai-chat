package com.pegas.yuki.virtual.chat.ui.component.screen.creat.component


const val CREATE_TOTAL_STEPS = 4

data class CreateImageOption(
    val title: String,
    @androidx.annotation.DrawableRes val imageRes: Int
)

data class PersonalityItem(
    val label: String,
    val value: String,
    @androidx.annotation.DrawableRes val iconRes: Int,
    val onClick: () -> Unit = {}
)
