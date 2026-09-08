package com.anrstudio.template.ui.bases.compose.mvi

import com.anrstudio.template.domain.model.common.PublicError

interface BaseUiState {
    val isLoading: Boolean
    val error: PublicError?
}
