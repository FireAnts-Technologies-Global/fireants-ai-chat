package com.pegas.yuki.virtual.chat.ui.component.screen.reward

import android.app.Activity
import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseUiState

data class RewardUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val dayIndex: Int = 0,
    val claimedToday: Boolean = false,
    val rewards: List<Int> = emptyList(),
    val showClaimDialog: Boolean = false,
    val showCheckInDialog: Boolean = false,
    val claimedRewardAmount: Int = 0,
    val checkInRewardAmount: Int = 0,
    val coinBalance: Int = 0,
    val adsWatchedToday: Int = 0,
    val adsRemainingToday: Int = 0,
    val coinsPerView: Int = 15,
    val adsConfigLoaded: Boolean = false,
    val adsRequireSsv: Boolean = false,
    val authUserId: String? = null,
    val isWatchingAd: Boolean = false,
    val isAdPreloaded: Boolean = false,
    val isCheckingIn: Boolean = false,
    val isVip: Boolean = false
) : BaseUiState

sealed interface RewardIntent {
    data object Initialize : RewardIntent
    data class PreloadAd(val activity: Activity) : RewardIntent
    data class ClaimCheckIn(val activity: Activity) : RewardIntent
    data class WatchAd(val activity: Activity) : RewardIntent
    data object DismissClaimDialog : RewardIntent
    data object DismissCheckInDialog : RewardIntent
    data object OpenStore : RewardIntent
}

sealed interface RewardEffect {
    data class ShowToast(val error: PublicError) : RewardEffect
    data class ShowMessage(val messageRes: Int) : RewardEffect
    data object NavigateToStore : RewardEffect
}
