package com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

fun Modifier.appVerticalGradientBackground(): Modifier = background(
    brush = Brush.verticalGradient(
        colors = listOf(
            Color0F0817,
            Color150F25,
            Color0D0713
        )
    )
)
