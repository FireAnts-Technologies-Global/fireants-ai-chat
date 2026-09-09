package com.pegas.aura.aigirlfriend.soul.ui.component.bottomsheet

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_18
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_21
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_32
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_40
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_56
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_7
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_9
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.ui.bases.ext.findActivity
import com.pegas.aura.aigirlfriend.soul.ui.bases.navigation.AppRoutes
import com.pegas.aura.aigirlfriend.soul.ui.bases.navigation.LocalNavController
import com.pegas.aura.aigirlfriend.soul.ui.component.dialog.LoadingDialog
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.CoinPackageUiModel
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.StoreEffect
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.StoreIntent
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.StoreUiState
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.StoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsBottomSheet(
    onDismiss: () -> Unit,
    viewModel: StoreViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedPackageId by remember { mutableStateOf<String?>(null) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.handleIntent(StoreIntent.Initialize)
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StoreEffect.ShowToast -> {
                    val msg = if (effect.formatArgs.isEmpty()) {
                        context.getString(effect.messageRes)
                    } else {
                        context.getString(effect.messageRes, *effect.formatArgs.toTypedArray())
                    }
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }

                StoreEffect.NavigateBack -> {
                    // Do nothing for bottom sheet
                }
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.Transparent,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to Color0B0616,
                        0.5f to Color160C2C,
                        1.0f to Color07030D
                    ),
                    shape = RoundedCornerShape(topStart = SdpR_24, topEnd = SdpR_24)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 22.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 32.dp, height = 4.dp)
                        .background(
                            color = ColorE5E5E7.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }

            CoinsBottomSheetContent(
                state = state,
                selectedPackageId = selectedPackageId,
                onPackageSelected = { selectedPackageId = it },
                onBuyClick = { packageId ->
                    val activity = context.findActivity()
                    if (activity != null) {
                        viewModel.handleIntent(
                            StoreIntent.PurchaseGemPackage(
                                activity,
                                packageId
                            )
                        )
                    }
                },
                onUpgradeProClick = {
                    onDismiss()
                    navController.navigate(AppRoutes.SUBSCRIPTION)
                }
            )
        }
    }
}


@Composable
fun CoinsBottomSheetContent(
    state: StoreUiState,
    selectedPackageId: String?,
    onPackageSelected: (String) -> Unit,
    onBuyClick: (String) -> Unit,
    onUpgradeProClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SdpR_18)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_12),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.no_credits_left),
                fontFamily = OutfitExtraBold,
                fontSize = SdpR_21.nonScaledSp,
                color = ColorFDFDFD
            )

            Box(
                modifier = Modifier
                    .border(
                        width = SdpR_1,
                        color = Color17FFFFFF,
                        shape = RoundedCornerShape(SdpR_24)
                    )
                    .background(
                        color = Color17FFFFFF,
                        shape = RoundedCornerShape(SdpR_24)
                    )
                    .padding(
                        horizontal = SdpR_12,
                        vertical = SdpR_8
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.img_coin),
                        contentDescription = null,
                        modifier = Modifier.size(SdpR_13)
                    )
                    Spacer(modifier = Modifier.width(SdpR_4))
                    Text(
                        text = stringResource(id = R.string.balance_format, state.coinBalance),
                        fontFamily = OutfitBold,
                        fontSize = SdpR_10.nonScaledSp,
                        color = ColorFDFDFD
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(SdpR_12))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_16)
                .height(SdpR_40)
                .clip(RoundedCornerShape(SdpR_32))
                .background(ColorD65A98)
                .clickable {
                    onUpgradeProClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_crow),
                    contentDescription = null,
                    modifier = Modifier.size(SdpR_14)
                )
                Spacer(modifier = Modifier.width(SdpR_6))

                Text(
                    text = stringResource(id = R.string.upgrade_pro),
                    fontFamily = OutfitBold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = ColorFDFDFD,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Spacer(modifier = Modifier.height(SdpR_16))

        Text(
            modifier = Modifier.padding(horizontal = SdpR_12),
            text = stringResource(id = R.string.coins),
            fontFamily = OutfitExtraBold,
            fontSize = SdpR_9.nonScaledSp,
            color = ColorA197B9
        )
        Spacer(modifier = Modifier.height(SdpR_8))

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                ImageLoadingLottie(size = SdpR_56)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp),
                contentPadding = PaddingValues(horizontal = SdpR_12),
                verticalArrangement = Arrangement.spacedBy(SdpR_8),
                horizontalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                items(state.coinPackages) { pkg ->
                    BottomSheetCoinPackageItem(
                        displayName = pkg.displayName ?: "${pkg.coinAmount} Gems",
                        price = pkg.priceText,
                        badge = pkg.bonusBadgeText,
                        isSelected = selectedPackageId == pkg.id,
                        onClick = { onPackageSelected(pkg.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(SdpR_24))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SdpR_16)
                    .height(SdpR_40)
                    .clip(RoundedCornerShape(SdpR_32))
                    .background(if (selectedPackageId != null) ColorD65A98 else Color150F25)
                    .clickable(enabled = selectedPackageId != null) {
                        if (selectedPackageId != null) {
                            onBuyClick(selectedPackageId)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.buy_coins),
                    fontFamily = OutfitBold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = if (selectedPackageId != null) ColorFDFDFD else ColorFDFDFD.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }

    if (state.isPurchasing) {
        LoadingDialog()
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430
)
@Composable
private fun CoinsBottomSheetContentPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color0B0616,
                    0.5f to Color160C2C,
                    1.0f to Color07030D
                )
            )
    ) {
        CoinsBottomSheetContent(
            state = StoreUiState(
                isLoading = false,
                coinBalance = 80,
                coinPackages = listOf(
                    CoinPackageUiModel("1", 100, "100 Gems", null, "52.000 đ"),
                    CoinPackageUiModel("2", 220, "220 Gems", "+20 (9%)", "105.000 đ"),
                    CoinPackageUiModel("3", 550, "550 Gems", "+50 (9%)", "184.000 đ"),
                    CoinPackageUiModel("4", 1200, "1200 Gems", "+200 (17%)", "263.000 đ")
                )
            ),
            selectedPackageId = "2",
            onPackageSelected = {},
            onBuyClick = {},
            onUpgradeProClick = {}
        )
    }
}

@Composable
fun BottomSheetCoinPackageItem(
    displayName: String,
    price: String,
    badge: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val borderColor = if (isSelected) ColorD65A98 else Color17FFFFFF
    val borderWidth = if (isSelected) SdpR_2 else SdpR_1
    val bgColor = if (isSelected) Color0FD65A98 else Color0AFFFFFF

    var extraAmount: String? = null
    var percentage: String? = null
    if (!badge.isNullOrBlank()) {
        if (badge.contains("(") && badge.contains(")")) {
            extraAmount = badge.substringBefore("(").trim()
            percentage = badge.substringAfter("(").substringBefore(")").trim()
            if (!percentage.startsWith("+") && !percentage.startsWith("-")) {
                percentage = "+$percentage"
            }
        } else {
            extraAmount = badge
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SdpR_16))
            .background(bgColor)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(SdpR_16)
            )
            .clickable { onClick() }
            .padding(SdpR_10),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.img_coin),
                    contentDescription = null,
                    modifier = Modifier.size(SdpR_13)
                )

                Spacer(modifier = Modifier.width(SdpR_4))

                val numGems = displayName.substringBefore(" ")
                Text(
                    text = numGems,
                    fontFamily = OutfitBold,
                    fontSize = SdpR_13.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    color = ColorFDFDFD
                )

                if (!extraAmount.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.width(SdpR_4))
                    Box(
                        modifier = Modifier
                            .background(Color2D253A, RoundedCornerShape(SdpR_4))
                            .padding(horizontal = SdpR_4, vertical = SdpR_4),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = extraAmount,
                            fontFamily = OutfitSemiBold,
                            fontSize = SdpR_7.nonScaledSp,
                            color = ColorE8C3AC
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(SdpR_2))

            Text(
                text = price,
                fontFamily = OutfitSemiBold,
                fontSize = SdpR_11.nonScaledSp,
                color = ColorE8C3AC
            )
        }

        if (!percentage.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color16302B, RoundedCornerShape(SdpR_6))
                    .padding(horizontal = SdpR_6, vertical = SdpR_4),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = percentage,
                    fontFamily = OutfitBold,
                    fontSize = SdpR_7.nonScaledSp,
                    color = Color38D668
                )
            }
        }
    }
}
