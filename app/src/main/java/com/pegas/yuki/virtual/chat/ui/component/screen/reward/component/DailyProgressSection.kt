package com.pegas.yuki.virtual.chat.ui.component.screen.reward.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppButton
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppText
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppTextHorizontalGradient
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color000000
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeExtraBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_100
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_11
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_22
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_48
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@Composable
fun DailyProgressSection(
    currentDayIndex: Int,
    claimedToday: Boolean,
    rewards: List<Int>,
    onCheckInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.reward_your_progress),
            fontFamily = ManropeBold,
            fontSize = SdpR_14.nonScaledSp,
            color = Color000000,
            modifier = Modifier.padding(bottom = SdpR_12)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SdpR_8)
        ) {
            for (day in 1..4) {
                val isCurrent = (day == currentDayIndex)
                val isClaimed = (day < currentDayIndex) || (day == currentDayIndex && claimedToday)
                val amount = rewards.getOrNull(day - 1) ?: 0

                DayCardItem(
                    dayNumber = day,
                    amount = amount,
                    isCurrent = isCurrent,
                    isClaimed = isClaimed,
                    multiplier = null,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(SdpR_8))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SdpR_8)
        ) {
            val multipliers = listOf("x3", "x5", "x7")
            for (day in 5..7) {
                val isCurrent = (day == currentDayIndex)
                val isClaimed = (day < currentDayIndex) || (day == currentDayIndex && claimedToday)
                val amount = rewards.getOrNull(day - 1) ?: 0

                DayCardItem(
                    dayNumber = day,
                    amount = amount,
                    isCurrent = isCurrent,
                    isClaimed = isClaimed,
                    multiplier = multipliers.getOrNull(day - 5),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(SdpR_16))

        AppButton(
            onClick = onCheckInClick,
            text = if (claimedToday) {
                stringResource(R.string.reward_checked_in_today)
            } else {
                stringResource(R.string.reward_check_in_today)
            },
            enabled = !claimedToday,
            disabledColor = Color(0xFFE5E5EA),
            disabledTextColor = Color(0xFF8E8E93),
            textColor = ColorFFFFFF,
            shape = RoundedCornerShape(SdpR_100),
            minHeight = SdpR_48,
            textStyle = TextStyle(
                fontFamily = ManropeBold,
                fontWeight = FontWeight.Bold,
                fontSize = SdpR_16.nonScaledSp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DayCardItem(
    dayNumber: Int,
    amount: Int,
    isCurrent: Boolean,
    isClaimed: Boolean,
    multiplier: String?,
    modifier: Modifier = Modifier
) {
    val currentBorderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF8040),
            Color(0xFFFF41BC),
            Color(0xFFDC60FF)
        )
    )

    val borderModifier = if (isCurrent && !isClaimed) {
        Modifier.border(
            width = SdpR_2,
            brush = currentBorderBrush,
            shape = RoundedCornerShape(SdpR_16)
        )
    } else {
        Modifier.border(
            width = SdpR_1,
            color = Color(0xFFEDE9F2),
            shape = RoundedCornerShape(SdpR_16)
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(SdpR_16))
            .background(ColorFFFFFF)
            .then(borderModifier)
            .padding(vertical = SdpR_10, horizontal = SdpR_4)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_4),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (multiplier == null) {
                AppText(
                    text = stringResource(R.string.reward_day_format, dayNumber),
                    fontFamily = OutfitBold,
                    fontSize = SdpR_11.nonScaledSp,
                    gradient = if (isCurrent && !isClaimed) AppTextHorizontalGradient else null,
                    color = if (isClaimed) Color(0xFFAFA5C3) else Color(0xFF6B5E80)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppText(
                        text = stringResource(R.string.reward_day_format, dayNumber),
                        fontFamily = OutfitBold,
                        fontSize = SdpR_11.nonScaledSp,
                        gradient = if (isCurrent && !isClaimed) AppTextHorizontalGradient else null,
                        color = if (isClaimed) Color(0xFFAFA5C3) else Color(0xFF6B5E80)
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(SdpR_4))
                            .background(Color(0xFFE91E8C))
                            .padding(
                                horizontal = SdpR_4,
                                vertical = SdpR_1
                            )
                    ) {
                        Text(
                            text = multiplier,
                            fontFamily = OutfitExtraBold,
                            fontSize = SdpR_8.nonScaledSp,
                            color = ColorFFFFFF
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(SdpR_6))

            if (multiplier != null) {
                Image(
                    painter = painterResource(R.drawable.ic_award),
                    contentDescription = null,
                    colorFilter = if (isClaimed) ColorFilter.tint(Color(0xFFB4B0BE)) else null,
                    modifier = Modifier.size(SdpR_22)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_diamond),
                    contentDescription = null,
                    tint = if (isClaimed) Color(0xFFB4B0BE) else Color(0xFFFFB03A),
                    modifier = Modifier.size(SdpR_20)
                )
            }

            Spacer(modifier = Modifier.height(SdpR_6))

            Text(
                text = "+$amount",
                fontFamily = ManropeExtraBold,
                fontSize = SdpR_14.nonScaledSp,
                color = if (isClaimed) Color(0xFFB4B0BE) else Color000000
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DailyProgressSectionPreview() {
    DailyProgressSection(
        currentDayIndex = 3,
        claimedToday = false,
        rewards = listOf(10, 15, 20, 25, 30, 40, 100),
        onCheckInClick = {}
    )
}
