package com.pegas.yuki.virtual.chat.ui.component.bottomsheet

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppButton
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppText
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppTextHorizontalGradient
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_13
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_18
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_26
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_28
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_50
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.bases.ext.findActivity
import com.pegas.yuki.virtual.chat.ui.bases.navigation.AppRoutes
import com.pegas.yuki.virtual.chat.ui.bases.navigation.LocalNavController
import com.pegas.yuki.virtual.chat.ui.component.dialog.LoadingDialog
import com.pegas.yuki.virtual.chat.ui.component.screen.store.CoinPackageUiModel
import com.pegas.yuki.virtual.chat.ui.component.screen.store.StoreEffect
import com.pegas.yuki.virtual.chat.ui.component.screen.store.StoreIntent
import com.pegas.yuki.virtual.chat.ui.component.screen.store.StoreUiState
import com.pegas.yuki.virtual.chat.ui.component.screen.store.StoreViewModel

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

    LaunchedEffect(state.coinPackages) {
        if (selectedPackageId == null && state.coinPackages.isNotEmpty()) {
            selectedPackageId = state.coinPackages.first().id
        }
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
        containerColor = Color.White,
        scrimColor = Color.Black.copy(alpha = 0.45f),
        shape = RoundedCornerShape(topStart = SdpR_28, topEnd = SdpR_28),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
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
            .padding(top = SdpR_20, bottom = SdpR_24)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.no_credits_left),
                fontFamily = ManropeBold,
                fontWeight = FontWeight.Bold,
                fontSize = SdpR_20.nonScaledSp,
                color = Color(0xFF150F25)
            )

            Box(
                modifier = Modifier
                    .border(
                        width = SdpR_1,
                        color = Color(0xFFE5E7EB),
                        shape = RoundedCornerShape(SdpR_24)
                    )
                    .background(
                        color = Color(0xFFF9FAFC),
                        shape = RoundedCornerShape(SdpR_24)
                    )
                    .padding(
                        horizontal = SdpR_12,
                        vertical = SdpR_10
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.img_coin),
                        contentDescription = null,
                        modifier = Modifier.size(SdpR_18)
                    )
                    Spacer(modifier = Modifier.width(SdpR_6))
                    Text(
                        text = stringResource(id = R.string.balance_format, state.coinBalance),
                        fontFamily = ManropeBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = SdpR_13.nonScaledSp,
                        color = Color(0xFF150F25)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(SdpR_16))

        AppButton(
            text = stringResource(id = R.string.upgrade_pro),
            iconRes = R.drawable.ic_crow,
            iconTint = Color.White,
            iconSize = SdpR_16,
            gradient = AppTextHorizontalGradient,
            shape = RoundedCornerShape(SdpR_26),
            minHeight = SdpR_50,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_16),
            onClick = onUpgradeProClick
        )

        Spacer(modifier = Modifier.height(SdpR_20))

        Text(
            modifier = Modifier.padding(horizontal = SdpR_16),
            text = stringResource(id = R.string.coins),
            fontFamily = ManropeBold,
            fontWeight = FontWeight.Bold,
            fontSize = SdpR_12.nonScaledSp,
            color = Color(0xFF150F25)
        )

        Spacer(modifier = Modifier.height(SdpR_10))

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                ImageLoadingLottie(size = SdpR_32)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 420.dp),
                contentPadding = PaddingValues(horizontal = SdpR_16),
                verticalArrangement = Arrangement.spacedBy(SdpR_10),
                horizontalArrangement = Arrangement.spacedBy(SdpR_10)
            ) {
                items(state.coinPackages) { pkg ->
                    BottomSheetCoinPackageItem(
                        displayName = pkg.displayName ?: "${pkg.coinAmount} Coins",
                        price = pkg.priceText,
                        badge = pkg.bonusBadgeText,
                        isSelected = selectedPackageId == pkg.id,
                        onClick = { onPackageSelected(pkg.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(SdpR_20))

            AppButton(
                text = stringResource(id = R.string.buy_coins),
                enabled = selectedPackageId != null,
                gradient = AppTextHorizontalGradient,
                disabledColor = Color(0xFFF3F4F6),
                disabledTextColor = Color(0xFF8E889B),
                shape = RoundedCornerShape(SdpR_26),
                minHeight = SdpR_50,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SdpR_16),
                onClick = {
                    if (selectedPackageId != null) {
                        onBuyClick(selectedPackageId)
                    }
                }
            )
        }
    }

    if (state.isPurchasing) {
        LoadingDialog()
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 430
)
@Composable
private fun CoinsBottomSheetContentPreview() {
    CoinsBottomSheetContent(
        state = StoreUiState(
            isLoading = false,
            coinBalance = 80,
            coinPackages = listOf(
                CoinPackageUiModel("1", 100, "100 Coins", null, "52.000 đ"),
                CoinPackageUiModel("2", 220, "220 Coins", "+20 (9%)", "105.000 đ"),
                CoinPackageUiModel("3", 550, "550 Coins", "+50 (9%)", "184.000 đ"),
                CoinPackageUiModel("4", 1200, "1200 Coins", "+200 (17%)", "263.000 đ"),
                CoinPackageUiModel("5", 2400, "2400 Coins", "+400 (17%)", "526.000 đ"),
                CoinPackageUiModel("6", 5200, "5200 Coins", "+1200 (23%)", "789.000 đ")
            )
        ),
        selectedPackageId = "1",
        onPackageSelected = {},
        onBuyClick = {},
        onUpgradeProClick = {}
    )
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

    val selectedBorder = AppTextHorizontalGradient
    val unselectedBorder = SolidColor(Color(0xFFE5E7EB))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SdpR_16))
            .background(if (isSelected) Color(0xFFFFF0F5) else Color.White)
            .border(
                border = BorderStroke(
                    width = if (isSelected) SdpR_2 else SdpR_1,
                    brush = if (isSelected) selectedBorder else unselectedBorder
                ),
                shape = RoundedCornerShape(SdpR_16)
            )
            .clickable { onClick() }
            .padding(horizontal = SdpR_12, vertical = SdpR_12),
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
                    modifier = Modifier.size(SdpR_18)
                )

                Spacer(modifier = Modifier.width(SdpR_6))

                val numCoins = displayName.substringBefore(" ")
                Text(
                    text = numCoins,
                    fontFamily = ManropeBold,
                    fontSize = SdpR_16.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF150F25)
                )

                if (!extraAmount.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.width(SdpR_4))
                    Box(
                        modifier = Modifier
                            .background(Color(0x12000000), RoundedCornerShape(SdpR_4))
                            .padding(horizontal = SdpR_4, vertical = SdpR_2),
                        contentAlignment = Alignment.Center
                    ) {
                        AppText(
                            text = extraAmount,
                            fontFamily = ManropeSemiBold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = SdpR_10.nonScaledSp,
                            gradient = AppTextHorizontalGradient,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(SdpR_6))

            AppText(
                text = price,
                fontFamily = ManropeBold,
                fontSize = SdpR_14.nonScaledSp,
                fontWeight = FontWeight.Bold,
                gradient = AppTextHorizontalGradient,
                color = if (isSelected) Color.Unspecified else Color(0xFFFF4081)
            )
        }

        if (!percentage.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color(0x2622C55E), RoundedCornerShape(SdpR_6))
                    .padding(horizontal = SdpR_6, vertical = SdpR_2),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = percentage,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_10.nonScaledSp,
                    color = Color(0xFF4ADE80)
                )
            }
        }
    }
}
