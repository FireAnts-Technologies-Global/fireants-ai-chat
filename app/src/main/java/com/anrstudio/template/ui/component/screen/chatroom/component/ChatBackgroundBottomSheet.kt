package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackground
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color000000
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color08030F
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color161127
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_20
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_32
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatBackgroundBottomSheet(
    backgrounds: List<CharacterBackground>,
    currentBackgroundId: String?,
    purchasingBackgroundId: String?,
    onDismiss: () -> Unit,
    onBackgroundClick: (CharacterBackground) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color161127,
        contentColor = ColorFDFDFD,
        scrimColor = Color.Black.copy(alpha = 0.68f),
        shape = RoundedCornerShape(
            topStart = SdpR_24,
            topEnd = SdpR_24
        ),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = SdpR_12, bottom = SdpR_24)
                    .size(width = SdpR_32, height = SdpR_4)
                    .clip(RoundedCornerShape(SdpR_2))
                    .background(Color322D41)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.chat_more_custom_background),
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_20.nonScaledSp,
                    color = ColorFDFDFD,
                    modifier = Modifier.weight(1f)
                )

                ActionsSheetCloseButton(onClick = onDismiss)
            }

            Spacer(modifier = Modifier.height(SdpR_16))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                items(backgrounds.chunked(3).size) { index ->
                    val rowItems = backgrounds.chunked(3)[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(SdpR_8)
                    ) {
                        rowItems.forEach { bg ->
                            Box(modifier = Modifier.weight(1f)) {
                                ChatBackgroundItemCard(
                                    background = bg,
                                    isPurchasing = purchasingBackgroundId == bg.id,
                                    isSelected = currentBackgroundId == bg.id,
                                    onClick = { onBackgroundClick(bg) }
                                )
                            }
                        }

                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(SdpR_24))
                }
            }
        }
    }
}

@Composable
private fun ChatBackgroundItemCard(
    background: CharacterBackground,
    isPurchasing: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFFFF2E93) else Color322D41
    val borderWidth = if (isSelected) SdpR_2 else SdpR_1

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f),
        shape = RoundedCornerShape(SdpR_16),
        colors = CardDefaults.cardColors(containerColor = Color150F25),
        border = BorderStroke(borderWidth, borderColor),
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
