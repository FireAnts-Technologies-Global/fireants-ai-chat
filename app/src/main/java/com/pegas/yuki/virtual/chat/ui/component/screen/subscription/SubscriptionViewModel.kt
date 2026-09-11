package com.pegas.yuki.virtual.chat.ui.component.screen.subscription

import android.app.Activity
import android.content.Context
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.billing.VipProduct
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.revenuecat.RevenueCatStoreProduct
import com.pegas.yuki.virtual.chat.domain.repository.BillingRepository
import com.pegas.yuki.virtual.chat.domain.repository.RevenueCatRepository
import com.pegas.yuki.virtual.chat.domain.usecase.billing.GetBillingStatusUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.billing.GetVipProductsUseCase
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseComposeViewModel
import com.pegas.yuki.virtual.chat.ui.billing.BillingPurchaseCoordinator
import com.pegas.yuki.virtual.chat.ui.billing.PurchaseFlowResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
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
        val rcProducts = cachedOfferings.flatMap { it.packages }.map { it.product }

        val cachedBilling = billingRepository.getCachedBillingStatus()
        val jsonStr = cachedBilling?.vip?.json
        val isVipActive =
            jsonStr != null && (jsonStr.contains("\"active\":true") || jsonStr.contains("\"active\": true"))
        val regex = Regex("\"productId\"\\s*:\\s*\"([^\"]+)\"")
        val activePlanStoreProductId = if (isVipActive) {
            jsonStr?.let { regex.find(it) }?.groupValues?.getOrNull(1)
        } else null

        val vipUiModels = mapVipProductsToUiModels(
            vips = cachedVips,
            rcProducts = rcProducts,
            activePlanStoreProductId = activePlanStoreProductId
        )
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
            var rcProducts = emptyList<RevenueCatStoreProduct>()
            when (val offeringsResult = revenueCatRepository.getOfferings(forceRefresh = true)) {
                is AppResult.Success -> {
                    rcProducts = offeringsResult.data.flatMap { it.packages }.map { it.product }
                }

                is AppResult.Failure -> {

                }
            }

            var activePlanStoreProductId: String? = null
            when (val billingResult = getBillingStatusUseCase(forceRefresh = true)) {
                is AppResult.Success -> {
                    val jsonStr = billingResult.data.vip?.json
                    val isVipActive =
                        jsonStr != null && (jsonStr.contains("\"active\":true") || jsonStr.contains(
                            "\"active\": true"
                        ))
                    val regex = Regex("\"productId\"\\s*:\\s*\"([^\"]+)\"")
                    activePlanStoreProductId = if (isVipActive) {
                        jsonStr?.let { regex.find(it) }?.groupValues?.getOrNull(1)
                    } else null
                }

                is AppResult.Failure -> {

                }
            }

            when (val vipResult = getVipProductsUseCase(forceRefresh = true)) {
                is AppResult.Success -> {
                    loadedVipProducts = vipResult.data
                    val vipUiModels = mapVipProductsToUiModels(
                        vips = vipResult.data,
                        rcProducts = rcProducts,
                        activePlanStoreProductId = activePlanStoreProductId
                    )
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

    private fun mapVipProductsToUiModels(
        vips: List<VipProduct>,
        rcProducts: List<RevenueCatStoreProduct>,
        activePlanStoreProductId: String?
    ): List<SubscriptionPlanUiModel> {
        fun findRcProduct(storeProductId: String): RevenueCatStoreProduct? {
            if (storeProductId.isBlank()) return null
            rcProducts.firstOrNull { it.id == storeProductId }?.let { return it }
            return rcProducts.firstOrNull {
                it.id.startsWith("$storeProductId:") ||
                        storeProductId.startsWith("${it.id}:") ||
                        (storeProductId.contains(":") && it.id == storeProductId.substringAfter(":")) ||
                        (storeProductId.contains(":") && it.id == storeProductId.substringBefore(":"))
            }
        }

        fun isAnnualPlan(vip: VipProduct): Boolean {
            return vip.period.contains("year", ignoreCase = true) ||
                    vip.code.contains("year", ignoreCase = true) ||
                    vip.code.contains("annual", ignoreCase = true)
        }

        val monthlyVip = vips.firstOrNull { !isAnnualPlan(it) }
        val annualVip = vips.firstOrNull { isAnnualPlan(it) }

        val monthlyRc = monthlyVip?.let { findRcProduct(it.storeProductId) }
        val annualRc = annualVip?.let { findRcProduct(it.storeProductId) }

        val dynamicSavePercentageText = when {
            !annualVip?.badge.isNullOrBlank() -> {
                val b = annualVip!!.badge!!.trim()
                if (b.startsWith("SAVE", ignoreCase = true)) b else context.getString(R.string.sub_save_badge_format, b)
            }

            monthlyRc != null && annualRc != null && monthlyRc.priceAmountMicros > 0 && annualRc.priceAmountMicros > 0 -> {
                val monthlyTotal = monthlyRc.priceAmountMicros * 12
                val annualTotal = annualRc.priceAmountMicros
                if (monthlyTotal > annualTotal) {
                    val percent =
                        (((monthlyTotal - annualTotal).toDouble() / monthlyTotal) * 100).toInt()
                    if (percent > 0) context.getString(R.string.sub_save_percent_format, percent) else null
                } else null
            }

            else -> null
        }

        val dynamicAnnualBreakdownText = if (annualRc != null && annualRc.priceAmountMicros > 0) {
            val monthlyEquivalentMicros = annualRc.priceAmountMicros / 12
            val formatted = try {
                val format = java.text.NumberFormat.getCurrencyInstance()
                if (annualRc.currencyCode.isNotBlank()) {
                    format.currency = java.util.Currency.getInstance(annualRc.currencyCode)
                }
                format.maximumFractionDigits = 2
                format.format(monthlyEquivalentMicros / 1_000_000.0)
            } catch (e: Exception) {
                val amount = monthlyEquivalentMicros / 1_000_000.0
                if (amount % 1.0 == 0.0) {
                    "%.0f %s".format(amount, annualRc.currencyCode)
                } else {
                    "%.2f %s".format(amount, annualRc.currencyCode)
                }
            }
            context.getString(R.string.sub_billed_annually_format, formatted)
        } else if (annualVip != null) {
            context.getString(R.string.sub_billed_annually_fallback, annualVip.displayName)
        } else null

        val dynamicMonthlyBreakdownText =
            if (monthlyRc != null && monthlyRc.priceAmountMicros > 0) {
                val formatted = try {
                    val format = java.text.NumberFormat.getCurrencyInstance()
                    if (monthlyRc.currencyCode.isNotBlank()) {
                        format.currency = java.util.Currency.getInstance(monthlyRc.currencyCode)
                    }
                    format.maximumFractionDigits = 2
                    format.format(monthlyRc.priceAmountMicros / 1_000_000.0)
                } catch (e: Exception) {
                    monthlyRc.priceFormatted
                }
                context.getString(R.string.sub_billed_monthly_format, formatted)
            } else if (monthlyVip != null) {
                context.getString(R.string.sub_billed_monthly_fallback, monthlyVip.displayName)
            } else null

        return vips.map { vip ->
            val isAnnual = isAnnualPlan(vip)
            val rcProduct = findRcProduct(vip.storeProductId)
            val realPrice = rcProduct?.priceFormatted
            val isActive = activePlanStoreProductId != null &&
                    (vip.storeProductId == activePlanStoreProductId ||
                            vip.storeProductId.startsWith("$activePlanStoreProductId:") ||
                            activePlanStoreProductId.startsWith("${vip.storeProductId}:"))
            val breakdownText =
                if (isAnnual) dynamicAnnualBreakdownText else dynamicMonthlyBreakdownText

            SubscriptionPlanUiModel(
                id = vip.id,
                storeProductId = vip.storeProductId,
                title = vip.displayName.takeIf { it.isNotBlank() },
                isAnnual = isAnnual,
                priceText = realPrice ?: vip.displayName,
                dailyBonusGems = if (isAnnual) "750" else "500",
                isActivePlan = isActive,
                savePercentageText = if (isAnnual) dynamicSavePercentageText else null,
                monthlyBreakdownText = breakdownText
            )
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
