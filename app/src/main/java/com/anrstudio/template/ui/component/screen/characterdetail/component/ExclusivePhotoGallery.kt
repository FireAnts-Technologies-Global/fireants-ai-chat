package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

        Column(verticalArrangement = Arrangement.spacedBy(SdpR_8)) {
            backgrounds.chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SdpR_8)
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
            .aspectRatio(0.85f),
        shape = RoundedCornerShape(SdpR_16),
        colors = CardDefaults.cardColors(containerColor = ColorFFFFFF),
        border = BorderStroke(SdpR_1, ColorE9DDF2),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingAsyncImage(
                imageUrl = background.imageUrl,
                contentDescription = background.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (background.isLocked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color000000.copy(alpha = 0.35f))
                )

                if (isPurchasing) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageLoadingLottie(size = SdpR_24)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(SdpR_36)
                            .clip(CircleShape)
                            .background(Color000000.copy(alpha = 0.4f))
                            .border(BorderStroke(SdpR_1, ColorFFFFFF.copy(alpha = 0.3f)), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_lock),
                            contentDescription = null,
                            tint = ColorFFFFFF,
                            modifier = Modifier.size(SdpR_16)
                        )
                    }

                    val priceText = if (background.priceCoins > 0) {
                        stringResource(
                            R.string.character_detail_coins_format,
                            background.priceCoins
                        )
                    } else {
                        stringResource(
                            R.string.character_detail_level_format,
                            background.unlockLevel
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = SdpR_8)
                            .clip(RoundedCornerShape(SdpR_100))
                            .background(AppButtonVerticalGradient)
                            .padding(horizontal = SdpR_10, vertical = SdpR_4),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = priceText,
                            color = ColorFFFFFF,
                            fontFamily = ManropeBold,
                            fontWeight = FontWeight.Bold,
                            fontSize = SdpR_11.nonScaledSp
                        )
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
                characterId = "char1",
                name = "Bg $index",
                imageUrl = "",
                description = "Bg $index",
                priceCoins = if (index % 2 == 0) 15 else 0,
                unlockLevel = index + 1,
                isDefault = index == 1,
                isLocked = index != 1,
                isUnlocked = index == 1
            )
        },
        purchasingBackgroundId = null,
        onBackgroundClick = {}
    )
}
