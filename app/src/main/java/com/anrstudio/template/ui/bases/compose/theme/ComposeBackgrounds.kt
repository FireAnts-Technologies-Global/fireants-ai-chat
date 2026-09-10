package com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.pegas.aura.aigirlfriend.soul.R

@Composable
fun Modifier.appSplashBackground(): Modifier = this.paint(
    painter = painterResource(id = R.drawable.bg_splash),
    contentScale = ContentScale.Crop
)

fun Modifier.appVerticalGradientBackground(): Modifier = background(
    brush = Brush.verticalGradient(
        colors = listOf(
            Color0F0817,
            Color150F25,
            Color0D0713
        )
    )
)

val AppButtonVerticalGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFD94BFF),
        Color(0xFFFF3FA4),
        Color(0xFFFF764C)
    ),
    start = Offset(0f, 500f),
    end = Offset(1000f, 0f)
)

