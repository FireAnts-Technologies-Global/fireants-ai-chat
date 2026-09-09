package com.pegas.aura.aigirlfriend.soul.ui.component.screen.histories

import androidx.compose.runtime.Immutable
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseUiState

@Immutable
data class HistoriesUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val isLoadingMore: Boolean = false,
    val characters: List<Character> = emptyList(),
    val currentPage: Int = 0,
    val hasNextPage: Boolean = true,
    val hasLoadedInitialData: Boolean = false
) : BaseUiState

sealed interface HistoriesIntent {
    data object Initialize : HistoriesIntent
    data object Retry : HistoriesIntent
    data object LoadMore : HistoriesIntent
    data class OpenCharacterDetail(val slug: String) : HistoriesIntent
    data object NavigateBack : HistoriesIntent
}

sealed interface HistoriesEffect {
    data class NavigateCharacterDetail(val slug: String) : HistoriesEffect
    data object NavigateBack : HistoriesEffect
}