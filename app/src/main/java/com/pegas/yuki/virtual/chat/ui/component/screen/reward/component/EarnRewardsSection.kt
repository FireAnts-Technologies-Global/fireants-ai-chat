package com.pegas.yuki.virtual.chat.ui.component.screen.reward.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color000000
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color150F25
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD4A24C
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE0D5F0
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorF0EBF8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFB03A
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_13
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_36
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@Composable
fun EarnRewardsSection(
    adsWatchedToday: Int,
    adsRemainingToday: Int,
    coinsPerView: Int,
    adsConfigLoaded: Boolean,
    isWatchingAd: Boolean,
    isVip: Boolean = false,
    onWatchAdClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SdpR_16)
    ) {
        Text(
            text = stringResource(R.string.reward_earn_rewards),
            fontFamily = ManropeBold,
            fontSize = SdpR_14.nonScaledSp,
            color = Color000000,
            modifier = Modifier.padding(bottom = SdpR_12)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(SdpR_16))
                .background(ColorFFFFFF)
                .border(SdpR_1, Color(0xFFEDE9F2), RoundedCornerShape(SdpR_16))
                .padding(vertical = SdpR_12, horizontal = SdpR_12)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_video),
                    contentDescription = null,
                    modifier = Modifier.size(SdpR_36)
                )

                Spacer(modifier = Modifier.width(SdpR_12))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.reward_daily_ads),
                        modifier = Modifier.basicMarquee(),
                        fontFamily = ManropeBold,
                        fontSize = SdpR_13.nonScaledSp,
                        lineHeight = SdpR_16.nonScaledSp,
                        color = Color000000,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Clip
                    )
                    Spacer(modifier = Modifier.height(SdpR_4))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isVip) {
                            Image(
                                painter = painterResource(id = R.drawable.img_coin),
                                contentDescription = null,
                                modifier = Modifier.size(SdpR_16)
                            )

                            Spacer(modifier = Modifier.width(SdpR_4))
                        }

                        Text(
                            text = if (isVip) {
                                stringResource(R.string.reward_vip_unlocked_desc)
                            } else {
                                stringResource(
                                    R.string.reward_daily_ads_desc,
                                    coinsPerView
                                )
                            },
                            modifier = Modifier.basicMarquee(),
                            fontFamily = ManropeRegular,
                            fontSize = SdpR_12.nonScaledSp,
                            lineHeight = SdpR_14.nonScaledSp,
                            color = Color(0xFFB440F2),
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Clip
                        )
                    }
                }

                Spacer(modifier = Modifier.width(SdpR_8))

                val isLimitReached = adsRemainingToday <= 0
                val buttonText = if (isVip) {
                    stringResource(R.string.reward_vip_unlocked)
                } else if (isWatchingAd) {
                    stringResource(R.string.loading_label)
                } else if (!adsConfigLoaded) {
                    stringResource(R.string.reward_ads_unavailable)
                } else {
                    stringResource(R.string.reward_watch_ads_format, adsWatchedToday)
                }

                val buttonShape = RoundedCornerShape(SdpR_12)

                val buttonModifier = if (isVip) {
                    Modifier
                        .clip(buttonShape)
                        .background(
                            Brush.horizontalGradient(
                                listOf(ColorD4A24C, ColorFFB03A)
                            )
                        )
                        .padding(horizontal = SdpR_14, vertical = SdpR_8)
                } else {
                    Modifier
                        .clip(buttonShape)
                        .background(
                            if (isLimitReached || !adsConfigLoaded) {
                                ColorF0EBF8
                            } else {
                                ColorF0EBF8
                            }
                        )
                        .border(
                            width = SdpR_1,
                            color = if (isLimitReached || !adsConfigLoaded) {
                                ColorE0D5F0
                            } else {
                                ColorE0D5F0
                            },
                            shape = buttonShape
                        )
                        .clickable(
                            enabled = adsConfigLoaded && !isWatchingAd && !isLimitReached
                        ) {
                            onWatchAdClick()
                        }
                        .padding(horizontal = SdpR_14, vertical = SdpR_8)
                }

                Box(
                    modifier = buttonModifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
                        fontFamily = ManropeBold,
                        fontSize = SdpR_12.nonScaledSp,
                        color = if (isVip) {
                            Color150F25
                        } else if (isLimitReached || !adsConfigLoaded) {
                            ColorAFA5C3
                        } else {
                            Color(0xFF7551B5)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun EarnRewardsSectionPreview() {
    EarnRewardsSection(
        adsWatchedToday = 0,
        adsRemainingToday = 10,
        coinsPerView = 15,
        adsConfigLoaded = true,
        isWatchingAd = false,
        onWatchAdClick = {}
    )
}
