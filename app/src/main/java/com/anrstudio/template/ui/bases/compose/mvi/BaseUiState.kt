package com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi

import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError

interface BaseUiState {
    val isLoading: Boolean
    val error: PublicError?
}
