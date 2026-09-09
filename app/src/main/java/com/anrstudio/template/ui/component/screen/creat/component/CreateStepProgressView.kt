package com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_18
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_40

@Composable
fun CreateStepProgressView(
    currentStep: Int,
    modifier: Modifier = Modifier,
    totalSteps: Int = 4,
    activeColor: Color = ColorE8C3AC,
    inactiveColor: Color = Color322D41,
    circleSize: Dp = SdpR_18,
    lineWidth: Dp = SdpR_1,
    inactiveStrokeWidth: Dp = SdpR_1,
    lineCircleGap: Dp = SdpR_4,
    height: Dp = SdpR_40
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

            drawLine(
                color = if (isCompletedLine) activeColor else inactiveColor,
                start = Offset(startCenterX + radius + lineCircleGap.toPx(), centerY),
                end = Offset(endCenterX - radius, centerY),
                strokeWidth = lineWidth.toPx(),
                cap = StrokeCap.Square
            )
        }

        for (index in 0 until totalSteps) {
            val centerX = radius + centerDistance * index
            val isCompletedStep = index < safeCurrentStep

            if (isCompletedStep) {
                drawCircle(
                    color = activeColor,
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

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 430)
@Composable
private fun CreateStepProgressViewPreview() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CreateStepProgressView(currentStep = 1)
    }
}
