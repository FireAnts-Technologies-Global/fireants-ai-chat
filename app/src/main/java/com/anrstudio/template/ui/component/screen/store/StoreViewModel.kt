package com.pegas.aura.aigirlfriend.soul.ui.component.screen.store

import android.app.Activity
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.billing.VipProduct
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinPackage
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.usecase.auth.GetAuthUserFlowUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.billing.GetVipProductsUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.coins.GetCoinPackagesUseCase
import com.pegas.aura.aigirlfriend.soul.domain.repository.BillingRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.CoinsRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.RevenueCatRepository
import com.pegas.aura.aigirlfriend.soul.ui.billing.BillingPurchaseCoordinator
import com.pegas.aura.aigirlfriend.soul.ui.billing.PurchaseFlowResult
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getAuthUserFlowUseCase: GetAuthUserFlowUseCase,
    private val getCoinPackagesUseCase: GetCoinPackagesUseCase,
    private val getVipProductsUseCase: GetVipProductsUseCase,
    private val coinsRepository: CoinsRepository,
    private val billingRepository: BillingRepository,
    private val revenueCatRepository: RevenueCatRepository,
    private val billingPurchaseCoordinator: BillingPurchaseCoordinator
) : BaseComposeViewModel<StoreUiState, StoreIntent, StoreEffect>(StoreUiState()) {

    private var loadedCoinPackages: List<CoinPackage> = emptyList()
    private var loadedVipProducts: List<VipProduct> = emptyList()

    init {
        tryLoadFromCache()
        launchIO {
            getAuthUserFlowUseCase().collect { user ->
                updateState { copy(coinBalance = user?.coinBalance ?: 0) }
            }
        }
    }

    private fun tryLoadFromCache() {
        val cachedPackages = coinsRepository.getCachedPackages()
        val cachedVips = billingRepository.getCachedVipProducts()
        if (cachedPackages == null && cachedVips == null) return

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

        val coinUiModels = cachedPackages?.map {
            val pct = if (it.coinAmount > 0) (it.bonusCoins * 100 / it.coinAmount) else 0
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
        } ?: emptyList()
        if (coinUiModels.isNotEmpty()) {
            loadedCoinPackages = cachedPackages ?: emptyList()
        }

        val vipUiModels = cachedVips?.map {
            val isAnnual = it.period.contains("year", ignoreCase = true) || it.code.contains("year", ignoreCase = true) || it.code.contains("annual", ignoreCase = true)
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
        } ?: emptyList()
        if (vipUiModels.isNotEmpty()) {
            loadedVipProducts = cachedVips ?: emptyList()
        }

        val defaultSelectedVip = vipUiModels.firstOrNull { it.isBestValue }?.id ?: vipUiModels.firstOrNull()?.id
        updateState {
            copy(
                isLoading = false,
                coinPackages = coinUiModels.ifEmpty { this.coinPackages },
                membershipPlans = vipUiModels.ifEmpty { this.membershipPlans },
                selectedPackageId = coinUiModels.firstOrNull()?.id ?: this.selectedPackageId,
                selectedPlanId = defaultSelectedVip ?: this.selectedPlanId
            )
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

        launchIO {
            handlePurchaseResult(
                billingPurchaseCoordinator.purchaseCoins(
                    activity = activity,
                    screen = SCREEN_NAME,
                    coinPackage = pkg,
                    currentBalance = currentState.coinBalance
                )
            )
            updateState { copy(isPurchasing = false) }
        }
    }

    private fun performVipPurchase(activity: Activity, plan: VipProduct) {
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
        sendEffect(StoreEffect.ShowToast(R.string.store_restoring_purchases))

        launchIO {
            handlePurchaseResult(billingPurchaseCoordinator.restoreVipPurchases())
            updateState { copy(isPurchasing = false) }
        }
    }

    private fun handlePurchaseResult(result: PurchaseFlowResult) {
        when (result) {
            is PurchaseFlowResult.Error -> updateState { copy(error = result.error) }
            is PurchaseFlowResult.Toast -> sendEffect(
                StoreEffect.ShowToast(result.messageRes, result.formatArgs)
            )
            PurchaseFlowResult.VipActivated -> sendEffect(
                StoreEffect.ShowToast(R.string.store_vip_activated)
            )
            PurchaseFlowResult.RestoreSuccess -> sendEffect(
                StoreEffect.ShowToast(R.string.store_restore_success)
            )
        }
    }

    private fun loadStoreData() {
        if (currentState.isLoading) return
        val hasData = currentState.coinPackages.isNotEmpty() || currentState.membershipPlans.isNotEmpty()
        if (!hasData) {
            updateState { copy(isLoading = true, error = null) }
        }

        launchIO {
            var rcPrices = emptyMap<String, String>()
            when (val offeringsResult = revenueCatRepository.getOfferings(forceRefresh = true)) {
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
            when (val packagesResult = getCoinPackagesUseCase(forceRefresh = true)) {
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
                    if (!hasData) {
                        updateState { copy(isLoading = false, error = packagesResult.error) }
                    } else {
                        updateState { copy(isLoading = false) }
                    }
                    return@launchIO
                }
            }

            var vipUiModels = emptyList<MembershipPlanUiModel>()
            when (val vipResult = getVipProductsUseCase(forceRefresh = true)) {
                is AppResult.Success -> {
                    loadedVipProducts = vipResult.data
                    vipUiModels = vipResult.data.map {
                        val isAnnual = it.period.contains("year", ignoreCase = true) || it.code.contains("year", ignoreCase = true) || it.code.contains("annual", ignoreCase = true)
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
                    if (!hasData) {
                        updateState { copy(isLoading = false, error = vipResult.error) }
                    } else {
                        updateState { copy(isLoading = false) }
                    }
                    return@launchIO
                }
            }

            val defaultPlanId = vipUiModels.firstOrNull { it.isBestValue }?.id ?: vipUiModels.firstOrNull()?.id

            updateState {
                copy(
                    isLoading = false,
                    coinPackages = coinUiModels,
                    membershipPlans = vipUiModels,
                    selectedPackageId = currentState.selectedPackageId ?: coinUiModels.firstOrNull()?.id,
                    selectedPlanId = currentState.selectedPlanId ?: defaultPlanId
                )
            }
        }
    }

    private companion object {
        const val SCREEN_NAME = "StoreScreen"
    }
}
