package com.pegas.aura.aigirlfriend.soul.ui.component.screen.subscription

import android.app.Activity
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.billing.VipProduct
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.BillingRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.RevenueCatRepository
import com.pegas.aura.aigirlfriend.soul.domain.usecase.billing.GetBillingStatusUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.billing.GetVipProductsUseCase
import com.pegas.aura.aigirlfriend.soul.ui.billing.BillingPurchaseCoordinator
import com.pegas.aura.aigirlfriend.soul.ui.billing.PurchaseFlowResult
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val getVipProductsUseCase: GetVipProductsUseCase,
    private val billingRepository: BillingRepository,
    private val revenueCatRepository: RevenueCatRepository,
    private val getBillingStatusUseCase: GetBillingStatusUseCase,
    private val billingPurchaseCoordinator: BillingPurchaseCoordinator
) : BaseComposeViewModel<SubscriptionUiState, SubscriptionIntent, SubscriptionEffect>(
    SubscriptionUiState()
) {

    private var loadedVipProducts: List<VipProduct> = emptyList()

    init {
        tryLoadFromCache()
    }

    private fun tryLoadFromCache() {
        val cachedVips = billingRepository.getCachedVipProducts() ?: return
        loadedVipProducts = cachedVips

        val cachedOfferings = revenueCatRepository.getCachedOfferings() ?: emptyList()
        val rcPrices = cachedOfferings.flatMap { it.packages }
            .associate { it.product.id to it.product.priceFormatted }

        fun findPriceForProduct(storeProductId: String): String? {
            if (storeProductId.isBlank()) return null
            rcPrices[storeProductId]?.let { return it }
            return rcPrices.entries.firstOrNull { (key, _) ->
                key.startsWith("$storeProductId:") || storeProductId.startsWith("$key:")
            }?.value
        }

        val cachedBilling = billingRepository.getCachedBillingStatus()
        val jsonStr = cachedBilling?.vip?.json
        val regex = Regex("\"productId\"\\s*:\\s*\"([^\"]+)\"")
        val activePlanStoreProductId =
            jsonStr?.let { regex.find(it) }?.groupValues?.getOrNull(1)

        val vipUiModels = cachedVips.map {
            val isAnnual = it.period.contains(
                "year",
                ignoreCase = true
            ) || it.code.contains(
                "year",
                ignoreCase = true
            ) || it.code.contains("annual", ignoreCase = true)

            val realPrice = findPriceForProduct(it.storeProductId)
            val isActive = activePlanStoreProductId != null &&
                    (it.storeProductId == activePlanStoreProductId ||
                            it.storeProductId.startsWith("$activePlanStoreProductId:") ||
                            activePlanStoreProductId.startsWith("${it.storeProductId}:"))
            SubscriptionPlanUiModel(
                id = it.id,
                storeProductId = it.storeProductId,
                isAnnual = isAnnual,
                priceText = realPrice ?: it.displayName,
                dailyBonusGems = if (isAnnual) "750" else "500",
                isActivePlan = isActive
            )
        }
        val activeModel = vipUiModels.firstOrNull { it.isActivePlan }
        val defaultSelected = activeModel?.id
            ?: vipUiModels.find { it.isAnnual }?.id
            ?: vipUiModels.firstOrNull()?.id

        updateState {
            copy(
                isLoading = false,
                plans = vipUiModels,
                selectedPlanId = defaultSelected,
                activePlanStoreProductId = activePlanStoreProductId
            )
        }
    }

    override fun handleIntent(intent: SubscriptionIntent) {
        when (intent) {
            SubscriptionIntent.Initialize -> loadStoreData()
            is SubscriptionIntent.SelectPlan -> {
                updateState { copy(selectedPlanId = intent.planId) }
            }

            is SubscriptionIntent.PurchasePlan -> {
                val plan = loadedVipProducts.firstOrNull { it.id == intent.planId }
                if (plan == null) {
                    sendEffect(SubscriptionEffect.ShowToast(R.string.store_product_not_found))
                    return
                }
                updateState { copy(selectedPlanId = plan.id) }
                performPurchase(intent.activity, plan)
            }

            is SubscriptionIntent.RestorePurchases -> {
                restorePurchases()
            }

            SubscriptionIntent.Retry -> loadStoreData()
        }
    }

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private fun loadStoreData() {
        if (currentState.isLoading) return
        val hasData = currentState.plans.isNotEmpty()
        if (!hasData) {
            updateState { copy(isLoading = true, error = null) }
        }
        launchIO {
            var rcPrices = emptyMap<String, String>()
            when (val offeringsResult = revenueCatRepository.getOfferings(forceRefresh = true)) {
                is AppResult.Success -> {
                    rcPrices = offeringsResult.data.flatMap { it.packages }
                        .associate { it.product.id to it.product.priceFormatted }
                }

                is AppResult.Failure -> {

                }
            }

            var activePlanStoreProductId: String? = null
            when (val billingResult = getBillingStatusUseCase(forceRefresh = true)) {
                is AppResult.Success -> {
                    val jsonStr = billingResult.data.vip?.json
                    val regex = Regex("\"productId\"\\s*:\\s*\"([^\"]+)\"")
                    activePlanStoreProductId =
                        jsonStr?.let { regex.find(it) }?.groupValues?.getOrNull(1)
                }

                is AppResult.Failure -> {

                }
            }

            fun findPriceForProduct(storeProductId: String): String? {
                if (storeProductId.isBlank()) return null
                rcPrices[storeProductId]?.let { return it }
                return rcPrices.entries.firstOrNull { (key, _) ->
                    key.startsWith("$storeProductId:") || storeProductId.startsWith("$key:")
                }?.value
            }

            when (val vipResult = getVipProductsUseCase(forceRefresh = true)) {
                is AppResult.Success -> {
                    loadedVipProducts = vipResult.data
                    val vipUiModels = vipResult.data.map {
                        val isAnnual = it.period.contains(
                            "year",
                            ignoreCase = true
                        ) || it.code.contains(
                            "year",
                            ignoreCase = true
                        ) || it.code.contains("annual", ignoreCase = true)

                        val realPrice = findPriceForProduct(it.storeProductId)
                        val isActive = activePlanStoreProductId != null &&
                                (it.storeProductId == activePlanStoreProductId ||
                                        it.storeProductId.startsWith("$activePlanStoreProductId:") ||
                                        activePlanStoreProductId.startsWith("${it.storeProductId}:"))
                        SubscriptionPlanUiModel(
                            id = it.id,
                            storeProductId = it.storeProductId,
                            isAnnual = isAnnual,
                            priceText = realPrice ?: it.displayName,
                            dailyBonusGems = if (isAnnual) "750" else "500",
                            isActivePlan = isActive
                        )
                    }
                    val activeModel = vipUiModels.firstOrNull { it.isActivePlan }
                    val defaultSelected = activeModel?.id
                        ?: vipUiModels.find { it.isAnnual }?.id
                        ?: vipUiModels.firstOrNull()?.id
                    updateState {
                        copy(
                            isLoading = false,
                            plans = vipUiModels,
                            selectedPlanId = currentState.selectedPlanId ?: defaultSelected,
                            activePlanStoreProductId = activePlanStoreProductId
                        )
                    }
                }

                is AppResult.Failure -> {
                    if (!hasData) {
                        updateState { copy(isLoading = false, error = vipResult.error) }
                    } else {
                        updateState { copy(isLoading = false) }
                    }
                }
            }
        }
    }


    private fun performPurchase(activity: Activity, plan: VipProduct) {
        if (currentState.isPurchasing) return
        updateState { copy(isPurchasing = true) }

        launchIO {
            handlePurchaseResult(
                billingPurchaseCoordinator.purchaseVip(
                    activity = activity,
                    screen = SCREEN_NAME,
                    plan = plan
                )
            )
            updateState { copy(isPurchasing = false) }
        }
    }

    private fun restorePurchases() {
        if (currentState.isPurchasing) return
        updateState { copy(isPurchasing = true) }
        sendEffect(SubscriptionEffect.ShowToast(R.string.store_restoring_purchases))

        launchIO {
            handlePurchaseResult(billingPurchaseCoordinator.restoreVipPurchases())
            updateState { copy(isPurchasing = false) }
        }
    }

    private fun handlePurchaseResult(result: PurchaseFlowResult) {
        when (result) {
            is PurchaseFlowResult.Error -> updateState { copy(error = result.error) }
            is PurchaseFlowResult.Toast -> sendEffect(
                SubscriptionEffect.ShowToast(result.messageRes, result.formatArgs)
            )

            PurchaseFlowResult.VipActivated -> {
                sendEffect(SubscriptionEffect.ShowToast(R.string.store_vip_activated))
                sendEffect(SubscriptionEffect.NavigateBack)
            }
            PurchaseFlowResult.RestoreSuccess -> sendEffect(
                SubscriptionEffect.ShowToast(R.string.store_restore_success)
            )
        }
    }

    private companion object {
        const val SCREEN_NAME = "SubscriptionScreen"
    }
}
