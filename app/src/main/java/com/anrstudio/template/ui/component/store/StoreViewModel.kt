package com.anrstudio.template.ui.component.store

import android.app.Activity
import com.anrstudio.ads.billing.AppPurchase
import com.anrstudio.template.domain.model.billing.VipProduct
import com.anrstudio.template.domain.model.coins.CoinPackage
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.repository.RevenueCatRepository
import com.anrstudio.template.domain.usecase.auth.GetAuthUserFlowUseCase
import com.anrstudio.template.domain.usecase.billing.GetBillingStatusUseCase
import com.anrstudio.template.domain.usecase.billing.GetVipProductsUseCase
import com.anrstudio.template.domain.usecase.coins.GetCoinBalanceUseCase
import com.anrstudio.template.domain.usecase.coins.GetCoinPackagesUseCase
import com.anrstudio.template.domain.usecase.revenuecat.RestoreRevenueCatPurchasesUseCase
import com.anrstudio.template.ui.bases.compose.mvi.BaseComposeViewModel
import com.anrstudio.template.utils.PurchaseTracking
import com.pegas.aura.aigirlfriend.soul.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getAuthUserFlowUseCase: GetAuthUserFlowUseCase,
    private val getCoinPackagesUseCase: GetCoinPackagesUseCase,
    private val getVipProductsUseCase: GetVipProductsUseCase,
    private val revenueCatRepository: RevenueCatRepository,
    private val getCoinBalanceUseCase: GetCoinBalanceUseCase,
    private val getBillingStatusUseCase: GetBillingStatusUseCase,
    private val restoreRevenueCatPurchasesUseCase: RestoreRevenueCatPurchasesUseCase
) : BaseComposeViewModel<StoreUiState, StoreIntent, StoreEffect>(StoreUiState()) {

    private var loadedCoinPackages: List<CoinPackage> = emptyList()
    private var loadedVipProducts: List<VipProduct> = emptyList()

    init {
        launchIO {
            getAuthUserFlowUseCase().collect { user ->
                updateState { copy(coinBalance = user?.coinBalance ?: 0) }
            }
        }
    }

    override fun handleIntent(intent: StoreIntent) {
        when (intent) {
            StoreIntent.Initialize -> loadStoreData()
            is StoreIntent.PurchaseGemPackage -> {
                val pkg = loadedCoinPackages.firstOrNull { it.id == intent.packageId }
                if (pkg == null) {
                    sendEffect(StoreEffect.ShowToast(R.string.store_product_not_found))
                    return
                }
                updateState { copy(selectedPackageId = pkg.id) }
                performCoinPurchase(intent.activity, pkg)
            }

            is StoreIntent.PurchaseMembershipPlan -> {
                val plan = loadedVipProducts.firstOrNull { it.id == intent.planId }
                if (plan == null) {
                    sendEffect(StoreEffect.ShowToast(R.string.store_product_not_found))
                    return
                }
                updateState { copy(selectedPlanId = plan.id) }
                performVipPurchase(intent.activity, plan)
            }

            is StoreIntent.RestorePurchases -> {
                restorePurchases()
            }

            StoreIntent.Retry -> loadStoreData()
        }
    }

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private fun performCoinPurchase(activity: Activity, pkg: CoinPackage) {
        if (currentState.isPurchasing) return
        updateState { copy(isPurchasing = true) }
        PurchaseTracking.coinIapIntent("StoreScreen", pkg, currentState.coinBalance)

        launchIO {
            revenueCatRepository.syncUserIdentity()

            when (val result = revenueCatRepository.purchase(activity, pkg.storeProductId)) {
                is AppResult.Success -> {
                    val credited = pollCoinBalance(pkg.storeProductId)
                    if (!credited) {
                        sendEffect(StoreEffect.ShowToast(R.string.store_purchase_processing))
                    }
                }

                is AppResult.Failure -> {
                    if (!result.error.isUserCancellation) {
                        PurchaseTracking.coinIapFail(
                            "StoreScreen",
                            pkg.storeProductId,
                            result.error
                        )
                        updateState { copy(error = result.error) }
                    } else {
                        PurchaseTracking.coinIapFail(
                            "StoreScreen",
                            pkg.storeProductId,
                            result.error,
                            cancelled = true
                        )
                        sendEffect(StoreEffect.ShowToast(R.string.store_purchase_cancelled))
                    }
                }
            }
            updateState { copy(isPurchasing = false) }
        }
    }

    private fun performVipPurchase(activity: Activity, plan: VipProduct) {
        if (currentState.isPurchasing) return
        updateState { copy(isPurchasing = true) }
        PurchaseTracking.vipIapIntent("StoreScreen", plan)

        launchIO {
            revenueCatRepository.syncUserIdentity()

            when (val result = revenueCatRepository.purchase(activity, plan.storeProductId)) {
                is AppResult.Success -> {
                    val activated = pollBillingStatus(plan.storeProductId)
                    if (!activated) {
                        sendEffect(StoreEffect.ShowToast(R.string.store_vip_processing))
                    }
                }

                is AppResult.Failure -> {
                    if (!result.error.isUserCancellation) {
                        PurchaseTracking.vipIapFail(
                            "StoreScreen",
                            plan.storeProductId,
                            result.error
                        )
                        updateState { copy(error = result.error) }
                    } else {
                        PurchaseTracking.vipIapFail(
                            "StoreScreen",
                            plan.storeProductId,
                            result.error,
                            cancelled = true
                        )
                        sendEffect(StoreEffect.ShowToast(R.string.store_purchase_cancelled))
                    }
                }
            }
            updateState { copy(isPurchasing = false) }
        }
    }

    private fun restorePurchases() {
        if (currentState.isPurchasing) return
        updateState { copy(isPurchasing = true) }
        sendEffect(StoreEffect.ShowToast(R.string.store_restoring_purchases))

        launchIO {
            revenueCatRepository.syncUserIdentity()
            when (val result = restoreRevenueCatPurchasesUseCase()) {
                is AppResult.Success -> {
                    val activated = pollBillingStatus()
                    sendEffect(
                        StoreEffect.ShowToast(
                            if (activated) R.string.store_restore_success else R.string.store_vip_processing
                        )
                    )
                }

                is AppResult.Failure -> {
                    updateState { copy(error = result.error) }
                }
            }
            updateState { copy(isPurchasing = false) }
        }
    }

    private suspend fun pollCoinBalance(
        storeProductId: String,
        maxAttempts: Int = 6,
        delayMs: Long = 2000
    ): Boolean {
        val initialBalance = currentState.coinBalance
        for (attempt in 1..maxAttempts) {
            when (val result = getCoinBalanceUseCase()) {
                is AppResult.Success -> {
                    val newBalance = result.data.balance
                    if (newBalance > initialBalance) {
                        PurchaseTracking.coinIapSuccess(
                            "StoreScreen",
                            storeProductId,
                            initialBalance,
                            newBalance
                        )
                        sendEffect(
                            StoreEffect.ShowToast(
                                R.string.store_coins_credited,
                                listOf(newBalance)
                            )
                        )
                        return true
                    }
                }

                is AppResult.Failure -> Unit
            }
            if (attempt < maxAttempts) {
                delay(delayMs)
            }
        }
        return false
    }

    private suspend fun pollBillingStatus(
        storeProductId: String? = null,
        maxAttempts: Int = 6,
        delayMs: Long = 2000
    ): Boolean {
        for (attempt in 1..maxAttempts) {
            when (val result = getBillingStatusUseCase()) {
                is AppResult.Success -> {
                    val jsonStr = result.data.vip?.json
                    if (jsonStr != null && jsonStr.contains("\"active\":true")) {
                        AppPurchase.getInstance().setPurchase(true)
                        storeProductId?.let {
                            PurchaseTracking.vipIapSuccess("StoreScreen", it)
                        }
                        sendEffect(StoreEffect.ShowToast(R.string.store_vip_activated))
                        return true
                    }
                }

                is AppResult.Failure -> Unit
            }
            if (attempt < maxAttempts) {
                delay(delayMs)
            }
        }
        return false
    }

    private fun loadStoreData() {
        if (currentState.isLoading) return
        updateState { copy(isLoading = true, error = null) }

        launchIO {
            var rcPrices = emptyMap<String, String>()
            when (val offeringsResult = revenueCatRepository.getOfferings()) {
                is AppResult.Success -> {
                    rcPrices = offeringsResult.data.flatMap { it.packages }
                        .associate { it.product.id to it.product.priceFormatted }
                    Timber.d("RevenueCat loaded prices: %s", rcPrices)
                }

                is AppResult.Failure -> {
                    Timber.w("RevenueCat getOfferings failed: %s", offeringsResult.error)
                }
            }

            fun findPriceForProduct(storeProductId: String): String? {
                if (storeProductId.isBlank()) return null
                rcPrices[storeProductId]?.let { return it }
                return rcPrices.entries.firstOrNull { (key, _) ->
                    key.startsWith("$storeProductId:") || storeProductId.startsWith("$key:")
                }?.value
            }

            var coinUiModels = emptyList<CoinPackageUiModel>()
            when (val packagesResult = getCoinPackagesUseCase()) {
                is AppResult.Success -> {
                    loadedCoinPackages = packagesResult.data
                    coinUiModels = packagesResult.data.map {
                        val pct =
                            if (it.coinAmount > 0) (it.bonusCoins * 100 / it.coinAmount) else 0
                        val computedBadge = if (!it.badge.isNullOrBlank()) {
                            it.badge
                        } else if (it.bonusCoins > 0) {
                            "+${it.bonusCoins} (${pct}%)"
                        } else {
                            null
                        }
                        val realPrice = findPriceForProduct(it.storeProductId)
                        CoinPackageUiModel(
                            id = it.id,
                            coinAmount = it.totalCoins,
                            displayName = it.displayName,
                            bonusBadgeText = computedBadge,
                            priceText = realPrice ?: "N/A"
                        )
                    }
                }

                is AppResult.Failure -> {
                    updateState { copy(isLoading = false, error = packagesResult.error) }
                    return@launchIO
                }
            }

            var vipUiModels = emptyList<MembershipPlanUiModel>()
            when (val vipResult = getVipProductsUseCase()) {
                is AppResult.Success -> {
                    loadedVipProducts = vipResult.data
                    vipUiModels = vipResult.data.map {
                        val isAnnual = it.period.contains(
                            "year",
                            ignoreCase = true
                        ) || it.code.contains(
                            "year",
                            ignoreCase = true
                        ) || it.code.contains("annual", ignoreCase = true)
                        val realPrice = findPriceForProduct(it.storeProductId)
                        MembershipPlanUiModel(
                            id = it.id,
                            title = it.displayName,
                            price = realPrice ?: "N/A",
                            durationLabelRes = if (isAnnual) R.string.store_duration_year else R.string.store_duration_month,
                            descriptionRes = if (isAnnual) R.string.store_vip_desc_year else R.string.store_vip_desc_month,
                            benefitsRes = if (isAnnual) listOf(
                                R.string.store_vip_benefit_2,
                                R.string.store_vip_benefit_3
                            ) else listOf(
                                R.string.store_vip_benefit_1
                            ),
                            footerTextRes = if (isAnnual) R.string.store_vip_footer_year else R.string.store_vip_footer_month,
                            isBestValue = it.badge != null || isAnnual
                        )
                    }
                }

                is AppResult.Failure -> {
                    updateState { copy(isLoading = false, error = vipResult.error) }
                    return@launchIO
                }
            }

            val defaultPlanId = vipUiModels.firstOrNull { it.isBestValue }?.id

            updateState {
                copy(
                    isLoading = false,
                    coinPackages = coinUiModels,
                    membershipPlans = vipUiModels,
                    selectedPlanId = defaultPlanId
                )
            }
        }
    }
}
