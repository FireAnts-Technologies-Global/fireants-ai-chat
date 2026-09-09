package com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

private val LoadingGradientColors = listOf(
    ColorDC60FF,
    ColorFF41BC,
    ColorFF8040,
    ColorDC60FF
)

@Composable
fun CircularProgressIndicator3Colors(
    size: Dp = SdpR_20,
    modifier: Modifier = Modifier,
    strokeWidth: Dp? = null,
    showTrack: Boolean = true
) {
    val actualStrokeWidth = strokeWidth ?: when {
        size <= 16.dp -> 2.dp
        size <= 28.dp -> 2.5.dp
        size <= 40.dp -> 3.dp
        size <= 56.dp -> 4.dp
        else -> 5.dp
    }

    val infiniteTransition = rememberInfiniteTransition(label = "ImageLoadingRotation")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAngle"
    )

    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidthPx = actualStrokeWidth.toPx()
        val arcDiameter = this.size.minDimension - strokeWidthPx
        val topLeft = Offset(
            x = (this.size.width - arcDiameter) / 2f,
            y = (this.size.height - arcDiameter) / 2f
        )
        val arcSize = Size(arcDiameter, arcDiameter)

        if (showTrack) {
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        ColorDC60FF.copy(alpha = 0.15f),
                        ColorFF41BC.copy(alpha = 0.15f),
                        ColorFF8040.copy(alpha = 0.15f),
                        ColorDC60FF.copy(alpha = 0.15f)
                    )
                ),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidthPx)
            )
        }

        rotate(degrees = rotationAngle) {
            drawArc(
                brush = Brush.sweepGradient(
                    0.0f to ColorDC60FF,
                    0.38f to ColorFF41BC,
                    0.78f to ColorFF8040,
                    1.0f to ColorDC60FF
                ),
                startAngle = 0f,
                sweepAngle = 280f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        }
    }
}

@Composable
fun ImageLoadingLottie(
    size: Dp = SdpR_32,
    modifier: Modifier = Modifier,
    strokeWidth: Dp? = null,
    showTrack: Boolean = true
) {
    CircularProgressIndicator3Colors(
        size = size,
        modifier = modifier,
        strokeWidth = strokeWidth,
        showTrack = showTrack
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun PreviewCircularProgressIndicator3Colors() {
    Box(
        modifier = Modifier.size(SdpR_56),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator3Colors(size = SdpR_56)
    }
}
