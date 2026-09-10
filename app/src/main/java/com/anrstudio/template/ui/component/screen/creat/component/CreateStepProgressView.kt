package com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_28

@Composable
fun CreateStepProgressView(
    currentStep: Int,
    modifier: Modifier = Modifier,
    totalSteps: Int = 4,
    activeColor: Color = Color(0xFFFF4081),
    inactiveColor: Color = Color(0xFFCBD5E1),
    circleSize: Dp = SdpR_14,
    lineWidth: Dp = SdpR_2,
    inactiveStrokeWidth: Dp = SdpR_1,
    lineCircleGap: Dp = SdpR_2,
    height: Dp = SdpR_28
) {
    if (totalSteps <= 0) return

    val safeCurrentStep = currentStep.coerceIn(0, totalSteps)

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val radius = circleSize.toPx() / 2f
        val centerY = size.height / 2f
        val centerDistance = if (totalSteps == 1) {
            0f
        } else {
            (size.width - circleSize.toPx()) / (totalSteps - 1)
        }

        for (index in 0 until totalSteps - 1) {
            val startCenterX = radius + centerDistance * index
            val endCenterX = radius + centerDistance * (index + 1)
            val isCompletedLine = index < safeCurrentStep - 1
            val isTransitionLine = index == safeCurrentStep - 1

            val lineStartX = startCenterX + radius + lineCircleGap.toPx()
            val lineEndX = endCenterX - radius - lineCircleGap.toPx()

            if (isCompletedLine) {
                drawLine(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF3377), Color(0xFFFF7A45)),
                        startX = lineStartX,
                        endX = lineEndX
                    ),
                    start = Offset(lineStartX, centerY),
                    end = Offset(lineEndX, centerY),
                    strokeWidth = lineWidth.toPx(),
                    cap = StrokeCap.Round
                )
            } else if (isTransitionLine && safeCurrentStep == 1) {
                drawLine(
                    brush = Brush.horizontalGradient(
                        colors = listOf(activeColor, inactiveColor.copy(alpha = 0.5f)),
                        startX = lineStartX,
                        endX = lineEndX
                    ),
                    start = Offset(lineStartX, centerY),
                    end = Offset(lineEndX, centerY),
                    strokeWidth = lineWidth.toPx(),
                    cap = StrokeCap.Round
                )
            } else {
                drawLine(
                    color = inactiveColor,
                    start = Offset(lineStartX, centerY),
                    end = Offset(lineEndX, centerY),
                    strokeWidth = lineWidth.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        for (index in 0 until totalSteps) {
            val centerX = radius + centerDistance * index
            val isCompletedStep = index < safeCurrentStep

            if (isCompletedStep) {
                drawCircle(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFF3377), Color(0xFFFF5E98)),
                        start = Offset(centerX - radius, centerY - radius),
                        end = Offset(centerX + radius, centerY + radius)
                    ),
                    radius = radius,
                    center = Offset(centerX, centerY)
                )
            } else {
                drawCircle(
                    color = inactiveColor,
                    radius = radius - inactiveStrokeWidth.toPx() / 2f,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = inactiveStrokeWidth.toPx())
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 430)
@Composable
private fun CreateStepProgressViewPreview() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CreateStepProgressView(currentStep = 1)
    }
}
