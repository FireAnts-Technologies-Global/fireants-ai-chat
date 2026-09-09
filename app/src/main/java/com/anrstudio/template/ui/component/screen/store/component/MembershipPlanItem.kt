package com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color0AFFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color0FD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color17FFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color21D65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color21E8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorA197B9
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE5D9D9D9
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_10
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_18
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_7
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
fun MembershipPlanItem(
    title: String,
    price: String,
    durationLabelRes: Int,
    descriptionRes: Int,
    benefitsRes: List<Int>,
    footerTextRes: Int,
    isBestValue: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val borderColor = if (isSelected) ColorD65A98 else Color17FFFFFF
    val titleColor = if (isSelected) ColorD65A98 else ColorFDFDFD
    val priceColor = if (isSelected) ColorD65A98 else ColorFDFDFD
    val checkIconTint = if (isSelected) ColorD65A98 else ColorE8C3AC
    val checkBgColor = if (isSelected) Color21D65A98 else Color21E8C3AC
    val borderWidth = if (isSelected) SdpR_2 else SdpR_1
    val bgColor = if (isSelected) Color0FD65A98 else Color0AFFFFFF
    val fontFamily = if (isSelected) OutfitExtraBold else OutfitBold
    val fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold

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
            .padding(SdpR_12)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontFamily = fontFamily,
                    fontSize = SdpR_16.nonScaledSp,
                    fontWeight = fontWeight,
                    color = titleColor
                )

                if (isBestValue) {
                    Spacer(modifier = Modifier.width(SdpR_8))
                    Box(
                        modifier = Modifier
                            .background(
                                color = ColorD65A98,
                                shape = RoundedCornerShape(SdpR_6)
                            )
                            .padding(horizontal = SdpR_6, vertical = SdpR_4)
                    ) {
                        Text(
                            text = stringResource(id = R.string.store_best_value),
                            fontFamily = OutfitExtraBold,
                            fontSize = SdpR_7.nonScaledSp,
                            fontWeight = FontWeight.Bold,
                            color = ColorFDFDFD
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = price,
                    fontFamily = OutfitExtraBold,
                    fontSize = SdpR_18.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    color = priceColor
                )
            }

            Spacer(modifier = Modifier.height(SdpR_4))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = descriptionRes),
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_12.nonScaledSp,
                    color = ColorA197B9,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = durationLabelRes),
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_11.nonScaledSp,
                    color = ColorE5D9D9D9
                )
            }

            Spacer(modifier = Modifier.height(SdpR_16))

            benefitsRes.forEach { benefitRes ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = SdpR_8)
                ) {
                    Box(
                        modifier = Modifier
                            .size(SdpR_18)
                            .background(
                                color = checkBgColor,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            tint = checkIconTint,
                            modifier = Modifier.size(SdpR_10)
                        )
                    }
                    Spacer(modifier = Modifier.width(SdpR_8))
                    Text(
                        text = stringResource(id = benefitRes),
                        fontFamily = ManropeRegular,
                        fontSize = SdpR_13.nonScaledSp,
                        color = ColorE5D9D9D9
                    )
                }
            }

            Spacer(modifier = Modifier.height(SdpR_2))

            Text(
                text = stringResource(id = footerTextRes),
                fontFamily = ManropeRegular,
                fontSize = SdpR_10.nonScaledSp,
                color = ColorA197B9
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 400
)
@Composable
private fun MembershipPlanMonthlyPreview() {
    MembershipPlanItem(
        title = "Monthly Plan",
        price = "263,000 đ",
        durationLabelRes = R.string.store_duration_month,
        descriptionRes = R.string.store_vip_desc_month,
        benefitsRes = listOf(
            R.string.store_vip_benefit_1
        ),
        footerTextRes = R.string.store_vip_footer_month,
        isBestValue = false,
        onClick = {}
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 400
)
@Composable
private fun MembershipPlanAnnualPreview() {
    MembershipPlanItem(
        title = "Annual Plan",
        price = "1,300,000 đ",
        durationLabelRes = R.string.store_duration_year,
        descriptionRes = R.string.store_vip_desc_year,
        benefitsRes = listOf(
            R.string.store_vip_benefit_2,
            R.string.store_vip_benefit_3
        ),
        footerTextRes = R.string.store_vip_footer_year,
        isBestValue = true,
        onClick = {}
    )
}
