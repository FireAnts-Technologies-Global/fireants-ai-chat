package com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color090514
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorF1CBB7
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFF5D82
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeSemiBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitMedium
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_100
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_26
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_38
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_48
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_52
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_56
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_90
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
fun CreateBottomAction(
    text: String,
    isShowCoin: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = SdpR_16)
            .padding(top = SdpR_8, bottom = SdpR_24)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(SdpR_48)
                .clip(RoundedCornerShape(SdpR_26))
                .background(ColorF1CBB7)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontFamily = OutfitBold,
                    fontSize = SdpR_16.nonScaledSp,
                    color = Color090514
                )
                if (isShowCoin) {
                    Spacer(modifier = Modifier.width(SdpR_6))

                    Image(
                        painter = painterResource(id = R.drawable.img_coin),
                        contentDescription = null,
                        modifier = Modifier.size(SdpR_14)
                    )
                }


            }

        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        fontFamily = OutfitBold,
        fontSize = SdpR_14.nonScaledSp,
        color = ColorFDFDFD,
        modifier = Modifier.padding(bottom = SdpR_8)
    )
}

@Composable
fun SegmentedOptions(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SdpR_8)
    ) {
        options.forEach { option ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(SdpR_38)
                    .clip(RoundedCornerShape(SdpR_12))
                    .background(if (option == selected) ColorD65A98 else Color150F25)
                    .border(
                        width = SdpR_1,
                        color = if (option == selected) ColorD65A98 else Color322D41,
                        shape = RoundedCornerShape(SdpR_12)
                    )
                    .clickable { onSelected(option) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    fontFamily = OutfitBold,
                    fontSize = SdpR_14.nonScaledSp,
                    color = if (option == selected) Color090514 else ColorAFA5C3,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun SelectableImageCard(
    option: CreateImageOption,
    selected: Boolean,
    modifier: Modifier = Modifier,
    imageAspectRatio: Float = 1.1f,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(SdpR_12))
            .background(Color150F25)
            .border(
                border = BorderStroke(
                    width = if (selected) SdpR_2 else SdpR_1,
                    color = if (selected) ColorFF5D82 else Color322D41
                ),
                shape = RoundedCornerShape(SdpR_12)
            )
            .clickable(onClick = onClick)
            .padding(SdpR_6),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = option.imageRes),
            contentDescription = option.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio)
                .clip(RoundedCornerShape(SdpR_6))
        )
        Spacer(modifier = Modifier.height(SdpR_6))
        Text(
            text = option.title,
            fontFamily = OutfitBold,
            fontSize = SdpR_14.nonScaledSp,
            color = if (selected) ColorE8C3AC else ColorAFA5C3,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PromptInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodySmall.copy(
            fontFamily = ManropeRegular,
            fontSize = SdpR_13.nonScaledSp,
            color = ColorFDFDFD
        ),
        cursorBrush = SolidColor(ColorD65A98),
        modifier = Modifier
            .fillMaxWidth()
            .height(SdpR_90)
            .clip(RoundedCornerShape(SdpR_12))
            .background(Color150F25)
            .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_12))
            .padding(SdpR_12),
        decorationBox = { innerTextField ->
            Box {
                if (value.isBlank()) {
                    Text(
                        text = androidx.compose.ui.res.stringResource(R.string.create_prompt_hint),
                        fontFamily = ManropeRegular,
                        fontSize = SdpR_13.nonScaledSp,
                        color = ColorAFA5C3
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun SingleLineInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = OutfitMedium,
            fontSize = SdpR_13.nonScaledSp,
            color = ColorFDFDFD
        ),
        cursorBrush = SolidColor(ColorD65A98),
        modifier = modifier
            .height(SdpR_52)
            .clip(RoundedCornerShape(SdpR_12))
            .background(Color150F25)
            .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_12))
            .padding(horizontal = SdpR_14),
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.CenterStart) {
                innerTextField()
            }
        }
    )
}

@Composable
fun RandomNameButton(onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .size(SdpR_52)
            .clip(RoundedCornerShape(SdpR_12))
            .clickable { onClick() }
            .background(Color150F25)
            .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_12)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_random),
            contentDescription = null,
            tint = ColorE8C3AC,
            modifier = Modifier.size(SdpR_24)
        )
    }
}

@Composable
fun SkinToneSwatch(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(SdpR_56)
            .clip(RoundedCornerShape(SdpR_8))
            .background(color)
            .border(
                width = if (selected) SdpR_2 else SdpR_1,
                color = if (selected) ColorD65A98 else Color.Transparent,
                shape = RoundedCornerShape(SdpR_8)
            )
            .clickable(onClick = onClick)
    )
}

@Composable
fun PersonalityCard(
    item: PersonalityItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .height(SdpR_100)
            .clip(RoundedCornerShape(SdpR_12))
            .clickable { onClick() }
            .background(Color150F25)
            .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_12))
            .padding(SdpR_12),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = item.iconRes),
            contentDescription = null,
            modifier = Modifier.size(SdpR_24)
        )
        Spacer(modifier = Modifier.height(SdpR_4))
        Column {
            Text(
                text = item.label,
                fontFamily = ManropeSemiBold,
                fontSize = SdpR_11.nonScaledSp,
                color = ColorE8C3AC,
                maxLines = 1
            )
            Text(
                text = item.value,
                fontFamily = ManropeBold,
                fontSize = SdpR_14.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = ColorFDFDFD,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 390)
@Composable
private fun CreateBottomActionPreview() {
    CreateBottomAction(
        isShowCoin = false,
        text = "Next Step",
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 390)
@Composable
private fun SegmentedOptionsPreview() {
    SegmentedOptions(
        options = listOf("Girls", "Guys", "Trans"),
        selected = "Girls",
        onSelected = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 190)
@Composable
private fun SelectableImageCardPreview() {
    SelectableImageCard(
        option = CreateImageOption(
            title = "Realistic",
            imageRes = R.drawable.img_empty
        ),
        selected = true,
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 390)
@Composable
private fun PromptInputPreview() {
    PromptInput(
        value = "A sweet, comforting companion who loves midnight star-gazing.",
        onValueChange = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 180)
@Composable
private fun PersonalityCardPreview() {
    PersonalityCard(
        item = PersonalityItem(
            label = "Personality",
            value = "Spiritual",
            iconRes = R.drawable.img_empty
        )
    )
}
