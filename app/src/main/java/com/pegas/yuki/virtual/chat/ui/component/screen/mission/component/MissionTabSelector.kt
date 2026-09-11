package com.pegas.yuki.virtual.chat.ui.component.screen.mission.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.AppButtonVerticalGradient
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_13
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_48
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.screen.mission.MissionTab

@Composable
fun MissionTabSelector(
    selectedTab: MissionTab,
    onTabSelected: (MissionTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(SdpR_48)
            .clip(RoundedCornerShape(SdpR_14))
            .background(ColorFFFFFF)
            .border(SdpR_1, Color(0xFFEDE9F2), RoundedCornerShape(SdpR_14))
            .padding(SdpR_4)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            val isMissionSelected = selectedTab == MissionTab.MISSION
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(SdpR_12))
                    .then(
                        if (isMissionSelected) {
                            Modifier.background(AppButtonVerticalGradient)
                        } else {
                            Modifier.background(Color.Transparent)
                        }
                    )
                    .clickable { onTabSelected(MissionTab.MISSION) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.mission_tab_mission),
                    fontFamily = if (isMissionSelected) OutfitBold else ManropeSemiBold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = if (isMissionSelected) ColorFFFFFF else Color(0xFF7A6F8B)
                )
            }

            val isHistorySelected = selectedTab == MissionTab.HISTORY
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(SdpR_12))
                    .then(
                        if (isHistorySelected) {
                            Modifier.background(AppButtonVerticalGradient)
                        } else {
                            Modifier.background(Color.Transparent)
                        }
                    )
                    .clickable { onTabSelected(MissionTab.HISTORY) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.mission_tab_history),
                    fontFamily = if (isHistorySelected) ManropeBold else ManropeSemiBold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = if (isHistorySelected) ColorFFFFFF else Color(0xFF7A6F8B)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun MissionTabSelectorPreview() {
    MissionTabSelector(
        selectedTab = MissionTab.HISTORY,
        onTabSelected = {}
    )
}
