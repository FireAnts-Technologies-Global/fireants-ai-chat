package com.pegas.aura.aigirlfriend.soul.ui.component.screen.mission

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color17FFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_10
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_56
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.appSplashBackground
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.mission.component.MissionHistoryItemCard
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.mission.component.MissionTabSelector
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.mission.component.RitualChronicleBanner

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
        fromScreen = fromScreen
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
        modifier = Modifier
            .fillMaxSize()
            .appSplashBackground()
    ) {
        Scaffold(
            containerColor = Color.Transparent
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
                        ImageLoadingLottie(size = SdpR_56)
                    }
                } else {
                    RitualChronicleBanner(
                        completedQuests = state.totalQuestsCompleted,
                        gemsObtained = state.totalGemsObtained
                    )

                    Spacer(modifier = Modifier.height(SdpR_10))

                    MissionTabSelector(
                        selectedTab = state.selectedTab,
                        onTabSelected = { tab ->
                            onIntent(MissionIntent.SelectTab(tab))
                        }
                    )

                    Spacer(modifier = Modifier.height(SdpR_10))

                    if (state.selectedTab == MissionTab.HISTORY) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            state = historyListState,
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
                                                color = Color17FFFFFF,
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
                                        color = ColorAFA5C3
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
                                .background(
                                    color = Color150F25,
                                    shape = RoundedCornerShape(radius)
                                )
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color17FFFFFF,
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
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.mission_no_active),
                                fontFamily = ManropeRegular,
                                fontSize = SdpR_12.nonScaledSp,
                                color = ColorAFA5C3
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
