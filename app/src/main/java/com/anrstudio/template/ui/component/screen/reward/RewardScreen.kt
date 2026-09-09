package com.pegas.aura.aigirlfriend.soul.ui.component.screen.reward

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_56
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_100
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_32
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.appSplashBackground
import com.pegas.aura.aigirlfriend.soul.ui.bases.ext.findActivity
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.reward.component.CheckInRewardDialog
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.reward.component.ClaimRewardDialog
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.reward.component.DailyProgressSection
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.reward.component.EarnRewardsSection
import com.pegas.aura.aigirlfriend.soul.ui.model.asString

@Composable
fun RewardScreen(
    fromScreen: String? = null,
    onNavigateToStore: () -> Unit = {},
    viewModel: RewardViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(RewardIntent.Initialize)
        val activity = context.findActivity()
        if (activity != null) {
            viewModel.handleIntent(RewardIntent.PreloadAd(activity))
        }
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "RewardScreen",
        fromScreen = fromScreen,
        showLoadingDialog = true,
        loadingDialogText = stringResource(R.string.ads_loading_label),
        shouldShowLoadingDialog = { it.isWatchingAd || it.isCheckingIn },
        errorTitleRes = R.string.reward_error_title,
        onRetryError = { viewModel.handleIntent(RewardIntent.Initialize) },
        onEffect = { effect ->
            when (effect) {
                is RewardEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        effect.error.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is RewardEffect.ShowMessage -> {
                    Toast.makeText(
                        context,
                        context.getString(effect.messageRes),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                RewardEffect.NavigateToStore -> onNavigateToStore()
            }
        }
    ) { state, onIntent ->
        RewardContent(
            state = state,
            onIntent = onIntent
        )
    }
}

@Composable
private fun RewardContent(
    state: RewardUiState,
    onIntent: (RewardIntent) -> Unit
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .appSplashBackground()
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    ImageLoadingLottie(size = SdpR_32)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = SdpR_16),
                    state = listState,
                    contentPadding = PaddingValues(bottom = SdpR_100)
                ) {
                    item(contentType = "daily_progress") {
                        Spacer(modifier = Modifier.height(SdpR_8))
                        DailyProgressSection(
                            currentDayIndex = state.dayIndex,
                            claimedToday = state.claimedToday,
                            rewards = state.rewards,
                            onCheckInClick = {
                                val activity = context.findActivity()
                                if (activity != null) {
                                    onIntent(RewardIntent.ClaimCheckIn(activity))
                                }
                            }
                        )
                    }

                    item(contentType = "spacer") {
                        Spacer(modifier = Modifier.height(SdpR_8))
                    }

                    item(contentType = "earn_rewards") {
                        EarnRewardsSection(
                            adsWatchedToday = state.adsWatchedToday,
                            adsRemainingToday = state.adsRemainingToday,
                            coinsPerView = state.coinsPerView,
                            adsConfigLoaded = state.adsConfigLoaded,
                            isWatchingAd = state.isWatchingAd,
                            isVip = state.isVip,
                            onWatchAdClick = {
                                val activity = context.findActivity()
                                if (activity != null) {
                                    onIntent(RewardIntent.WatchAd(activity))
                                }
                            }
                        )
                    }

                    item(contentType = "bottom_spacing") {
                        Spacer(modifier = Modifier.height(SdpR_24))
                    }
                }
            }
        }

        if (state.showClaimDialog) {
            ClaimRewardDialog(
                rewardAmount = state.claimedRewardAmount,
                onDismiss = {
                    onIntent(RewardIntent.DismissClaimDialog)
                }
            )
        }

        if (state.showCheckInDialog) {
            CheckInRewardDialog(
                rewardAmount = state.checkInRewardAmount,
                onDismiss = {
                    onIntent(RewardIntent.DismissCheckInDialog)
                }
            )
        }
    }
}

@Preview(
    name = "Reward Screen",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun RewardContentPreview() {
    RewardContent(
        state = RewardUiState(
            dayIndex = 3,
            claimedToday = false,
            showClaimDialog = false
        ),
        onIntent = {}
    )
}
