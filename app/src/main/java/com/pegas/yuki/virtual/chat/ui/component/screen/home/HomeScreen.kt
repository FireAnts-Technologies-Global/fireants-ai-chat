package com.pegas.yuki.virtual.chat.ui.component.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.character.Character
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterCategorySummary
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseScreen
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_100
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.appSplashBackground
import com.pegas.yuki.virtual.chat.ui.component.screen.home.component.CreateAssistantBanner
import com.pegas.yuki.virtual.chat.ui.component.screen.home.component.HomeEmptyContent
import com.pegas.yuki.virtual.chat.ui.component.screen.home.component.RecommendSection
import com.pegas.yuki.virtual.chat.ui.component.screen.home.component.TopAssistantsBentoSection

@Composable
fun HomeScreen(
    fromScreen: String? = null,
    onOpenCharacterDetail: (String) -> Unit = {},
    onCreateAssistant: () -> Unit = {},
    onSeeAll: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.handleIntent(HomeIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "HomeScreen",
        fromScreen = fromScreen,
        showBackground = false,
        errorTitleRes = R.string.home_error_title,
        onRetryError = {
            viewModel.handleIntent(HomeIntent.Retry)
        },
        onEffect = { effect ->
            when (effect) {
                is HomeEffect.NavigateCharacterDetail -> onOpenCharacterDetail(effect.slug)
                is HomeEffect.CreateAssistant -> onCreateAssistant()
            }
        }
    ) { state, onIntent ->
        HomeContent(
            state = state,
            onOpenCharacterDetail = {
                onIntent(HomeIntent.OpenCharacterDetail(it))
            },
            onLoadMore = {
                onIntent(HomeIntent.LoadMore)
            },
            onCreateAssistant = onCreateAssistant,
            onSeeAll = onSeeAll
        )
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onOpenCharacterDetail: (String) -> Unit = {},
    onLoadMore: () -> Unit = {},
    onCreateAssistant: () -> Unit = {},
    onSeeAll: () -> Unit = {}
) {
    val topAssistants = if (state.topAssistants.isNotEmpty()) state.topAssistants else state.characters.take(3)
    val recommendCharacters = if (state.recommendCharacters.isNotEmpty()) state.recommendCharacters else state.characters

    val listState = rememberLazyListState()

    val shouldLoadMore = remember(state.hasNextPage, state.isLoadingMore, state.error) {
        state.hasNextPage && !state.isLoadingMore && state.error == null
    }

    LaunchedEffect(
        listState,
        state.characters.size,
        shouldLoadMore
    ) {
        if (!shouldLoadMore) {
            return@LaunchedEffect
        }

        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisibleIndex ->
            if (
                lastVisibleIndex != null &&
                lastVisibleIndex >= (state.characters.size - LOAD_MORE_THRESHOLD)
            ) {
                onLoadMore()
            }
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Crossfade(
                targetState = state.isLoading,
                animationSpec = tween(durationMillis = 250),
                label = "HomeLoadingCrossfade"
            ) { isLoading ->
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageLoadingLottie(size = SdpR_32)
                    }
                } else if (state.characters.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        HomeEmptyContent()
                    }
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = SdpR_12,
                            bottom = SdpR_100
                        ),
                        verticalArrangement = Arrangement.spacedBy(SdpR_20)
                    ) {
                        item(key = "create_assistant_banner") {
                            CreateAssistantBanner(
                                backgroundPainter = painterResource(R.drawable.bg_create),
                                onCreateClick = onCreateAssistant,
                                modifier = Modifier.padding(horizontal = SdpR_16)
                            )
                        }

                        item(key = "top_assistants_section") {
                            TopAssistantsBentoSection(
                                topAssistants = topAssistants,
                                onCharacterClick = onOpenCharacterDetail,
                                modifier = Modifier.padding(horizontal = SdpR_16)
                            )
                        }

                        item(key = "recommend_section") {
                            RecommendSection(
                                characters = recommendCharacters,
                                onCharacterClick = onOpenCharacterDetail,
                                onSeeAllClick = onSeeAll
                            )
                        }

                        if (state.isLoadingMore) {
                            item(key = "load_more") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = SdpR_12),
                                    contentAlignment = Alignment.Center
                                ) {
                                    ImageLoadingLottie(size = SdpR_16)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private const val LOAD_MORE_THRESHOLD = 4

@Preview(
    name = "Home characters",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun HomeContentPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appSplashBackground()
    ) {
        HomeContent(
            state = HomeUiState(
                characters = listOf(
                    previewCharacter("sakura", "Sakura"),
                    previewCharacter("hespera", "Hespera"),
                    previewCharacter("luna", "Luna"),
                    previewCharacter("mia", "Mia")
                ),
                currentPage = 1,
                hasNextPage = false,
                hasLoadedInitialData = true
            )
        )
    }
}

private fun previewCharacter(id: String, name: String) = Character(
    id = id,
    name = name,
    slug = id,
    image = null,
    description = "A warm and playful AI companion.",
    task = "Companion",
    categoryId = "companion",
    category = CharacterCategorySummary(
        id = "companion",
        name = "Companion",
        slug = "companion",
        sort = 1
    ),
    sort = 1,
    isHot = true,
    likes = 1_000,
    ratingStars = 4.8,
    ratingCount = 250,
    createdAt = "2026-07-29T00:00:00Z",
    updatedAt = "2026-07-29T00:00:00Z"
)
