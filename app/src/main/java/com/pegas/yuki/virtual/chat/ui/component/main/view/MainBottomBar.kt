package com.pegas.yuki.virtual.chat.ui.component.main.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color6F6794
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorB957E8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD94BEE
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorF84DB5
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFF6B8A
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeMedium
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_22
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_48
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_56
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_7
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_74
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.main.MainTab


@Composable
fun MainBottomBar(
    tabs: List<MainTab>,
    currentRoute: String?,
    onTabSelected: (MainTab) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SdpR_74)
            .background(
                color = ColorFFFFFF,
                shape = RoundedCornerShape(SdpR_32)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEach { tab ->
            BottomBarItem(
                tab = tab,
                selected = currentRoute == tab.route,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit
) {
    val tint by animateColorAsState(
        targetValue = if (selected) ColorB957E8 else Color6F6794,
        label = "bottomBarTint"
    )

    val interactionSource = remember {
        MutableInteractionSource()
    }

    if (tab == MainTab.Add) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(SdpR_56),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(SdpR_56)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                ColorD94BEE,
                                ColorF84DB5,
                                ColorFF6B8A
                            )
                        ),
                        shape = CircleShape
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(tab.label),
                    modifier = Modifier.size(SdpR_48),
                    tint = Color.Unspecified
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .weight(1f)
                .height(SdpR_74)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(
                    top = SdpR_10,
                    bottom = SdpR_7
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(
                    id = when (tab) {
                        MainTab.Discover -> R.drawable.ic_discover
                        MainTab.Reward -> R.drawable.ic_reward
                        MainTab.Add -> R.drawable.ic_add
                        MainTab.Chats -> R.drawable.ic_chat
                        MainTab.Mission -> R.drawable.ic_mission
                    }
                ),
                contentDescription = stringResource(tab.label),
                modifier = Modifier.size(SdpR_22),
                tint = tint
            )
            Spacer(
                modifier = Modifier.height(SdpR_2)
            )
            Text(
                text = stringResource(tab.label),
                color = tint,
                fontSize = SdpR_10.nonScaledSp,
                fontFamily = if (selected) ManropeBold else ManropeMedium,
                fontWeight = if (selected) {
                    FontWeight.Bold
                } else {
                    FontWeight.Medium
                },
                maxLines = 1
            )
        }
    }
}

@Preview(
    name = "Main Bottom Bar",
    showBackground = true,
)
@Composable
private fun MainBottomBarPreview() {
    MainBottomBar(
        tabs = listOf(
            MainTab.Discover,
            MainTab.Reward,
            MainTab.Add,
            MainTab.Chats,
            MainTab.Mission
        ),
        currentRoute = MainTab.Discover.route,
        onTabSelected = {}
    )
}
