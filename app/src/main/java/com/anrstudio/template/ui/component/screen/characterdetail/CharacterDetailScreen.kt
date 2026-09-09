package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ads.AdRemoteConfig
import com.pegas.aura.aigirlfriend.soul.ads.banner_all
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackground
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackgroundsData
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategorySummary
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterProgress
import com.pegas.aura.aigirlfriend.soul.domain.model.character.FullCharacterDetail
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.BannerAdView
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.CommonTopBar
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.CommonTopBarStyle
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color08030F
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_150
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.ext.showRateDialog
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component.CharacterDetailBottomBar
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component.CharacterHeaderCover
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component.CharacterInfoSection
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component.ExclusivePhotoGallery
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component.IntimacyProgressCard

@Composable
fun CharacterDetailScreen(
    fromScreen: String? = null,
    onBack: () -> Unit,
    onOpenChat: (String) -> Unit = {},
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(CharacterDetailIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "CharacterDetailScreen",
        fromScreen = fromScreen,
        showLoadingDialog = true,
        shouldShowLoadingDialog = { it.isLoading || it.isStartingChat },
        errorTitleRes = R.string.character_detail_error_title,
        onRetryError = {
            viewModel.handleIntent(CharacterDetailIntent.Retry)
        },
        onEffect = { effect ->
            when (effect) {
                is CharacterDetailEffect.NavigateToChat -> onOpenChat(effect.conversationId)
                is CharacterDetailEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        context.getString(effect.messageResId),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                CharacterDetailEffect.ShowRateDialog -> {
                    val activity = context as? Activity
                    if (activity != null) {
                        showRateDialog(activity, false) {
                            viewModel.handleIntent(CharacterDetailIntent.RateSubmitted)
                        }
                    }
                }
            }
        }
    ) { state, onIntent ->
        CharacterDetailContent(
            state = state,
            onBack = onBack,
            onStartChat = {
                onIntent(CharacterDetailIntent.StartChat)
            },
            onPurchaseBackground = { bg ->
                if (bg.isLocked) {
                    onIntent(CharacterDetailIntent.PurchaseBackground(bg.id))
                }
            }
        )
    }
}

@Composable
private fun CharacterDetailContent(
    state: CharacterDetailUiState,
    onBack: () -> Unit,
    onStartChat: () -> Unit,
    onPurchaseBackground: (CharacterBackground) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current
    val collapseThresholdPx = with(density) { SdpR_150.toPx() }

    val collapseProgress by remember {
        derivedStateOf {
            if (lazyListState.firstVisibleItemIndex > 0) {
                1f
            } else {
                (
                        lazyListState.firstVisibleItemScrollOffset /
                                collapseThresholdPx
                        ).coerceIn(0f, 1f)
            }
        }
    }

    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color08030F)
            .navigationBarsPadding()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            containerColor = Color08030F
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color08030F)
                    .padding(paddingValues)
            ) {
                state.characterDetail?.let { fullDetail ->
                    CharacterDetailBody(
                        fullDetail = fullDetail,
                        purchasingBackgroundId = state.purchasingBackgroundId,
                        lazyListState = lazyListState,
                        onBackgroundClick = onPurchaseBackground
                    )
                }

                CommonTopBar(
                    title = state.character?.name.orEmpty(),
                    style = CommonTopBarStyle.CHARACTER_DETAIL,
                    showActions = true,
                    onBack = onBack,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .background(
                            Color08030F.copy(alpha = collapseProgress)
                        )
                        .statusBarsPadding()
                )
            }
        }

        if (state.characterDetail != null) {
            CharacterDetailBottomBar(
                isStartingChat = state.isStartingChat,
                onStartChat = onStartChat
            )
        }

        BannerAdView(
            adUnitId = AdRemoteConfig.banner_all.id,
            isEnabled = AdRemoteConfig.banner_all.isEnable,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CharacterDetailBody(
    fullDetail: FullCharacterDetail,
    purchasingBackgroundId: String?,
    lazyListState: LazyListState,
    onBackgroundClick: (CharacterBackground) -> Unit
) {
    val character = fullDetail.character
    val backgroundsData = fullDetail.backgroundsData

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_16)
    ) {
        item {
            CharacterHeaderCover(character = character)
        }

        item {
            CharacterInfoSection(character = character)
        }

        item {
            IntimacyProgressCard(progress = backgroundsData?.progress)
        }

        if (backgroundsData != null && backgroundsData.backgrounds.isNotEmpty()) {
            item {
                ExclusivePhotoGallery(
                    backgrounds = backgroundsData.backgrounds,
                    purchasingBackgroundId = purchasingBackgroundId,
                    onBackgroundClick = onBackgroundClick
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(SdpR_16))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun CharacterDetailContentPreview() {
    CharacterDetailContent(
        state = CharacterDetailUiState(
            characterDetail = FullCharacterDetail(
                character = Character(
                    id = "1",
                    name = "Luna",
                    slug = "luna",
                    image = null,
                    description = "Dreamer",
                    task = "Soulmate",
                    tags = listOf("Empathetic", "Cozy", "Dreamer", "Sweet & Caring"),
                    age = 21,
                    gender = "FEMALE",
                    categoryId = "cat1",
                    category = CharacterCategorySummary("cat1", "Anime", "anime", 1),
                    sort = 1,
                    isHot = true,
                    likes = 14200,
                    ratingStars = 4.9,
                    ratingCount = 120,
                    createdAt = "",
                    updatedAt = ""
                ),
                backgroundsData = CharacterBackgroundsData(
                    progress = CharacterProgress("1", 40, 12, 50, 100, 100, 60, false),
                    backgrounds = listOf(
                        CharacterBackground("1", "1", "Bg 1", "", "", 15, 1, false, true, false),
                        CharacterBackground("2", "1", "Bg 2", "", "", 20, 1, false, true, false),
                        CharacterBackground("3", "1", "Bg 3", "", "", 15, 1, false, true, false)
                    )
                )
            )
        ),
        onBack = {},
        onStartChat = {},
        onPurchaseBackground = {}
    )
}
