package com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_104
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_26
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_28
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_38
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_40
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_44
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
        val buttonGradient = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFE040FB),
                Color(0xFFFF3377),
                Color(0xFFFF7A45)
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(SdpR_52)
                .clip(RoundedCornerShape(SdpR_26))
                .background(buttonGradient)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_16.nonScaledSp,
                    color = Color.White
                )
                if (isShowCoin) {
                    Spacer(modifier = Modifier.width(SdpR_6))

                    Image(
                        painter = painterResource(id = R.drawable.img_coin),
                        contentDescription = null,
                        modifier = Modifier.size(SdpR_16)
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = SdpR_8),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontFamily = ManropeBold,
            fontWeight = FontWeight.Bold,
            fontSize = SdpR_14.nonScaledSp,
            color = Color(0xFF150F25)
        )
        action?.invoke()
    }
}

@Composable
fun IdentityOptionCard(
    title: String,
    @DrawableRes iconRes: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val selectedBorder = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF4081),
            Color(0xFFFF8E53)
        )
    )
    val unselectedBorder = SolidColor(Color(0xFFE5E7EB))

    Box(
        modifier = modifier
            .height(SdpR_90)
            .clip(RoundedCornerShape(SdpR_16))
            .background(if (selected) Color(0xFFFFF0F5) else Color(0xFFF9FAFC))
            .border(
                border = BorderStroke(
                    width = if (selected) SdpR_2 else SdpR_1,
                    brush = if (selected) selectedBorder else unselectedBorder
                ),
                shape = RoundedCornerShape(SdpR_16)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                tint = if (selected) Color(0xFFFF4081) else Color(0xFF150F25),
                modifier = Modifier.size(SdpR_28)
            )
            Spacer(modifier = Modifier.height(SdpR_8))
            Text(
                text = title,
                fontFamily = ManropeBold,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                fontSize = SdpR_13.nonScaledSp,
                color = if (selected) Color(0xFFFF4081) else Color(0xFF150F25),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SegmentedOptions(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    val selectedGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFE040FB),
            Color(0xFFFF3377),
            Color(0xFFFF7A45)
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SdpR_8)
    ) {
        options.forEach { option ->
            val isSelected = option == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(SdpR_40)
                    .clip(RoundedCornerShape(SdpR_14))
                    .then(
                        if (isSelected) {
                            Modifier.background(selectedGradient)
                        } else {
                            Modifier
                                .background(Color(0xFFF9FAFC))
                                .border(
                                    width = SdpR_1,
                                    color = Color(0xFFE5E7EB),
                                    shape = RoundedCornerShape(SdpR_14)
                                )
                        }
                    )
                    .clickable { onSelected(option) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    fontFamily = ManropeBold,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = SdpR_14.nonScaledSp,
                    color = if (isSelected) Color.White else Color(0xFF8E889B),
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
    imageAspectRatio: Float = 1.15f,
    onClick: () -> Unit
) {
    val selectedBorder = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF4081),
            Color(0xFFFF8E53)
        )
    )
    val unselectedBorder = SolidColor(Color(0xFFE5E7EB))

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(SdpR_16))
            .background(if (selected) Color(0xFFFFF0F5) else Color(0xFFF9FAFC))
            .border(
                border = BorderStroke(
                    width = if (selected) SdpR_2 else SdpR_1,
                    brush = if (selected) selectedBorder else unselectedBorder
                ),
                shape = RoundedCornerShape(SdpR_16)
            )
            .clickable(onClick = onClick)
            .padding(SdpR_8),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = option.imageRes),
            contentDescription = option.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio)
                .clip(RoundedCornerShape(SdpR_12))
        )
        Spacer(modifier = Modifier.height(SdpR_8))
        Text(
            text = option.title,
            fontFamily = ManropeBold,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            fontSize = SdpR_13.nonScaledSp,
            color = if (selected) Color(0xFFFF4081) else Color(0xFF8E889B),
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
            color = Color(0xFF150F25)
        ),
        cursorBrush = SolidColor(Color(0xFFFF4081)),
        modifier = Modifier
            .fillMaxWidth()
            .height(SdpR_100)
            .clip(RoundedCornerShape(SdpR_16))
            .background(Color(0xFFF9FAFC))
            .border(SdpR_1, Color(0xFFE5E7EB), RoundedCornerShape(SdpR_16))
            .padding(SdpR_14),
        decorationBox = { innerTextField ->
            Box {
                if (value.isBlank()) {
                    Text(
                        text = androidx.compose.ui.res.stringResource(R.string.create_prompt_hint),
                        fontFamily = ManropeRegular,
                        fontSize = SdpR_13.nonScaledSp,
                        color = Color(0xFF9CA3AF)
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
            fontFamily = ManropeBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = SdpR_16.nonScaledSp,
            color = Color(0xFF150F25)
        ),
        cursorBrush = SolidColor(Color(0xFFFF4081)),
        modifier = modifier
            .height(SdpR_52)
            .clip(RoundedCornerShape(SdpR_16))
            .background(Color(0xFFF9FAFC))
            .border(SdpR_1, Color(0xFFE5E7EB), RoundedCornerShape(SdpR_16))
            .padding(horizontal = SdpR_16),
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
            .clip(RoundedCornerShape(SdpR_16))
            .clickable { onClick() }
            .background(Color.White)
            .border(SdpR_1, Color(0xFFE5E7EB), RoundedCornerShape(SdpR_16)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_random),
            contentDescription = null,
            tint = Color(0xFFFF4081),
            modifier = Modifier.size(SdpR_24)
        )
    }
}

@Composable
fun SkinToneSwatch(
    color: Color,
    selected: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = SdpR_44,
    onClick: () -> Unit
) {
    val selectedBorder = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF4081),
            Color(0xFFFF8E53)
        )
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
            .then(
                if (selected) {
                    Modifier.border(
                        border = BorderStroke(
                            width = SdpR_2,
                            brush = selectedBorder
                        ),
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
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
            .height(SdpR_104)
            .clip(RoundedCornerShape(SdpR_16))
            .clickable { onClick() }
            .background(Color.White)
            .border(SdpR_1, Color(0xFFE5E7EB), RoundedCornerShape(SdpR_16))
            .padding(SdpR_14),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = item.iconRes),
            contentDescription = null,
            tint = Color(0xFFFF4081),
            modifier = Modifier.size(SdpR_26)
        )
        Spacer(modifier = Modifier.height(SdpR_4))
        Column {
            Text(
                text = item.label,
                fontFamily = ManropeSemiBold,
                fontWeight = FontWeight.SemiBold,
                fontSize = SdpR_12.nonScaledSp,
                color = Color(0xFFFF4081),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(SdpR_2))
            Text(
                text = item.value,
                fontFamily = ManropeBold,
                fontSize = SdpR_14.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF150F25),
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
