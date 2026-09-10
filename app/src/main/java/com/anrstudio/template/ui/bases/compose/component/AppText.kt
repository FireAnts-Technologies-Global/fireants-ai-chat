package com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

val AppTextHorizontalGradient: Brush = Brush.horizontalGradient(
    colors = listOf(
        ColorDC60FF,
        ColorFF41BC,
        ColorFF8040
    )
)

val AppTextVerticalGradient: Brush = Brush.verticalGradient(
    colors = listOf(
        ColorDC60FF,
        ColorFF41BC,
        ColorFF8040
    )
)

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    gradient: Brush? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    style: TextStyle = LocalTextStyle.current
) {
    val mergedStyle = if (gradient != null) {
        style.copy(
            brush = gradient,
            fontSize = if (fontSize != TextUnit.Unspecified) fontSize else style.fontSize,
            fontStyle = fontStyle ?: style.fontStyle,
            fontWeight = fontWeight ?: style.fontWeight,
            fontFamily = fontFamily ?: style.fontFamily,
            letterSpacing = if (letterSpacing != TextUnit.Unspecified) letterSpacing else style.letterSpacing,
            textDecoration = textDecoration ?: style.textDecoration,
            textAlign = textAlign ?: style.textAlign,
            lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else style.lineHeight
        )
    } else {
        style.copy(
            color = if (color != Color.Unspecified) color else style.color,
            fontSize = if (fontSize != TextUnit.Unspecified) fontSize else style.fontSize,
            fontStyle = fontStyle ?: style.fontStyle,
            fontWeight = fontWeight ?: style.fontWeight,
            fontFamily = fontFamily ?: style.fontFamily,
            letterSpacing = if (letterSpacing != TextUnit.Unspecified) letterSpacing else style.letterSpacing,
            textDecoration = textDecoration ?: style.textDecoration,
            textAlign = textAlign ?: style.textAlign,
            lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else style.lineHeight
        )
    }

    Text(
        text = text,
        modifier = modifier,
        style = mergedStyle,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines
    )
}


@Composable
fun AppGradientText(
    text: String,
    modifier: Modifier = Modifier,
    gradient: Brush = AppTextHorizontalGradient,
    fontSize: TextUnit = SdpR_16.nonScaledSp,
    fontFamily: FontFamily? = OutfitBold,
    fontWeight: FontWeight? = FontWeight.Bold,
    fontStyle: FontStyle? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    style: TextStyle = LocalTextStyle.current
) {
    AppText(
        text = text,
        modifier = modifier,
        gradient = gradient,
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        style = style
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AppTextPreview() {
    AppGradientText(
        text = "$9.99 / month",
        fontSize = SdpR_24.nonScaledSp
    )
}
