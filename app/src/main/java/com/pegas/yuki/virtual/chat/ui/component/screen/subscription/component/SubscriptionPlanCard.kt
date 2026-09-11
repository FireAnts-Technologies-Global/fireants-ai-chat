package com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppText
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_11
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_18
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_22
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_31
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_9
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.screen.subscription.SubscriptionPlanUiModel

private val PlanCardPinkColor = Color(0xFFFF41BC)

private val PlanCardPinkGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFDC60FF),
        Color(0xFFFF41BC),
        Color(0xFFFF8040)
    )
)

private val PlanCardBorderUnselected = Color(0xFFDED3EB)
private val PlanCardRadioUnselected = Color(0xFF8D809F)
private val PlanCardTitleDark = Color(0xFF1E142F)

@Composable
fun SubscriptionPlanCard(
    plan: SubscriptionPlanUiModel,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(SdpR_31)

    val titleRes = if (plan.isAnnual) {
        R.string.sub_plan_yearly
    } else {
        R.string.sub_plan_monthly
    }
    val cardTitle = plan.title?.takeIf { it.isNotBlank() } ?: stringResource(id = titleRes)

    val displayPrice = if (plan.priceText.contains("/")) {
        plan.priceText
    } else {
        stringResource(
            id = if (plan.isAnnual) {
                R.string.sub_price_year_format
            } else {
                R.string.sub_price_month_format
            },
            plan.priceText
        )
    }

    val cardBorderModifier = if (isSelected) {
        Modifier.border(
            width = SdpR_1,
            brush = PlanCardPinkGradient,
            shape = shape
        )
    } else {
        Modifier.border(
            width = SdpR_1,
            color = PlanCardBorderUnselected,
            shape = shape
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = SdpR_12,
                shape = shape,
                clip = false
            )
            .clip(shape)
            .background(Color.White.copy(alpha = 0.95f))
            .then(cardBorderModifier)
            .clickable { onClick() }
            .padding(
                horizontal = SdpR_16,
                vertical = SdpR_16
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlanRadioButton(
                isSelected = isSelected
            )

            Spacer(
                modifier = Modifier.width(SdpR_12)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                AppText(
                    text = cardTitle,
                    color = PlanCardTitleDark,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_14.nonScaledSp
                )

                Spacer(
                    modifier = Modifier.height(SdpR_2)
                )

                AppText(
                    text = displayPrice,
                    color = PlanCardPinkColor,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    gradient = PlanCardPinkGradient,
                    fontSize = SdpR_18.nonScaledSp
                )
            }

            when {
                plan.isActivePlan -> {
                    CurrentPlanBadge()
                }

                plan.isAnnual -> {
                    AnnualPlanInfo(
                        savePercentageText = plan.savePercentageText
                    )
                }
            }
        }
    }
}

@Composable
private fun PlanRadioButton(
    isSelected: Boolean
) {
    val borderModifier = if (isSelected) {
        Modifier.border(
            width = SdpR_2,
            brush = PlanCardPinkGradient,
            shape = CircleShape
        )
    } else {
        Modifier.border(
            width = SdpR_2,
            color = PlanCardRadioUnselected,
            shape = CircleShape
        )
    }

    Box(
        modifier = Modifier
            .size(SdpR_22)
            .then(borderModifier),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(SdpR_11)
                    .background(
                        brush = PlanCardPinkGradient,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun CurrentPlanBadge() {
    Box(
        modifier = Modifier
            .background(
                brush = PlanCardPinkGradient,
                shape = RoundedCornerShape(SdpR_8)
            )
            .padding(
                horizontal = SdpR_8,
                vertical = SdpR_2
            )
    ) {
        AppText(
            text = stringResource(id = R.string.sub_current_plan),
            color = Color.White,
            fontFamily = OutfitBold,
            fontSize = SdpR_9.nonScaledSp
        )
    }
}

@Composable
private fun AnnualPlanInfo(
    savePercentageText: String?
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        if (!savePercentageText.isNullOrBlank()) {
            AppText(
                text = savePercentageText,
                color = Color(0xFFED39CF),
                fontFamily = ManropeRegular,
                fontWeight = FontWeight.Bold,
                fontSize = SdpR_12.nonScaledSp
            )

            Spacer(
                modifier = Modifier.height(SdpR_2)
            )
        }

        AppText(
            text = stringResource(id = R.string.sub_best_value_arrow),
            color = Color(0xFFED39CF),
            fontFamily = ManropeRegular,
            fontSize = SdpR_12.nonScaledSp
        )
    }
}
