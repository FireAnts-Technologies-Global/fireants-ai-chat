package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.unit.dp
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackground
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage

import androidx.compose.material3.rememberModalBottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatBackgroundBottomSheet(
    backgrounds: List<CharacterBackground>,
    currentBackgroundId: String?,
    purchasingBackgroundId: String?,
    onDismiss: () -> Unit,
    onBackgroundClick: (CharacterBackground) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = ColorFFFFFF,
        contentColor = ColorFDFDFD,
        scrimColor = Color.Black.copy(alpha = 0.68f),
        shape = RoundedCornerShape(
            topStart = SdpR_24,
            topEnd = SdpR_24
        ),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = SdpR_12, bottom = SdpR_20)
                    .size(width = SdpR_48, height = SdpR_4)
                    .clip(RoundedCornerShape(SdpR_2))
                    .background(Color(0xFFE5E5EA))
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
                    color = Color000000,
                    modifier = Modifier.weight(1f)
                )

                ActionsSheetCloseButton(onClick = onDismiss)
            }

            Spacer(modifier = Modifier.height(SdpR_16))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(SdpR_12)
            ) {
                items(backgrounds.chunked(3).size) { index ->
                    val rowItems = backgrounds.chunked(3)[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(SdpR_12)
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
    val selectedBorder = if (isSelected) {
        BorderStroke(
            width = SdpR_2,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFF8040),
                    Color(0xFFFF41BC),
                    Color(0xFFDC60FF)
                )
            )
        )
    } else null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f),
        shape = RoundedCornerShape(SdpR_16),
        colors = CardDefaults.cardColors(containerColor = Color150F25),
        border = selectedBorder,
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
