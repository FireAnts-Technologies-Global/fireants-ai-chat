package com.pegas.yuki.virtual.chat.ui.component.screen.mission

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseScreen
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_100
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_11
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.screen.mission.component.MissionHistoryItemCard
import com.pegas.yuki.virtual.chat.ui.component.screen.mission.component.MissionTabSelector

@Composable
fun MissionScreen(
    fromScreen: String? = null,
    viewModel: MissionViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.handleIntent(MissionIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "MissionScreen",
        fromScreen = fromScreen,
        showBackground = false
    ) { state, onIntent ->
        MissionContent(
            state = state,
            onIntent = onIntent
        )
    }
}

@Composable
private fun MissionContent(
    state: MissionUiState,
    onIntent: (MissionIntent) -> Unit
) {
    val historyListState = rememberLazyListState()
    val radius = SdpR_16
    val strokeWidth = SdpR_1
    val dash = SdpR_4
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = SdpR_16)
            ) {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageLoadingLottie(size = SdpR_32)
                    }
                } else {
                    Spacer(modifier = Modifier.height(SdpR_8))

                    MissionTabSelector(
                        selectedTab = state.selectedTab,
                        onTabSelected = { tab ->
                            onIntent(MissionIntent.SelectTab(tab))
                        }
                    )

                    Spacer(modifier = Modifier.height(SdpR_12))

                    if (state.selectedTab == MissionTab.HISTORY) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            state = historyListState,
                            contentPadding = PaddingValues(bottom = SdpR_100),
                            verticalArrangement = Arrangement.spacedBy(SdpR_8)
                        ) {
                            items(
                                items = state.historyItems,
                                key = { it.id },
                                contentType = { "mission_history" }
                            ) { item ->
                                MissionHistoryItemCard(item = item)
                            }

                            item(contentType = "stardust_footer") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = SdpR_8)
                                        .drawBehind {
                                            drawRoundRect(
                                                color = Color(0xFFEDE9F2),
                                                cornerRadius = CornerRadius(
                                                    radius.toPx(),
                                                    radius.toPx()
                                                ),
                                                style = Stroke(
                                                    width = strokeWidth.toPx(),
                                                    pathEffect = PathEffect.dashPathEffect(
                                                        floatArrayOf(
                                                            dash.toPx(),
                                                            dash.toPx()
                                                        )
                                                    )
                                                )
                                            )
                                        }
                                        .padding(vertical = SdpR_12, horizontal = SdpR_16),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.mission_history_footer_stardust),
                                        fontFamily = ManropeRegular,
                                        fontSize = SdpR_11.nonScaledSp,
                                        color = Color(0xFF7A6F8B)
                                    )
                                }
                            }

                            item(contentType = "bottom_spacing") {
                                Spacer(modifier = Modifier.height(SdpR_24))
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(bottom = SdpR_100)
                                .background(
                                    color = ColorFFFFFF,
                                    shape = RoundedCornerShape(radius)
                                )
                                .border(
                                    width = SdpR_1,
                                    color = Color(0xFFEDE9F2),
                                    shape = RoundedCornerShape(radius)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.mission_no_active),
                                fontFamily = ManropeRegular,
                                fontSize = SdpR_12.nonScaledSp,
                                color = Color(0xFF7A6F8B)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Mission Screen History",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun MissionScreenPreview() {
    MissionContent(
        state = MissionUiState(
            selectedTab = MissionTab.HISTORY,
            totalQuestsCompleted = 24,
            totalGemsObtained = 385,
            historyItems = listOf(
                MissionHistoryItemUiState(
                    "1",
                    R.string.mission_type_play_game,
                    null,
                    15,
                    "Today",
                    "Jan 26, 14:32"
                ),
                MissionHistoryItemUiState(
                    "2",
                    R.string.mission_type_online,
                    null,
                    15,
                    "Today",
                    "Jan 26, 10:15"
                ),
                MissionHistoryItemUiState(
                    "3",
                    R.string.mission_type_daily_login,
                    null,
                    15,
                    "Yesterday",
                    "Jan 25, 08:00"
                ),
                MissionHistoryItemUiState(
                    "4",
                    R.string.mission_type_share,
                    null,
                    15,
                    "Yesterday",
                    "Jan 25, 19:40"
                ),
                MissionHistoryItemUiState(
                    "5",
                    R.string.mission_type_watch_ad,
                    null,
                    15,
                    "2 days ago",
                    "Jan 24, 15:20"
                )
            )
        ),
        onIntent = {}
    )
}
