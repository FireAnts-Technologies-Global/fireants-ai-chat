package com.pegas.aura.aigirlfriend.soul.ui.component.screen.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage

@Composable
fun RecommendSection(
    characters: List<Character>,
    onCharacterClick: (String) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (characters.isEmpty()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SdpR_12)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SdpR_6)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_vector),
                    contentDescription = stringResource(R.string.close),
                    tint = ColorB440F2,
                    modifier = Modifier.size(SdpR_13)
                )
                Text(
                    text = stringResource(R.string.home_recommend_for_you),
                    fontFamily = ManropeSemiBold,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = SdpR_16.nonScaledSp,
                    color = Color110640
                )
            }

            Text(
                text = stringResource(R.string.home_see_all),
                fontFamily = ManropeSemiBold,
                fontWeight = FontWeight.SemiBold,
                fontSize = SdpR_12.nonScaledSp,
                color = ColorB440F2,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onSeeAllClick
                )
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = SdpR_16),
            horizontalArrangement = Arrangement.spacedBy(SdpR_12)
        ) {
            items(
                items = characters,
                key = { it.id },
                contentType = { "recommend_character" }
            ) { character ->
                RecommendCharacterCard(
                    character = character,
                    onClick = { onCharacterClick(character.slug) }
                )
            }
        }
    }
}

@Composable
private fun RecommendCharacterCard(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(SdpR_145)
            .height(SdpR_195)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(SdpR_20),
        colors = CardDefaults.cardColors(containerColor = Color161127),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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
                                0.35f to Color.Transparent,
                                0.65f to Color08030F.copy(alpha = 0.45f),
                                1.0f to Color08030F.copy(alpha = 0.95f)
                            )
                        )
                    )
            )

            if (character.isHot) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = SdpR_10, top = SdpR_10)
                        .background(
                            color = ColorFF41A3,
                            shape = RoundedCornerShape(SdpR_10)
                        )
                        .padding(horizontal = SdpR_8, vertical = SdpR_2)
                ) {
                    Text(
                        text = stringResource(R.string.character_detail_hot),
                        fontFamily = ManropeExtraBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = SdpR_10.nonScaledSp,
                        color = ColorFDFDFD
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(SdpR_10),
                verticalArrangement = Arrangement.spacedBy(SdpR_1)
            ) {
                Text(
                    text = character.name,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_15.nonScaledSp,
                    color = ColorFDFDFD,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (character.description.isNotBlank()) {
                    Text(
                        text = character.description,
                        fontFamily = ManropeRegular,
                        fontWeight = FontWeight.Normal,
                        fontSize = SdpR_12.nonScaledSp,
                        lineHeight = SdpR_13.nonScaledSp,
                        color = ColorD9D9D9,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
