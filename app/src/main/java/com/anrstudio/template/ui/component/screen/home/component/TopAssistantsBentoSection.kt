package com.pegas.aura.aigirlfriend.soul.ui.component.screen.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage

@Composable
fun TopAssistantsBentoSection(
    topAssistants: List<Character>,
    onCharacterClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (topAssistants.isEmpty()) return

    val top1 = topAssistants.getOrNull(0)
    val top2 = topAssistants.getOrNull(1)
    val top3 = topAssistants.getOrNull(2)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SdpR_12)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SdpR_6)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_vector),
                contentDescription = stringResource(R.string.close),
                tint = ColorED4DA4,
                modifier = Modifier.size(SdpR_13)
            )
            Text(
                text = stringResource(R.string.home_top_assistants),
                fontFamily = ManropeSemiBold,
                fontWeight = FontWeight.SemiBold,
                fontSize = SdpR_16.nonScaledSp,
                color = Color110640
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(SdpR_220),
            horizontalArrangement = Arrangement.spacedBy(SdpR_12)
        ) {
            if (top1 != null) {
                Top1HeroCard(
                    character = top1,
                    onClick = { onCharacterClick(top1.slug) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(SdpR_10)
            ) {
                if (top2 != null) {
                    TopHorizontalCard(
                        rank = 2,
                        rankBadgeColor = Color8B5CF6,
                        nameColor = Color8B5CF6,
                        character = top2,
                        onClick = { onCharacterClick(top2.slug) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                if (top3 != null) {
                    TopHorizontalCard(
                        rank = 3,
                        rankBadgeColor = ColorFF7A45,
                        nameColor = ColorFF7A45,
                        character = top3,
                        onClick = { onCharacterClick(top3.slug) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun RankBadge(
    rank: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(SdpR_20)
            .background(color = backgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rank,
            fontFamily = ManropeBold,
            fontWeight = FontWeight.Bold,
            fontSize = SdpR_11.nonScaledSp,
            color = ColorFFFFFF,
            textAlign = TextAlign.Center,
            style = LocalTextStyle.current.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                )
            )
        )
    }
}

@Composable
private fun Top1HeroCard(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hazeState = remember { HazeState() }

    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(SdpR_20),
        colors = CardDefaults.cardColors(containerColor = ColorFFFFFF),
        border = BorderStroke(SdpR_1, Color14000000)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState)
            ) {
                LoadingAsyncImage(
                    imageUrl = character.image,
                    contentDescription = character.name,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.Transparent,
                                    0.5f to Color.Transparent,
                                    1.0f to Color66000000
                                )
                            )
                        )
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(SdpR_8)
                    .clip(RoundedCornerShape(SdpR_14))
                    .hazeEffect(state = hazeState) {
                        blurRadius = 18.dp
                        backgroundColor = Color4DFFFFFF
                    }
                    .padding(horizontal = SdpR_10, vertical = SdpR_8)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(SdpR_3)
                ) {
                    RankBadge(
                        rank = "1",
                        backgroundColor = ColorFFB03A
                    )

                    Text(
                        text = character.name,
                        fontFamily = ManropeBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = SdpR_16.nonScaledSp,
                        color = ColorFFFFFF,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (character.description.isNotBlank()) {
                        Text(
                            text = character.description,
                            fontFamily = ManropeRegular,
                            fontWeight = FontWeight.Normal,
                            fontSize = SdpR_11.nonScaledSp,
                            lineHeight = SdpR_13.nonScaledSp,
                            color = ColorF2FFFFFF,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopHorizontalCard(
    rank: Int,
    rankBadgeColor: Color,
    nameColor: Color,
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(SdpR_20),
        colors = CardDefaults.cardColors(containerColor = ColorFFFFFF),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingAsyncImage(
                imageUrl = character.image,
                contentDescription = character.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colorStops = arrayOf(
                                0.0f to Color.White.copy(alpha = 0.85f),
                                0.35f to Color.White.copy(alpha = 0.45f),
                                0.7f to Color.Transparent
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = SdpR_12, bottom = SdpR_10, end = SdpR_6),
                verticalArrangement = Arrangement.spacedBy(SdpR_2)
            ) {
                RankBadge(
                    rank = rank.toString(),
                    backgroundColor = rankBadgeColor
                )

                Text(
                    text = character.name,
                    fontFamily = ManropeSemiBold,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = SdpR_14.nonScaledSp,
                    color = nameColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(
    name = "Top Assistants Bento",
    showBackground = true,
    backgroundColor = 0xFFFAF8FF,
    widthDp = 430
)
@Composable
private fun TopAssistantsBentoSectionPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SdpR_16)
    ) {
        TopAssistantsBentoSection(
            topAssistants = listOf(
                previewCharacter("1", "Luna", "A dreamy soul who loves stargazing and poetry."),
                previewCharacter("2", "Sylvia", "Playful, charming and always ready for fun."),
                previewCharacter("3", "Aria", "Gentle, thoughtful and your sweetest listener.")
            ),
            onCharacterClick = {}
        )
    }
}

private fun previewCharacter(id: String, name: String, description: String) = Character(
    id = id,
    name = name,
    slug = id,
    image = null,
    description = description,
    task = "Companion",
    categoryId = "companion",
    category = null,
    sort = 1,
    isHot = true,
    likes = 1_000,
    ratingStars = 4.9,
    ratingCount = 250,
    createdAt = "2026-07-29T00:00:00Z",
    updatedAt = "2026-07-29T00:00:00Z"
)
