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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.AppText
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.AppTextHorizontalGradient
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

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
    val borderBrush = if (isSelected) {
        Brush.horizontalGradient(
            listOf(
                Color(0xFFE040FB),
                Color(0xFFFF3377),
                Color(0xFFFF7A45)
            )
        )
    } else {
        SolidColor(Color(0xFFEAE6F2))
    }
    val borderWidth = if (isSelected) 1.5.dp else 1.dp
    val bgColor = if (isSelected) Color(0xFFFFF5F8) else Color.White
    val textGradient = if (isSelected) AppTextHorizontalGradient else null
    val textColor = if (isSelected) Color.Unspecified else Color(0xFF1E1B24)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SdpR_16))
            .background(bgColor)
            .border(
                width = borderWidth,
                brush = borderBrush,
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
                AppText(
                    text = title,
                    fontFamily = if (isSelected) ManropeExtraBold else ManropeBold,
                    fontSize = SdpR_16.nonScaledSp,
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                    color = textColor,
                    gradient = textGradient
                )

                if (isBestValue) {
                    Spacer(modifier = Modifier.width(SdpR_8))
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(Color(0xFFFF3377), Color(0xFFFF7043))
                                ),
                                shape = RoundedCornerShape(SdpR_6)
                            )
                            .padding(horizontal = SdpR_6, vertical = SdpR_4)
                    ) {
                        Text(
                            text = stringResource(id = R.string.store_best_value),
                            fontFamily = OutfitExtraBold,
                            fontSize = SdpR_7.nonScaledSp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                AppText(
                    text = price,
                    fontFamily = ManropeExtraBold,
                    fontSize = SdpR_20.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    gradient = textGradient
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
                    color = Color(0xFFA197B9),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = durationLabelRes),
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_11.nonScaledSp,
                    color = Color(0xE5000000)
                )
            }

            Spacer(modifier = Modifier.height(SdpR_8))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFF2EDF7))
            )

            Spacer(modifier = Modifier.height(SdpR_12))

            benefitsRes.forEach { benefitRes ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = SdpR_8)
                ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_vip),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(SdpR_18)
                        )

                    Spacer(modifier = Modifier.width(SdpR_8))
                    Text(
                        text = stringResource(id = benefitRes),
                        fontFamily = ManropeRegular,
                        fontSize = SdpR_13.nonScaledSp,
                        color = Color(0xFF2D2938)
                    )
                }
            }

            Spacer(modifier = Modifier.height(SdpR_2))

            Text(
                text = stringResource(id = footerTextRes),
                fontFamily = ManropeRegular,
                fontSize = SdpR_10.nonScaledSp,
                color = Color(0xFFA197B9)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
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
        isSelected = false,
        onClick = {}
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
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
        isSelected = true,
        onClick = {}
    )
}
