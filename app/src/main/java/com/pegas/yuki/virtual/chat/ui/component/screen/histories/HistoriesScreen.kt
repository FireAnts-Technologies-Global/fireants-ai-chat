package com.pegas.yuki.virtual.chat.ui.component.screen.histories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.character.Character
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseScreen
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color110640
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color663A1C
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color6B5E80
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD9D9D9
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE25798
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE9DDF2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFF41A3
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFBF92
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeExtraBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_100
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_15
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_18
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_220
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_36
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_5
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_56
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.custom.LoadingAsyncImage

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
        modifier = modifier.fillMaxSize()
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
                    ImageLoadingLottie(size = SdpR_32)
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
        colors = CardDefaults.cardColors(containerColor = ColorD9D9D9),
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
                                0.55f to Color.Transparent,
                                1.0f to ColorFFFFFF
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
                        color = if (isHot) ColorFF41A3 else ColorFFBF92,
                        shape = RoundedCornerShape(SdpR_100)
                    )
                    .padding(horizontal = SdpR_10, vertical = SdpR_5)
            ) {
                Text(
                    text = if (isHot) stringResource(R.string.character_detail_hot) else stringResource(R.string.character_badge_new),
                    fontFamily = ManropeExtraBold,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = SdpR_10.nonScaledSp,
                    color = if (isHot) ColorFFFFFF else Color663A1C
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(horizontal = SdpR_10, vertical = SdpR_10),
                verticalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                Text(
                    text = displayName,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_15.nonScaledSp,
                    color = ColorFFFFFF,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (character.description.isNotBlank()) {
                    Text(
                        text = character.description,
                        fontFamily = ManropeRegular,
                        fontWeight = FontWeight.Normal,
                        fontSize = SdpR_12.nonScaledSp,
                        lineHeight = SdpR_18.nonScaledSp,
                        color = Color6B5E80,
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