package com.pegas.yuki.virtual.chat.ui.component.screen.mission.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppText
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppTextHorizontalGradient
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_36
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.screen.mission.MissionHistoryItemUiState

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
            .background(ColorFFFFFF)
            .border(
                width = SdpR_1,
                color = Color(0xFFEDE9F2),
                shape = RoundedCornerShape(SdpR_16)
            )
            .padding(horizontal = SdpR_14, vertical = SdpR_12)
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
                        fontFamily = ManropeBold,
                        fontSize = SdpR_14.nonScaledSp,
                        lineHeight = SdpR_16.nonScaledSp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    color = Color(0xFF161022)
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
                            fontFamily = ManropeBold,
                            fontSize = SdpR_12.nonScaledSp,
                            lineHeight = SdpR_14.nonScaledSp,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        color = Color(0xFFE02469)
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
                AppText(
                    text = timeAgoText,
                    fontFamily = ManropeSemiBold,
                    fontSize = SdpR_12.nonScaledSp,
                    lineHeight = SdpR_14.nonScaledSp,
                    gradient = AppTextHorizontalGradient
                )
                Spacer(modifier = Modifier.height(SdpR_4))

                Text(
                    text = item.exactTime,
                    style = TextStyle(
                        fontFamily = ManropeBold,
                        fontSize = SdpR_10.nonScaledSp,
                        lineHeight = SdpR_12.nonScaledSp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    color = Color(0xFF6F6794)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun MissionHistoryItemCardPreview() {
    MissionHistoryItemCard(
        item = MissionHistoryItemUiState(
            id = "1",
            titleRes = R.string.mission_type_play_game,
            titleRaw = null,
            gemsEarned = 15,
            timeAgo = "Today",
            exactTime = "Jan 26, 14:32"
        )
    )
}