package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackground
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage

@Composable
internal fun ExclusivePhotoGallery(
    backgrounds: List<CharacterBackground>,
    purchasingBackgroundId: String?,
    onBackgroundClick: (CharacterBackground) -> Unit,
    modifier: Modifier = Modifier
) {
    if (backgrounds.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_12)
    ) {
        Text(
            text = stringResource(R.string.character_detail_exclusive_photo_gallery),
            fontFamily = ManropeBold,
            fontWeight = FontWeight.Bold,
            fontSize = SdpR_18.nonScaledSp,
            color = Color110640
        )

        Column(verticalArrangement = Arrangement.spacedBy(SdpR_12)) {
            backgrounds.chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SdpR_12)
                ) {
                    rowItems.forEach { bg ->
                        Box(modifier = Modifier.weight(1f)) {
                            BackgroundItemCard(
                                background = bg,
                                isPurchasing = purchasingBackgroundId == bg.id,
                                onClick = { onBackgroundClick(bg) }
                            )
                        }
                    }

                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun BackgroundItemCard(
    background: CharacterBackground,
    isPurchasing: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f),
        shape = RoundedCornerShape(SdpR_16),
        colors = CardDefaults.cardColors(containerColor = Color66FFFFFF),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingAsyncImage(
                imageUrl = background.imageUrl,
                contentDescription = background.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (background.isLocked) Modifier.blur(radius = 16.dp) else Modifier
                    )
            )

            if (background.isLocked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.25f))
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = SdpR_10)
                ) {
                    if (isPurchasing) {
                        ImageLoadingLottie(size = SdpR_20)
                    } else {
                        val priceText = if (background.priceCoins > 0) {
                            background.priceCoins.toString()
                        } else {
                            background.unlockLevel.toString()
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(SdpR_4),
                            modifier = Modifier
                                .clip(RoundedCornerShape(percent = 50))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(ColorFF41BC, ColorFF8040)
                                    )
                                )
                                .padding(horizontal = SdpR_10, vertical = SdpR_4)
                        ) {
                            Text(
                                text = priceText,
                                color = ColorFFFFFF,
                                fontFamily = OutfitBold,
                                fontWeight = FontWeight.Bold,
                                fontSize = SdpR_12.nonScaledSp
                            )
                            Image(
                                painter = painterResource(R.drawable.img_coin),
                                contentDescription = null,
                                modifier = Modifier.size(SdpR_14)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ExclusivePhotoGalleryPreview() {
    ExclusivePhotoGallery(
        backgrounds = (1..10).map { index ->
            CharacterBackground(
                id = "$index",
                characterId = "char_$index",
                name = "Background $index",
                imageUrl = "https://example.com/bg$index.jpg",
                description = "Background $index description",
                priceCoins = if (index % 2 == 0) 15 else 0,
                unlockLevel = if (index % 2 == 0) 0 else 5,
                isDefault = index == 1,
                isLocked = index != 1,
                isUnlocked = index == 1
            )
        },
        purchasingBackgroundId = null,
        onBackgroundClick = {}
    )
}
