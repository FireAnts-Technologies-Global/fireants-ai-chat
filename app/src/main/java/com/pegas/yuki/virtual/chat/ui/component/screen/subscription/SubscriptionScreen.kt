package com.pegas.yuki.virtual.chat.ui.component.screen.subscription

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.app.AppConstants
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppButton
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppText
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseScreen
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.PlayfairDisplayBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_13
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_22
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_250
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_28
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_36
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_38
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_40
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_52
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_80
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.bases.ext.findActivity
import com.pegas.yuki.virtual.chat.ui.component.dialog.LoadingDialog
import com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component.SubscriptionFeatureHighlightsCard
import com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component.SubscriptionFinePrintText
import com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component.SubscriptionPlanCard
import com.pegas.yuki.virtual.chat.ui.model.asString

private val SubGradientCTA = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFFF2E93),
        Color(0xFFFF5E62),
        Color(0xFFFF7A45)
    )
)

@Composable
fun SubscriptionScreen(
    viewModel: SubscriptionViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(SubscriptionIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "SubscriptionScreen",
        onEffect = { effect ->
            when (effect) {
                is SubscriptionEffect.ShowToast -> {
                    val msg = if (effect.formatArgs.isEmpty()) {
                        context.getString(effect.messageRes)
                    } else {
                        context.getString(effect.messageRes, *effect.formatArgs.toTypedArray())
                    }
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }

                SubscriptionEffect.NavigateBack -> onNavigateUp()
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
            SubscriptionScreenContent(
                state = state,
                onBackClick = onNavigateUp,
                onPlanSelect = { onIntent(SubscriptionIntent.SelectPlan(it)) },
                onSubscribeClick = {
                    state.selectedPlanId?.let { planId ->
                        context.findActivity()?.let { activity ->
                            onIntent(SubscriptionIntent.PurchasePlan(activity, planId))
                        }
                    }
                },
                onRestoreClick = {
                    onIntent(SubscriptionIntent.RestorePurchases)
                },
                onRetry = {
                    onIntent(SubscriptionIntent.Retry)
                }
            )

            if (state.isPurchasing) {
                LoadingDialog()
            }
        }
    }
}

@Composable
fun SubscriptionScreenContent(
    state: SubscriptionUiState,
    onBackClick: () -> Unit,
    onPlanSelect: (String) -> Unit,
    onSubscribeClick: () -> Unit,
    onRestoreClick: () -> Unit,
    onRetry: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_sup),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(bottom = SdpR_32),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(SdpR_250))

            AppText(
                text = stringResource(id = R.string.sub_paywall_title),
                color = Color(0xFF1E142F),
                fontFamily = PlayfairDisplayBold,
                fontWeight = FontWeight.Bold,
                fontSize = SdpR_28.nonScaledSp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SdpR_16)
            )

            Spacer(modifier = Modifier.height(SdpR_2))

            AppText(
                text = stringResource(id = R.string.sub_paywall_subtitle),
                color = Color(0xFF4C2878),
                fontFamily = ManropeRegular,
                fontSize = SdpR_14.nonScaledSp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SdpR_16)
            )

            Spacer(modifier = Modifier.height(SdpR_20))

            SubscriptionFeatureHighlightsCard(
                modifier = Modifier.padding(horizontal = SdpR_16)
            )

            Spacer(modifier = Modifier.height(SdpR_16))

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ImageLoadingLottie(size = SdpR_40)
                }
            } else if (state.plans.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SdpR_16)
                        .background(
                            color = Color.White.copy(alpha = 0.95f),
                            shape = RoundedCornerShape(SdpR_16)
                        )
                        .border(
                            width = SdpR_1,
                            color = Color(0xFFEDE7F6),
                            shape = RoundedCornerShape(SdpR_16)
                        )
                        .padding(vertical = SdpR_24, horizontal = SdpR_16),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_empty),
                        contentDescription = null,
                        modifier = Modifier.size(SdpR_80)
                    )
                    Spacer(modifier = Modifier.height(SdpR_12))
                    AppText(
                        text = stringResource(id = R.string.sub_empty_plans),
                        color = Color(0xFF6E6482),
                        fontFamily = OutfitRegular,
                        fontSize = SdpR_13.nonScaledSp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(SdpR_16))
                    AppButton(
                        text = stringResource(id = R.string.store_empty_retry),
                        gradient = SubGradientCTA,
                        shape = RoundedCornerShape(SdpR_20),
                        minHeight = SdpR_36,
                        modifier = Modifier.padding(horizontal = SdpR_24),
                        onClick = onRetry
                    )
                }
            } else {
                val monthlyPlan = state.plans.firstOrNull { !it.isAnnual }
                val yearlyPlan = state.plans.firstOrNull { it.isAnnual }

                monthlyPlan?.let {
                    SubscriptionPlanCard(
                        plan = it,
                        isSelected = state.selectedPlanId == it.id,
                        onClick = { onPlanSelect(it.id) },
                        modifier = Modifier.padding(horizontal = SdpR_16)
                    )
                    Spacer(modifier = Modifier.height(SdpR_12))
                }

                yearlyPlan?.let {
                    SubscriptionPlanCard(
                        plan = it,
                        isSelected = state.selectedPlanId == it.id,
                        onClick = { onPlanSelect(it.id) },
                        modifier = Modifier.padding(horizontal = SdpR_16)
                    )
                }

                val selectedPlan = state.plans.firstOrNull { it.id == state.selectedPlanId }
                val breakdownText = selectedPlan?.monthlyBreakdownText
                if (!breakdownText.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(SdpR_8))

                    AppText(
                        text = breakdownText,
                        color = Color(0xFF7A6F90),
                        fontFamily = OutfitRegular,
                        fontSize = SdpR_12.nonScaledSp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (state.plans.isNotEmpty()) {
                Spacer(modifier = Modifier.height(SdpR_16))

                AppButton(
                    text = stringResource(id = R.string.sub_subscribe_now),
                    onClick = onSubscribeClick,
                    enabled = state.selectedPlanId != null,
                    gradient = SubGradientCTA,
                    shape = RoundedCornerShape(SdpR_24),
                    minHeight = SdpR_52,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SdpR_16)
                )
            }

            Spacer(modifier = Modifier.height(SdpR_12))

            AppText(
                text = stringResource(id = R.string.sub_cancel_anytime),
                color = Color(0xFF6E6482),
                fontFamily = ManropeRegular,
                fontSize = SdpR_12.nonScaledSp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        try {
                            uriHandler.openUri("https://play.google.com/store/account/subscriptions?package=${context.packageName}")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    .padding(vertical = SdpR_4)
            )

            Spacer(modifier = Modifier.height(SdpR_16))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SdpR_16)
            ) {
                SubscriptionFinePrintText(textRes = R.string.sub_fine_print_1)
                SubscriptionFinePrintText(textRes = R.string.sub_fine_print_2)
                SubscriptionFinePrintText(textRes = R.string.sub_fine_print_3)
                SubscriptionFinePrintText(
                    textRes = R.string.sub_fine_print_4,
                    onClick = {
                        try {
                            uriHandler.openUri("https://play.google.com/store/account/subscriptions?package=${context.packageName}")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(SdpR_16))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppText(
                    text = stringResource(id = R.string.sub_terms),
                    color = Color(0xFF6E6482),
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_12.nonScaledSp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            try {
                                uriHandler.openUri(AppConstants.LINK_PRIVACY_POLICY)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        .padding(horizontal = SdpR_6, vertical = SdpR_4)
                )

                AppText(
                    text = "|",
                    color = Color(0xFF9E92B3),
                    fontSize = SdpR_12.nonScaledSp
                )

                AppText(
                    text = stringResource(id = R.string.sub_privacy),
                    color = Color(0xFF6E6482),
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_12.nonScaledSp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            try {
                                uriHandler.openUri(AppConstants.LINK_PRIVACY_POLICY)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        .padding(horizontal = SdpR_6, vertical = SdpR_4)
                )

                AppText(
                    text = "|",
                    color = Color(0xFF9E92B3),
                    fontSize = SdpR_12.nonScaledSp
                )

                AppText(
                    text = stringResource(id = R.string.sub_restore_purchase),
                    color = Color(0xFF6E6482),
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_12.nonScaledSp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable { onRestoreClick() }
                        .padding(horizontal = SdpR_6, vertical = SdpR_4)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = SdpR_16, vertical = SdpR_12),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(SdpR_38)
                    .clip(CircleShape)
                    .background(Color(0x52FFFFFF))
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_circle),
                    contentDescription = "Close",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(SdpR_22)
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(SdpR_20))
                    .background(Color.White.copy(alpha = 0.85f))
                    .clickable { onRestoreClick() }
                    .padding(horizontal = SdpR_16, vertical = SdpR_8),
                contentAlignment = Alignment.Center
            ) {
                AppText(
                    text = stringResource(id = R.string.sub_restore_purchase),
                    color = Color(0xFF4C2878),
                    fontFamily = ManropeSemiBold,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = SdpR_12.nonScaledSp
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SubscriptionScreenPreview() {
    SubscriptionScreenContent(
        state = SubscriptionUiState(
            plans = listOf(
                SubscriptionPlanUiModel(
                    id = "1",
                    storeProductId = "monthly",
                    title = "Monthly",
                    isAnnual = false,
                    priceText = "$9.99 / month",
                    dailyBonusGems = "500",
                    monthlyBreakdownText = "$9.99/month, billed monthly"
                ),
                SubscriptionPlanUiModel(
                    id = "2",
                    storeProductId = "yearly",
                    title = "Yearly",
                    isAnnual = true,
                    priceText = "$49.99 / year",
                    dailyBonusGems = "750",
                    savePercentageText = "SAVE 58%",
                    monthlyBreakdownText = "$4.16/month, billed annually"
                )
            ),
            selectedPlanId = "2"
        ),
        onBackClick = {},
        onPlanSelect = {},
        onSubscribeClick = {},
        onRestoreClick = {}
    )
}