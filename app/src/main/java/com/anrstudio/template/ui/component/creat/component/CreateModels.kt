package com.anrstudio.template.ui.component.creat.component

import androidx.annotation.DrawableRes


const val CREATE_TOTAL_STEPS = 4

data class CreateImageOption(
    val title: String,
    @DrawableRes val imageRes: Int
)

data class PersonalityItem(
    val label: String,
    val value: String,
    @DrawableRes val iconRes: Int,
    val onClick: () -> Unit = {}
)
