package com.pegas.yuki.virtual.chat.ui.component.screen.mission

import androidx.annotation.StringRes
import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseUiState

enum class MissionTab {
    MISSION,
    HISTORY
}

data class MissionHistoryItemUiState(
    val id: String,
    @StringRes val titleRes: Int? = null,
    val titleRaw: String? = null,
    val gemsEarned: Int,
    val timeAgo: String,
    val exactTime: String
)

data class MissionUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val selectedTab: MissionTab = MissionTab.HISTORY,
    val totalQuestsCompleted: Int = 24,
    val totalGemsObtained: Int = 385,
    val historyItems: List<MissionHistoryItemUiState> = emptyList()
) : BaseUiState

sealed interface MissionIntent {
    data object Initialize : MissionIntent
    data class SelectTab(val tab: MissionTab) : MissionIntent
}

sealed interface MissionEffect {
    data class ShowToast(@StringRes val messageRes: Int) : MissionEffect
}
