package com.anrstudio.template.ui.component.mission.component

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
import com.anrstudio.template.ui.bases.compose.theme.Color150F25
import com.anrstudio.template.ui.bases.compose.theme.Color161127
import com.anrstudio.template.ui.bases.compose.theme.Color322D41
import com.anrstudio.template.ui.bases.compose.theme.ColorAFA5C3
import com.anrstudio.template.ui.bases.compose.theme.ColorF1CBB7
import com.anrstudio.template.ui.bases.compose.theme.ManropeSemiBold
import com.anrstudio.template.ui.bases.compose.theme.OutfitBold
import com.anrstudio.template.ui.bases.compose.theme.SdpR_1
import com.anrstudio.template.ui.bases.compose.theme.SdpR_12
import com.anrstudio.template.ui.bases.compose.theme.SdpR_13
import com.anrstudio.template.ui.bases.compose.theme.SdpR_14
import com.anrstudio.template.ui.bases.compose.theme.SdpR_4
import com.anrstudio.template.ui.bases.compose.theme.SdpR_48
import com.anrstudio.template.ui.bases.compose.theme.nonScaledSp
import com.anrstudio.template.ui.component.mission.MissionTab
import com.pegas.aura.aigirlfriend.soul.R

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
            .background(Color150F25)
            .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_14))
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
                    .background(if (isMissionSelected) ColorF1CBB7 else Color.Transparent)
                    .clickable { onTabSelected(MissionTab.MISSION) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.mission_tab_mission),
                    fontFamily = if (isMissionSelected) OutfitBold else ManropeSemiBold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = if (isMissionSelected) Color161127 else ColorAFA5C3
                )
            }

            val isHistorySelected = selectedTab == MissionTab.HISTORY
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(SdpR_12))
                    .background(if (isHistorySelected) ColorF1CBB7 else Color.Transparent)
                    .clickable { onTabSelected(MissionTab.HISTORY) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.mission_tab_history),
                    fontFamily = if (isHistorySelected) OutfitBold else ManropeSemiBold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = if (isHistorySelected) Color161127 else ColorAFA5C3
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun MissionTabSelectorPreview() {
    MissionTabSelector(
        selectedTab = MissionTab.HISTORY,
        onTabSelected = {}
    )
}
