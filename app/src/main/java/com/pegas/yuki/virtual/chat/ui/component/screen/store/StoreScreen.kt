package com.pegas.yuki.virtual.chat.ui.component.screen.store

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppButton
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppTextHorizontalGradient
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseScreen
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color000000
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color271E38
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_120
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_13
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_18
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_40
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_48
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.appSplashBackground
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.bases.ext.findActivity
import com.pegas.yuki.virtual.chat.ui.component.dialog.LoadingDialog
import com.pegas.yuki.virtual.chat.ui.component.screen.store.component.CoinPackageItem
import com.pegas.yuki.virtual.chat.ui.component.screen.store.component.MembershipPlanItem
import com.pegas.yuki.virtual.chat.ui.component.screen.store.component.SectionHeader
import com.pegas.yuki.virtual.chat.ui.component.screen.store.component.StoreFooter
import com.pegas.yuki.virtual.chat.ui.component.screen.store.component.StoreTopBar
import com.pegas.yuki.virtual.chat.ui.model.asString

@Composable
fun StoreScreen(
    fromScreen: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: StoreViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(StoreIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "StoreScreen",
        fromScreen = fromScreen,
        onEffect = { effect ->
            when (effect) {
                is StoreEffect.ShowToast -> {
                    val msg = if (effect.formatArgs.isEmpty()) {
                        context.getString(effect.messageRes)
                    } else {
                        context.getString(effect.messageRes, *effect.formatArgs.toTypedArray())
                    }
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
                StoreEffect.NavigateBack -> onNavigateBack()
            }
        },
        onError = { error, dismiss ->
            if (error.isUserCancellation) {
                dismiss()
            } else {
                Toast.makeText(context, error.asString(context), Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    ) { state, onIntent ->
        Box(modifier = Modifier.fillMaxSize()) {
            StoreContent(
                state = state,
                onBackClick = onNavigateBack,
                onRetry = { onIntent(StoreIntent.Retry) },
                onPurchaseGem = { packageId ->
                    val activity = context.findActivity()
                    if (activity != null) {
                        onIntent(StoreIntent.PurchaseGemPackage(activity, packageId))
                    }
                },
                onPurchaseMembership = { planId ->
                    val activity = context.findActivity()
                    if (activity != null) {
                        onIntent(StoreIntent.PurchaseMembershipPlan(activity, planId))
                    }
                },
                onRestorePurchases = {
                    val activity = context.findActivity()
                    if (activity != null) {
                        onIntent(StoreIntent.RestorePurchases(activity))
                    }
                }
            )

            if (state.isPurchasing) {
                LoadingDialog()
            }
        }
    }
}

@Composable
private fun StoreContent(
    state: StoreUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit = {},
    onPurchaseGem: (String) -> Unit,
    onPurchaseMembership: (String) -> Unit,
    onRestorePurchases: () -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            StoreTopBar(
                coinCount = state.coinBalance,
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    ImageLoadingLottie(size = SdpR_32)
                }
            } else if (state.coinPackages.isEmpty() && state.membershipPlans.isEmpty()) {
                StoreEmptyContent(
                    onRetry = onRetry,
                    onRestorePurchases = onRestorePurchases
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = SdpR_12,
                        top = SdpR_12,
                        end = SdpR_12,
                        bottom = SdpR_32
                    ),
                    verticalArrangement = Arrangement.spacedBy(SdpR_8),
                    horizontalArrangement = Arrangement.spacedBy(SdpR_8)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionHeader(
                            title = stringResource(id = R.string.store_top_up_coins),
                            subtitle = stringResource(id = R.string.store_unlock_storylines)
                        )
                    }

                    if (state.coinPackages.isNotEmpty()) {
                        items(state.coinPackages) { pkg ->
                            CoinPackageItem(
                                displayName = pkg.displayName ?: stringResource(id = R.string.store_coins_format, pkg.coinAmount),
                                price = pkg.priceText,
                                badge = pkg.bonusBadgeText,
                                isSelected = state.selectedPackageId == pkg.id,
                                onClick = { onPurchaseGem(pkg.id) }
                            )
                        }
                    } else {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            StoreSectionEmptyCard(
                                message = stringResource(id = R.string.store_empty_coin_packages)
                            )
                        }
                    }

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Spacer(modifier = Modifier.height(SdpR_12))
                        SectionHeader(
                            title = stringResource(id = R.string.store_membership_plans),
                            subtitle = stringResource(id = R.string.store_unlimited_vip)
                        )
                    }

                    if (state.membershipPlans.isNotEmpty()) {
                        items(
                            items = state.membershipPlans,
                            span = { GridItemSpan(maxLineSpan) }
                        ) { plan ->
                            MembershipPlanItem(
                                title = plan.title,
                                price = plan.price,
                                durationLabelRes = plan.durationLabelRes,
                                descriptionRes = plan.descriptionRes,
                                benefitsRes = plan.benefitsRes,
                                footerTextRes = plan.footerTextRes,
                                isBestValue = plan.isBestValue,
                                isSelected = state.selectedPlanId == plan.id,
                                onClick = { onPurchaseMembership(plan.id) }
                            )
                        }
                    } else {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            StoreSectionEmptyCard(
                                message = stringResource(id = R.string.store_empty_vip_plans)
                            )
                        }
                    }

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Spacer(modifier = Modifier.height(SdpR_24))
                        StoreFooter(
                            onRestorePurchasesClick = onRestorePurchases
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StoreEmptyContent(
    onRetry: () -> Unit,
    onRestorePurchases: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = SdpR_24)
            .padding(top = SdpR_40, bottom = SdpR_24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.img_empty),
            contentDescription = null,
            modifier = Modifier.size(SdpR_120)
        )

        Spacer(modifier = Modifier.height(SdpR_16))

        Text(
            text = stringResource(id = R.string.store_empty_title),
            color = ColorFDFDFD,
            fontFamily = OutfitBold,
            fontSize = SdpR_18.nonScaledSp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SdpR_8))

        Text(
            text = stringResource(id = R.string.store_empty_subtitle),
            color = ColorAFA5C3,
            fontFamily = OutfitRegular,
            fontSize = SdpR_13.nonScaledSp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SdpR_24))

        AppButton(
            text = stringResource(id = R.string.store_empty_retry),
            gradient = AppTextHorizontalGradient,
            shape = RoundedCornerShape(SdpR_24),
            minHeight = SdpR_48,
            modifier = Modifier.padding(horizontal = SdpR_48),
            onClick = onRetry
        )

        Spacer(modifier = Modifier.weight(1f))

        StoreFooter(
            onRestorePurchasesClick = onRestorePurchases
        )
    }
}

@Composable
private fun StoreSectionEmptyCard(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = ColorFFFFFF,
                shape = RoundedCornerShape(SdpR_12)
            )
            .border(
                width = SdpR_1,
                color = Color271E38,
                shape = RoundedCornerShape(SdpR_12)
            )
            .padding(horizontal = SdpR_16, vertical = SdpR_20),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color000000,
            fontFamily = OutfitRegular,
            fontSize = SdpR_13.nonScaledSp,
            textAlign = TextAlign.Center
        )
    }
}



@Preview(
    name = "Store Screen",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun StoreContentPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appSplashBackground()
    ) {
        StoreContent(
            state = StoreUiState(
                isLoading = false,
                coinBalance = 480,
                coinPackages = listOf(
                    CoinPackageUiModel("1", 100, "100 Coins", null, "52.000 đ"),
                    CoinPackageUiModel("2", 220, "220 Coins", "+20 (9%)", "105.000 đ"),
                    CoinPackageUiModel("3", 550, "550 Coins", "+50 (9%)", "184.000 đ"),
                    CoinPackageUiModel("4", 1200, "1200 Coins", "+200 (17%)", "263.000 đ"),
                    CoinPackageUiModel("5", 2400, "2400 Coins", "+400 (17%)", "526.000 đ"),
                    CoinPackageUiModel("6", 5200, "5200 Coins", "+1200 (23%)", "789.000 đ")
                ),
                membershipPlans = listOf(
                    MembershipPlanUiModel(
                        id = "monthly",
                        title = "Monthly Plan",
                        price = "263,000 đ",
                        durationLabelRes = R.string.store_duration_month,
                        descriptionRes = R.string.store_vip_desc_month,
                        benefitsRes = listOf(R.string.store_vip_benefit_1),
                        footerTextRes = R.string.store_vip_footer_month,
                        isBestValue = false
                    ),
                    MembershipPlanUiModel(
                        id = "annual",
                        title = "Annual Plan",
                        price = "1,300,000 đ",
                        durationLabelRes = R.string.store_duration_year,
                        descriptionRes = R.string.store_vip_desc_year,
                        benefitsRes = listOf(
                            R.string.store_vip_benefit_2,
                            R.string.store_vip_benefit_3
                        ),
                        footerTextRes = R.string.store_vip_footer_year,
                        isBestValue = true
                    )
                )
            ),
            onBackClick = {},
            onPurchaseGem = {},
            onPurchaseMembership = {},
            onRestorePurchases = {}
        )
    }
}
