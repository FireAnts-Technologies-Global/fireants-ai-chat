package com.pegas.aura.aigirlfriend.soul.ui.component.screen.reward.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color271E38
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color363144
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD4A24C
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFFB03A
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_10
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_32
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

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
            color = ColorFFFFFF,
            modifier = Modifier.padding(bottom = SdpR_8)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(SdpR_16))
                .background(Color150F25)
                .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_16))
                .padding(vertical = SdpR_8, horizontal = SdpR_10)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_video),
                    contentDescription = null,
                    modifier = Modifier.size(SdpR_32)
                )

                Spacer(modifier = Modifier.width(SdpR_12))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.reward_daily_ads),
                        modifier = Modifier.basicMarquee(),
                        fontFamily = OutfitBold,
                        fontSize = SdpR_13.nonScaledSp,
                        lineHeight = SdpR_14.nonScaledSp,
                        color = ColorFFFFFF,
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
                            fontSize = SdpR_11.nonScaledSp,
                            lineHeight = SdpR_12.nonScaledSp,
                            color = ColorE8C3AC,
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

                val buttonModifier = if (isVip) {
                    Modifier
                        .clip(RoundedCornerShape(SdpR_12))
                        .background(Brush.horizontalGradient(listOf(ColorD4A24C, ColorFFB03A)))
                        .padding(horizontal = SdpR_12, vertical = SdpR_8)
                } else {
                    Modifier
                        .clip(RoundedCornerShape(SdpR_12))
                        .background(if (isLimitReached || !adsConfigLoaded) Color322D41 else Color271E38)
                        .border(SdpR_1, Color363144, RoundedCornerShape(SdpR_12))
                        .clickable(enabled = adsConfigLoaded && !isWatchingAd && !isLimitReached) { onWatchAdClick() }
                        .padding(horizontal = SdpR_12, vertical = SdpR_8)
                }

                Box(
                    modifier = buttonModifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
                        fontFamily = OutfitBold,
                        fontSize = SdpR_12.nonScaledSp,
                        color = if (isVip) Color150F25 else if (isLimitReached || !adsConfigLoaded) ColorAFA5C3 else ColorFFFFFF
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
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
