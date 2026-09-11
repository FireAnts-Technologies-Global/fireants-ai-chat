package com.pegas.yuki.virtual.chat.ui.component.screen.subscription

import android.app.Activity
import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseUiState

data class SubscriptionPlanUiModel(
    val id: String,
    val storeProductId: String,
    val title: String? = null,
    val isAnnual: Boolean,
    val priceText: String,
    val dailyBonusGems: String,
    val isActivePlan: Boolean = false,
    val savePercentageText: String? = null,
    val monthlyBreakdownText: String? = null
)

data class SubscriptionUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val plans: List<SubscriptionPlanUiModel> = emptyList(),
    val selectedPlanId: String? = null,
    val isPurchasing: Boolean = false,
    val activePlanStoreProductId: String? = null
) : BaseUiState

sealed interface SubscriptionIntent {
    data object Initialize : SubscriptionIntent
    data class PurchasePlan(val activity: Activity, val planId: String) : SubscriptionIntent
    data object RestorePurchases : SubscriptionIntent
    data class SelectPlan(val planId: String) : SubscriptionIntent
    data object Retry : SubscriptionIntent
}

sealed interface SubscriptionEffect {
    data class ShowToast(val messageRes: Int, val formatArgs: List<Any> = emptyList()) :
        SubscriptionEffect

    data object NavigateBack : SubscriptionEffect
}