package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail

import com.pegas.aura.aigirlfriend.soul.domain.model.character.FullCharacterDetail
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseUiState

data class CharacterDetailUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val characterDetail: FullCharacterDetail? = null,
    val isStartingChat: Boolean = false,
    val purchasingBackgroundId: String? = null,
    val coinBalance: Int = 0
) : BaseUiState {
    val character get() = characterDetail?.character
    val backgroundsData get() = characterDetail?.backgroundsData
}

sealed interface CharacterDetailIntent {
    data object Initialize : CharacterDetailIntent
    data object Retry : CharacterDetailIntent
    data object StartChat : CharacterDetailIntent
    data class PurchaseBackground(val backgroundId: String) : CharacterDetailIntent
    data object RateSubmitted : CharacterDetailIntent
}

sealed interface CharacterDetailEffect {
    data class NavigateToChat(val conversationId: String) : CharacterDetailEffect
    data class ShowToast(val messageResId: Int) : CharacterDetailEffect
    data object ShowRateDialog : CharacterDetailEffect
}
