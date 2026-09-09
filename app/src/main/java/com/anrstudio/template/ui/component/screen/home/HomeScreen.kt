package com.pegas.aura.aigirlfriend.soul.ui.component.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.layout.WindowInsets
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
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategorySummary
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_20
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_32
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_56
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_100
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.appSplashBackground
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.home.component.CharacterCard
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.home.component.CreateAssistantBanner
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.home.component.HomeEmptyContent

@Composable
fun HomeScreen(
    fromScreen: String? = null,
    onOpenCharacterDetail: (String) -> Unit = {},
    onCreateAssistant: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.handleIntent(HomeIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "HomeScreen",
        fromScreen = fromScreen,
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
        )
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onOpenCharacterDetail: (String) -> Unit = {},
    onLoadMore: () -> Unit = {},
    onCreateAssistant: () -> Unit = {},
) {
    val gridState = rememberLazyGridState()
    val shouldLoadMore = remember(state.hasNextPage, state.isLoadingMore, state.error) {
        state.hasNextPage && !state.isLoadingMore && state.error == null
    }

    LaunchedEffect(
        gridState,
        state.characters.size,
        shouldLoadMore
    ) {
        if (!shouldLoadMore) {
            return@LaunchedEffect
        }

        snapshotFlow {
            gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisibleIndex ->
            if (
                lastVisibleIndex != null &&
                lastVisibleIndex >= state.characters.lastIndex - LOAD_MORE_THRESHOLD
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
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageLoadingLottie(size = SdpR_56)
                    }
                }

                state.characters.isEmpty() -> {
                    HomeEmptyContent(modifier = Modifier)
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = gridState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = SdpR_16,
                            top = SdpR_12,
                            end = SdpR_16,
                            bottom = SdpR_100
                        ),
                        verticalArrangement = Arrangement.spacedBy(SdpR_12),
                        horizontalArrangement = Arrangement.spacedBy(SdpR_12)
                    ) {
                        item(
                            key = "create_assistant_banner",
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            CreateAssistantBanner(
                                backgroundPainter = painterResource(R.drawable.bg_create),
                                onCreateClick = onCreateAssistant
                            )
                        }

                        items(
                            items = state.characters,
                            key = { it.id },
                            contentType = { "character" }
                        ) { character ->
                            CharacterCard(
                                character = character,
                                onClick = {
                                    onOpenCharacterDetail(character.slug)
                                }
                            )
                        }

                        if (state.isLoadingMore) {
                            item(
                                key = "load_more",
                                span = { GridItemSpan(maxLineSpan) },
                                contentType = "loading"
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = SdpR_12),
                                    contentAlignment = Alignment.Center
                                ) {
                                    ImageLoadingLottie(size = SdpR_32)
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
