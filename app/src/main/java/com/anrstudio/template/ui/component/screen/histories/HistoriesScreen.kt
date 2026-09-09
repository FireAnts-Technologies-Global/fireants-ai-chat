package com.pegas.aura.aigirlfriend.soul.ui.component.screen.histories

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage

@Composable
fun HistoriesScreen(
    fromScreen: String? = null,
    onBack: () -> Unit,
    onOpenCharacterDetail: (String) -> Unit = {},
    viewModel: HistoriesViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.handleIntent(HistoriesIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "HistoriesScreen",
        fromScreen = fromScreen,
        errorTitleRes = R.string.home_error_title,
        onRetryError = {
            viewModel.handleIntent(HistoriesIntent.Retry)
        },
        onEffect = { effect ->
            when (effect) {
                is HistoriesEffect.NavigateCharacterDetail -> onOpenCharacterDetail(effect.slug)
                HistoriesEffect.NavigateBack -> onBack()
            }
        }
    ) { state, onIntent ->
        HistoriesContent(
            state = state,
            onBack = onBack,
            onCharacterClick = { slug ->
                onIntent(HistoriesIntent.OpenCharacterDetail(slug))
            },
            onLoadMore = {
                onIntent(HistoriesIntent.LoadMore)
            }
        )
    }
}

@Composable
fun HistoriesContent(
    state: HistoriesUiState,
    onBack: () -> Unit,
    onCharacterClick: (String) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItems = gridState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisibleItemIndex >= totalItems - 4
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .appSplashBackground()
    ) {
        HistoriesTopBar(onBack = onBack)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (state.isLoading && state.characters.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ImageLoadingLottie(size = SdpR_56)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = SdpR_16,
                        vertical = SdpR_12
                    ),
                    horizontalArrangement = Arrangement.spacedBy(SdpR_12),
                    verticalArrangement = Arrangement.spacedBy(SdpR_14)
                ) {
                    items(
                        items = state.characters,
                        key = { it.id },
                        contentType = { "history_character" }
                    ) { character ->
                        HistoryCharacterCard(
                            character = character,
                            onClick = { onCharacterClick(character.slug) }
                        )
                    }

                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = SdpR_16),
                                contentAlignment = Alignment.Center
                            ) {
                                ImageLoadingLottie(size = SdpR_36)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoriesTopBar(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(SdpR_56)
            .padding(horizontal = SdpR_16)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(SdpR_36)
                .background(color = ColorFFFFFF, shape = CircleShape)
                .border(width = SdpR_1, color = ColorE9DDF2, shape = CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onBack
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = stringResource(R.string.close),
                tint = ColorE25798,
                modifier = Modifier.size(SdpR_15)
            )
        }

        Text(
            text = stringResource(R.string.histories_title),
            fontFamily = ManropeBold,
            fontWeight = FontWeight.Bold,
            fontSize = SdpR_18.nonScaledSp,
            color = Color110640,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun HistoryCharacterCard(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displayName = if (character.age != null) "${character.name}, ${character.age}" else character.name

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(SdpR_220)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(SdpR_18),
        colors = CardDefaults.cardColors(containerColor = Color161127),
        border = BorderStroke(SdpR_1, Color14000000)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingAsyncImage(
                imageUrl = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to Color.Transparent,
                                0.45f to Color.Transparent,
                                1.0f to Color66000000
                            )
                        )
                    )
            )

            val isHot = character.isHot
            Box(
                modifier = Modifier
                    .padding(SdpR_8)
                    .align(Alignment.TopStart)
                    .background(
                        color = if (isHot) ColorED4DA4 else ColorFFB03A,
                        shape = RoundedCornerShape(SdpR_6)
                    )
                    .padding(horizontal = SdpR_6, vertical = SdpR_2)
            ) {
                Text(
                    text = if (isHot) stringResource(R.string.character_detail_hot) else stringResource(R.string.character_badge_new),
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_8.nonScaledSp,
                    color = if (isHot) ColorFFFFFF else Color663A1C
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(horizontal = SdpR_10, vertical = SdpR_10),
                verticalArrangement = Arrangement.spacedBy(SdpR_2)
            ) {
                Text(
                    text = displayName,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_14.nonScaledSp,
                    color = ColorFFFFFF,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (character.description.isNotBlank()) {
                    Text(
                        text = character.description,
                        fontFamily = ManropeRegular,
                        fontWeight = FontWeight.Normal,
                        fontSize = SdpR_10.nonScaledSp,
                        lineHeight = SdpR_12.nonScaledSp,
                        color = ColorF2FFFFFF,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HistoriesContentPreview() {
    HistoriesContent(
        state = HistoriesUiState(
            characters = listOf(
                Character(
                    id = "1",
                    name = "Hespera",
                    slug = "hespera",
                    image = null,
                    description = "Hespera is a mysterious night owl drawn to ancient...",
                    task = "",
                    age = 24,
                    isHot = true
                ),
                Character(
                    id = "2",
                    name = "Elara",
                    slug = "elara",
                    image = null,
                    description = "Elara is a curious astronomer who spends...",
                    task = "",
                    age = 22,
                    isHot = false
                )
            )
        ),
        onBack = {},
        onCharacterClick = {},
        onLoadMore = {}
    )
}