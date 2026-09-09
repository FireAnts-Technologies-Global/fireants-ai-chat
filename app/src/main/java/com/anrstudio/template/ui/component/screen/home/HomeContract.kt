package com.pegas.aura.aigirlfriend.soul.ui.component.screen.home

import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseUiState

data class HomeUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val characters: List<Character> = emptyList(),
    val isLoadingMore: Boolean = false,
    val currentPage: Int = 0,
    val hasNextPage: Boolean = true,
    val hasLoadedInitialData: Boolean = false
) : BaseUiState

sealed interface HomeIntent {
    data object Initialize : HomeIntent

    data class OpenCharacterDetail(val slug: String) : HomeIntent

    object CreateAssistant : HomeIntent


    data object LoadMore : HomeIntent

    data object Retry : HomeIntent
}

sealed interface HomeEffect {
    data class NavigateCharacterDetail(val slug: String) : HomeEffect
    data object CreateAssistant : HomeEffect
}
