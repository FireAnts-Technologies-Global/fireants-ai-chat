package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color000000
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color08030F
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_20
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp
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
            text = stringResource(R.string.character_detail_exclusive_gallery),
            fontFamily = OutfitBold,
            fontWeight = FontWeight.Bold,
            fontSize = SdpR_14.nonScaledSp,
            color = ColorFDFDFD
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
        colors = CardDefaults.cardColors(containerColor = Color150F25),
        border = BorderStroke(SdpR_1, Color322D41),
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
                        .background(Color08030F.copy(alpha = 0.5f))
                )
            }

            if (background.isLocked) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(SdpR_12))
                        .padding(horizontal = SdpR_8, vertical = SdpR_6),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isPurchasing) {
                        ImageLoadingLottie(size = SdpR_20)
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_lock),
                            contentDescription = "Locked",
                            tint = ColorE8C3AC,
                            modifier = Modifier.size(SdpR_16)
                        )
                        Spacer(modifier = Modifier.height(SdpR_4))
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
                                .clip(RoundedCornerShape(SdpR_6))
                                .background(ColorE8C3AC)
                                .padding(horizontal = SdpR_12, vertical = SdpR_4),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = priceText,
                                color = Color000000,
                                fontFamily = OutfitRegular,
                                fontWeight = FontWeight.Bold,
                                fontSize = SdpR_12.nonScaledSp
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
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
