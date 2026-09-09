package com.pegas.aura.aigirlfriend.soul.ui.component.screen.mission.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color6B5E80
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorF1CBB7
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeSemiBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_10
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_15
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_36
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.mission.MissionHistoryItemUiState

@Composable
fun MissionHistoryItemCard(
    item: MissionHistoryItemUiState,
    modifier: Modifier = Modifier
) {
    val titleText = if (item.titleRes != null) {
        stringResource(item.titleRes)
    } else {
        item.titleRaw ?: ""
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SdpR_16))
            .background(Color150F25)
            .border(
                width = SdpR_1,
                color = Color322D41,
                shape = RoundedCornerShape(SdpR_16)
            )
            .padding(SdpR_10)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_done_mission),
                contentDescription = null,
                modifier = Modifier.size(SdpR_36)
            )

            Spacer(modifier = Modifier.width(SdpR_12))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titleText,
                    style = TextStyle(
                        fontFamily = OutfitBold,
                        fontSize = SdpR_14.nonScaledSp,
                        lineHeight = SdpR_15.nonScaledSp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    color = ColorFFFFFF
                )
                Spacer(modifier = Modifier.height(SdpR_4))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_coin),
                        contentDescription = null,
                        modifier = Modifier.size(SdpR_16)
                    )

                    Spacer(modifier = Modifier.width(SdpR_4))

                    Text(
                        text = stringResource(
                            R.string.mission_coins_earned_format,
                            item.gemsEarned
                        ),
                        style = TextStyle(
                            fontFamily = ManropeRegular,
                            fontSize = SdpR_12.nonScaledSp,
                            lineHeight = SdpR_13.nonScaledSp,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        color = ColorF1CBB7
                    )
                }
            }

            Spacer(modifier = Modifier.width(SdpR_8))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                val timeAgoText = when (item.timeAgo) {
                    "Today" -> stringResource(R.string.today)
                    "Yesterday" -> stringResource(R.string.yesterday)
                    else -> item.timeAgo
                }
                Text(
                    text = timeAgoText,
                    style = TextStyle(
                        fontFamily = ManropeSemiBold,
                        fontSize = SdpR_12.nonScaledSp,
                        lineHeight = SdpR_13.nonScaledSp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    color = ColorAFA5C3
                )
                Spacer(modifier = Modifier.height(SdpR_4))

                Text(
                    text = item.exactTime,
                    style = TextStyle(
                        fontFamily = ManropeRegular,
                        fontSize = SdpR_10.nonScaledSp,
                        lineHeight = SdpR_11.nonScaledSp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    color = Color6B5E80
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F
)
@Composable
private fun MissionHistoryItemCardPreview() {
    MissionHistoryItemCard(
        item = MissionHistoryItemUiState(
            id = "1",
            titleRes = R.string.mission_type_play_game,
            gemsEarned = 15,
            timeAgo = "Today",
            exactTime = "Jan 26, 14:32"
        )
    )
}