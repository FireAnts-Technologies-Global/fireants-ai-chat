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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color1A4CAF50
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color1AD65A98
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color1B1227
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color271E38
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD65A98
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorF1CBB7
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_15
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_18
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_9
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.screen.subscription.SubscriptionPlanUiModel

@Composable
fun SubscriptionPlanCard(
    plan: SubscriptionPlanUiModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val activeColor = ColorF1CBB7
    val borderColor = when {
        plan.isActivePlan -> activeColor
        isSelected -> ColorD65A98
        else -> Color271E38
    }
    val bgColor = when {
        plan.isActivePlan -> Color1A4CAF50
        isSelected -> Color1AD65A98
        else -> Color1B1227
    }
    val textColor = when {
        plan.isActivePlan -> activeColor
        isSelected -> ColorD65A98
        else -> ColorFDFDFD
    }
    val titleRes = if (plan.isAnnual) R.string.sub_plan_yearly else R.string.sub_plan_monthly
    val descRes =
        if (plan.isAnnual) R.string.sub_plan_yearly_desc else R.string.sub_plan_monthly_desc

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SdpR_18))
            .background(bgColor)
            .border(
                width = SdpR_1,
                color = borderColor,
                shape = RoundedCornerShape(SdpR_18)
            )
            .clickable { onClick() }
            .padding(SdpR_12)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = titleRes),
                        color = textColor,
                        fontFamily = OutfitBold,
                        fontSize = SdpR_16.nonScaledSp
                    )

                    if (plan.isActivePlan) {
                        Spacer(modifier = Modifier.width(SdpR_8))
                        Box(
                            modifier = Modifier
                                .background(ColorD65A98, RoundedCornerShape(SdpR_4))
                                .padding(horizontal = SdpR_6, vertical = 2.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.sub_current_plan),
                                color = ColorFDFDFD,
                                fontFamily = OutfitExtraBold,
                                fontSize = SdpR_9.nonScaledSp
                            )
                        }
                    } else if (plan.isAnnual) {
                        Spacer(modifier = Modifier.width(SdpR_8))
                        Box(
                            modifier = Modifier
                                .background(ColorD65A98, RoundedCornerShape(SdpR_4))
                                .padding(horizontal = SdpR_6, vertical = 2.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.store_best_value),
                                color = ColorFDFDFD,
                                fontFamily = OutfitExtraBold,
                                fontSize = SdpR_9.nonScaledSp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(SdpR_2))

                Text(
                    text = stringResource(id = descRes),
                    color = ColorAFA5C3,
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_12.nonScaledSp
                )
            }

            Text(
                text = plan.priceText,
                color = textColor,
                fontFamily = OutfitBold,
                fontSize = SdpR_15.nonScaledSp
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun SubscriptionPlanCardPreview() {
    Column {
        SubscriptionPlanCard(
            plan = SubscriptionPlanUiModel("1", "monthly", false, "263,000 đ/month", "500"),
            isSelected = false,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        SubscriptionPlanCard(
            plan = SubscriptionPlanUiModel("2", "yearly", true, "1,300,000 đ/year", "750"),
            isSelected = true,
            onClick = {}
        )
    }
}
