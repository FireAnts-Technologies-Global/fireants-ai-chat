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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.app.AppConstants
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseScreen
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color07030D
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color0B0616
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color160C2C
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorBE5AD2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD25A9F
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD65A98
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_11
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_13
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_28
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_3
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_40
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_48
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_56
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import androidx.compose.ui.text.style.TextAlign
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppButton
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppTextHorizontalGradient
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color271E38
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_36
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_80
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.bases.ext.findActivity
import com.pegas.yuki.virtual.chat.ui.component.dialog.LoadingDialog
import com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component.SubscriptionFeatureItem
import com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component.SubscriptionFinePrintText
import com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component.SubscriptionPlanCard
import com.pegas.yuki.virtual.chat.ui.model.asString

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color0B0616,
                    0.5f to Color160C2C,
                    1.0f to Color07030D
                )
            )
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ImageLoadingLottie(size = SdpR_56)
            }

            // Still show close button when loading
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SdpR_12),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close_new),
                        contentDescription = "Close",
                        tint = ColorFDFDFD
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_banner_sup),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SdpR_12),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(R.drawable.ic_close_new),
                                contentDescription = "Close",
                                tint = ColorFDFDFD
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color0B0616)
                                )
                            )
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SdpR_12)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .border(
                                width = SdpR_1,
                                color = ColorD65A98,
                                shape = RoundedCornerShape(SdpR_16)
                            )
                            .padding(horizontal = SdpR_12, vertical = SdpR_6),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite),
                                contentDescription = null,
                                tint = ColorD65A98,
                                modifier = Modifier.size(SdpR_12)
                            )
                            Spacer(modifier = Modifier.width(SdpR_6))
                            Text(
                                text = stringResource(id = R.string.sub_vip_member),
                                color = ColorD65A98,
                                fontFamily = OutfitBold,
                                fontSize = SdpR_11.nonScaledSp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(SdpR_16))

                    val monthlyPlan = state.plans.firstOrNull { !it.isAnnual }
                    val yearlyPlan = state.plans.firstOrNull { it.isAnnual }

                    Text(
                        text = stringResource(id = R.string.sub_get_premium_access),
                        color = ColorFDFDFD,
                        fontFamily = OutfitExtraBold,
                        fontSize = SdpR_28.nonScaledSp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(SdpR_8))

                    if (state.plans.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_coin),
                                contentDescription = null,
                                modifier = Modifier.size(SdpR_13)
                            )
                            Spacer(modifier = Modifier.width(SdpR_6))

                            val selectedPlan =
                                state.plans.firstOrNull { it.id == state.selectedPlanId } ?: monthlyPlan
                            val isAnnual = selectedPlan?.isAnnual == true
                            val bonusGems = selectedPlan?.dailyBonusGems ?: "500"
                            val dailyBonusInt = bonusGems.toIntOrNull() ?: 500
                            val (totalGems, formatRes) = if (isAnnual) {
                                (dailyBonusInt * 365) to R.string.sub_price_year_format
                            } else {
                                (dailyBonusInt * 30) to R.string.sub_price_month_format
                            }
                            val totalGemsFormatted = "%,d".format(java.util.Locale.US, totalGems)

                            Text(
                                text = stringResource(id = formatRes, totalGemsFormatted),
                                color = ColorE8C3AC,
                                fontFamily = OutfitBold,
                                fontSize = SdpR_13.nonScaledSp
                            )
                            Spacer(modifier = Modifier.width(SdpR_3))

                            Text(
                                text = stringResource(id = R.string.sub_bonus_gems_format, bonusGems),
                                color = ColorAFA5C3,
                                fontFamily = OutfitSemiBold,
                                fontSize = SdpR_13.nonScaledSp
                            )
                        }

                        Spacer(modifier = Modifier.height(SdpR_12))
                    }

                    SubscriptionFeatureItem(
                        iconRes = R.drawable.ic_sparkles,
                        textRes = R.string.sub_feature_outfits
                    )
                    SubscriptionFeatureItem(
                        iconRes = R.drawable.ic_thunder,
                        textRes = R.string.sub_feature_action
                    )
                    SubscriptionFeatureItem(
                        iconRes = R.drawable.ic_palette,
                        textRes = R.string.sub_feature_personality
                    )
                    SubscriptionFeatureItem(
                        iconRes = R.drawable.ic_eye,
                        textRes = R.string.sub_feature_ad_free
                    )
                    SubscriptionFeatureItem(
                        iconRes = R.drawable.ic_star,
                        textRes = R.string.sub_feature_support
                    )

                    Spacer(modifier = Modifier.height(SdpR_12))

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
                                .background(
                                    color = Color(0x33161127),
                                    shape = RoundedCornerShape(SdpR_16)
                                )
                                .border(
                                    width = SdpR_1,
                                    color = Color271E38,
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
                            Text(
                                text = stringResource(id = R.string.sub_empty_plans),
                                color = ColorAFA5C3,
                                fontFamily = OutfitRegular,
                                fontSize = SdpR_13.nonScaledSp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(SdpR_16))
                            AppButton(
                                text = stringResource(id = R.string.store_empty_retry),
                                gradient = AppTextHorizontalGradient,
                                shape = RoundedCornerShape(SdpR_20),
                                minHeight = SdpR_36,
                                modifier = Modifier.padding(horizontal = SdpR_24),
                                onClick = onRetry
                            )
                        }
                    } else {
                        monthlyPlan?.let {
                            SubscriptionPlanCard(
                                plan = it,
                                isSelected = state.selectedPlanId == it.id,
                                onClick = { onPlanSelect(it.id) }
                            )
                            Spacer(modifier = Modifier.height(SdpR_12))
                        }

                        yearlyPlan?.let {
                            SubscriptionPlanCard(
                                plan = it,
                                isSelected = state.selectedPlanId == it.id,
                                onClick = { onPlanSelect(it.id) }
                            )
                        }
                    }

                    if (state.plans.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(SdpR_16))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(SdpR_48)
                                .clip(RoundedCornerShape(SdpR_24))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(ColorD25A9F, ColorBE5AD2)
                                    )
                                )
                                .clickable { onSubscribeClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.sub_subscribe_now),
                                color = ColorFDFDFD,
                                fontFamily = OutfitExtraBold,
                                fontSize = SdpR_13.nonScaledSp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(SdpR_24))

                    SubscriptionFinePrintText(textRes = R.string.sub_fine_print_1)
                    SubscriptionFinePrintText(textRes = R.string.sub_fine_print_2)
                    SubscriptionFinePrintText(textRes = R.string.sub_fine_print_3)
                    SubscriptionFinePrintText(
                        textRes = R.string.sub_fine_print_4,
                        onClick = {
                            try {
                                uriHandler.openUri("https://play.google.com/store/account/subscriptions?package=com.pegas.yuki.virtual.chat")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(SdpR_24))

                    Text(
                        text = stringResource(id = R.string.sub_restore_purchase),
                        color = ColorE8C3AC,
                        fontFamily = OutfitSemiBold,
                        fontSize = SdpR_13.nonScaledSp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { onRestoreClick() }
                    )

                    Spacer(modifier = Modifier.height(SdpR_12))

                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.sub_terms_of_use),
                            color = ColorAFA5C3,
                            fontFamily = OutfitSemiBold,
                            fontSize = SdpR_13.nonScaledSp,
                            modifier = Modifier.clickable {
                                try {
                                    uriHandler.openUri(AppConstants.LINK_PRIVACY_POLICY)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(SdpR_8))
                        Text(
                            text = "•",
                            color = ColorAFA5C3,
                            fontSize = SdpR_10.nonScaledSp
                        )
                        Spacer(modifier = Modifier.width(SdpR_8))
                        Text(
                            text = stringResource(id = R.string.sub_privacy_policy),
                            color = ColorAFA5C3,
                            fontFamily = OutfitSemiBold,
                            fontSize = SdpR_13.nonScaledSp,
                            modifier = Modifier.clickable {
                                try {
                                    uriHandler.openUri(AppConstants.LINK_PRIVACY_POLICY)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(SdpR_40))
                }
            }
        }
    }

}

@androidx.compose.ui.tooling.preview.Preview(showSystemUi = true)
@Composable
fun SubscriptionScreenPreview() {
    SubscriptionScreenContent(
        state = SubscriptionUiState(
            plans = listOf(
                SubscriptionPlanUiModel("1", "monthly", false, "263,000 đ/month", "500"),
                SubscriptionPlanUiModel("2", "yearly", true, "1,300,000 đ/year", "750")
            ),
            selectedPlanId = "2"
        ),
        onBackClick = {},
        onPlanSelect = {},
        onSubscribeClick = {},
        onRestoreClick = {}
    )
}