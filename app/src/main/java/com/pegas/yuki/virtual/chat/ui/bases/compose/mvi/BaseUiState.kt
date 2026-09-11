package com.pegas.yuki.virtual.chat.ui.bases.compose.mvi

import com.pegas.yuki.virtual.chat.domain.model.common.PublicError

interface BaseUiState {
    val isLoading: Boolean
    val error: PublicError?
}
