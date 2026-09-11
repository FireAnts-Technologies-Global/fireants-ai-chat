package com.pegas.yuki.virtual.chat.ui.bases.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.AppButtonVerticalGradient
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color6B5E80
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD9D9D9
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_100
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_22
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_52
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
    @DrawableRes iconRes: Int? = null,
    iconPainter: Painter? = null,
    iconSize: Dp = SdpR_20,
    iconTint: Color? = ColorFFFFFF,
    iconSpacing: Dp = SdpR_8,
    gradient: Brush = AppButtonVerticalGradient,
    disabledColor: Color = Color6B5E80,
    textColor: Color = ColorFFFFFF,
    disabledTextColor: Color = ColorD9D9D9,
    shape: Shape = RoundedCornerShape(SdpR_100),
    minHeight: Dp = SdpR_52,
    contentPadding: PaddingValues = PaddingValues(horizontal = SdpR_24, vertical = SdpR_12),
    textStyle: TextStyle = TextStyle(
        fontFamily = ManropeBold,
        fontWeight = FontWeight.Bold,
        fontSize = SdpR_16.nonScaledSp,
        textAlign = TextAlign.Center
    ),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: (@Composable RowScope.() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled && !loading) 0.96f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "app_button_scale"
    )

    val backgroundModifier = if (enabled && !loading) {
        Modifier.background(brush = gradient, shape = shape)
    } else {
        Modifier.background(color = disabledColor, shape = shape)
    }

    val resolvedLeadingIcon: (@Composable () -> Unit)? = when {
        leadingIcon != null -> leadingIcon
        iconPainter != null -> {
            {
                if (iconTint != null) {
                    Icon(
                        painter = iconPainter,
                        contentDescription = null,
                        tint = if (enabled) iconTint else disabledTextColor,
                        modifier = Modifier.size(iconSize)
                    )
                } else {
                    Icon(
                        painter = iconPainter,
                        contentDescription = null,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
        iconRes != null -> {
            {
                if (iconTint != null) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        tint = if (enabled) iconTint else disabledTextColor,
                        modifier = Modifier.size(iconSize)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
        else -> null
    }

    Box(
        modifier = modifier
            .scale(scale)
            .clip(shape)
            .then(backgroundModifier)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled && !loading,
                onClick = onClick
            )
            .defaultMinSize(minHeight = minHeight)
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = textColor,
                strokeWidth = SdpR_2,
                modifier = Modifier.size(SdpR_22)
            )
        } else if (content != null) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                resolvedLeadingIcon?.let {
                    it()
                    Spacer(modifier = Modifier.width(iconSpacing))
                }

                if (!text.isNullOrBlank()) {
                    Text(
                        text = text,
                        style = textStyle,
                        color = if (enabled) textColor else disabledTextColor
                    )
                }

                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(iconSpacing))
                    it()
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0713)
@Composable
private fun AppButtonWithIconPreview() {
    AppButton(
        text = "Let's chat with me",
        iconRes = R.drawable.ic_chat,
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0713)
@Composable
private fun AppButtonPreview() {
    AppButton(
        text = "Get Started",
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0713)
@Composable
private fun AppButtonDisabledPreview() {
    AppButton(
        text = "Disabled Button",
        iconRes = R.drawable.ic_chat,
        enabled = false,
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0713)
@Composable
private fun AppButtonLoadingPreview() {
    AppButton(
        text = "Loading",
        loading = true,
        onClick = {}
    )
}
