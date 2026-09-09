package com.pegas.aura.aigirlfriend.soul.ui.component.screen.reward.component

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color1B1227
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color6B5E80
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorF1CBB7
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_20
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_22
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_26
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_48
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
fun DailyProgressSection(
    currentDayIndex: Int,
    claimedToday: Boolean,
    rewards: List<Int>,
    onCheckInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.reward_your_progress),
            fontFamily = OutfitBold,
            fontSize = SdpR_14.nonScaledSp,
            color = ColorFFFFFF,
            modifier = Modifier.padding(bottom = SdpR_8)
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

        val buttonBackground = if (claimedToday) Color322D41 else ColorF1CBB7
        val buttonTextColor = if (claimedToday) ColorAFA5C3 else Color150F25

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(SdpR_48)
                .clip(RoundedCornerShape(SdpR_26))
                .background(buttonBackground)
                .clickable(enabled = !claimedToday) { onCheckInClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (claimedToday) stringResource(R.string.reward_checked_in_today) else stringResource(
                    R.string.reward_check_in_today
                ),
                fontFamily = OutfitBold,
                fontSize = SdpR_16.nonScaledSp,
                color = buttonTextColor
            )
        }
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
    val borderColor = if (isCurrent) ColorF1CBB7 else Color322D41
    val cardBackground = if (isCurrent) Color1B1227 else Color150F25

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(SdpR_16))
            .background(cardBackground)
            .border(
                width = if (isCurrent) SdpR_2 else SdpR_1,
                color = borderColor,
                shape = RoundedCornerShape(SdpR_16)
            )
            .padding(vertical = SdpR_8, horizontal = SdpR_4)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_6),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (multiplier == null) {
                Text(
                    text = stringResource(R.string.reward_day_format, dayNumber),
                    fontFamily = OutfitBold,
                    fontSize = SdpR_11.nonScaledSp,
                    color = if (isCurrent) ColorE8C3AC else ColorAFA5C3
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.reward_day_format, dayNumber),
                        fontFamily = OutfitBold,
                        fontSize = SdpR_11.nonScaledSp,
                        color = if (isCurrent) ColorE8C3AC else ColorAFA5C3
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(SdpR_4))
                            .background(ColorD65A98)
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

            Spacer(modifier = Modifier.height(SdpR_2))

            if (multiplier != null) {
                Image(
                    painter = painterResource(R.drawable.ic_award),
                    contentDescription = null,
                    modifier = Modifier.size(SdpR_22)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_diamond),
                    contentDescription = null,
                    tint = if (isCurrent) ColorF1CBB7 else Color6B5E80,
                    modifier = Modifier.size(SdpR_20)
                )
            }

            Spacer(modifier = Modifier.height(SdpR_2))

            Text(
                text = "+$amount",
                fontFamily = OutfitExtraBold,
                fontSize = SdpR_14.nonScaledSp,
                color = if (isClaimed) Color6B5E80 else ColorFDFDFD
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun DailyProgressSectionPreview() {
    DailyProgressSection(
        currentDayIndex = 3,
        claimedToday = false,
        rewards = listOf(10, 15, 20, 25, 30, 40, 100),
        onCheckInClick = {}
    )
}
