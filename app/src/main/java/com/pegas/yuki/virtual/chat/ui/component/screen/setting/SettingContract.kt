package com.pegas.yuki.virtual.chat.ui.component.screen.setting

import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseUiState

data class SettingUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val title: String = "Setting",
    val description: String = "Setting screen scaffold is ready for feature migration."
) : BaseUiState

sealed interface SettingIntent {
    data object Initialize : SettingIntent
}

sealed interface SettingEffect
