package com.anrstudio.template.ui.component.store

import android.app.Activity
import com.anrstudio.template.domain.model.common.PublicError
import com.anrstudio.template.ui.bases.compose.mvi.BaseUiState

data class CoinPackageUiModel(
    val id: String,
    val coinAmount: Int,
    val displayName: String?,
    val bonusBadgeText: String?,
    val priceText: String
)

data class MembershipPlanUiModel(
    val id: String,
    val title: String,
    val price: String,
    val durationLabelRes: Int,
    val descriptionRes: Int,
    val benefitsRes: List<Int>,
    val footerTextRes: Int,
    val isBestValue: Boolean
)

data class StoreUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val coinBalance: Int = 0,
    val coinPackages: List<CoinPackageUiModel> = emptyList(),
    val membershipPlans: List<MembershipPlanUiModel> = emptyList(),
    val isPurchasing: Boolean = false,
    val selectedPackageId: String? = null,
    val selectedPlanId: String? = null
) : BaseUiState

sealed interface StoreIntent {
    data object Initialize : StoreIntent
    data class PurchaseGemPackage(val activity: Activity, val packageId: String) : StoreIntent
    data class PurchaseMembershipPlan(val activity: Activity, val planId: String) : StoreIntent
    data class RestorePurchases(val activity: Activity) : StoreIntent
    data object Retry : StoreIntent
}

sealed interface StoreEffect {
    data class ShowToast(val messageRes: Int, val formatArgs: List<Any> = emptyList()) : StoreEffect
    data object NavigateBack : StoreEffect
}
