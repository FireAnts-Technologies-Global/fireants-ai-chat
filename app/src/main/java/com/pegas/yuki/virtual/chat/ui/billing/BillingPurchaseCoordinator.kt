package com.pegas.yuki.virtual.chat.ui.billing

import android.app.Activity
import com.fireants.adsdk.billing.AppPurchase
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.billing.VipProduct
import com.pegas.yuki.virtual.chat.domain.model.coins.CoinPackage
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.domain.repository.RevenueCatRepository
import com.pegas.yuki.virtual.chat.domain.usecase.billing.GetBillingStatusUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.coins.GetCoinBalanceUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.revenuecat.RestoreRevenueCatPurchasesUseCase
import com.pegas.yuki.virtual.chat.utils.PurchaseTracking
import kotlinx.coroutines.delay
import javax.inject.Inject

sealed interface PurchaseFlowResult {
    data class Toast(val messageRes: Int, val formatArgs: List<Any> = emptyList()) :
        PurchaseFlowResult

    data class Error(val error: PublicError) : PurchaseFlowResult
    data object VipActivated : PurchaseFlowResult
    data object RestoreSuccess : PurchaseFlowResult
}

class BillingPurchaseCoordinator @Inject constructor(
    private val revenueCatRepository: RevenueCatRepository,
    private val getCoinBalanceUseCase: GetCoinBalanceUseCase,
    private val getBillingStatusUseCase: GetBillingStatusUseCase,
    private val restoreRevenueCatPurchasesUseCase: RestoreRevenueCatPurchasesUseCase
) {
    suspend fun purchaseCoins(
        activity: Activity,
        screen: String,
        coinPackage: CoinPackage,
        currentBalance: Int
    ): PurchaseFlowResult {
        PurchaseTracking.coinIapIntent(screen, coinPackage, currentBalance)
        revenueCatRepository.syncUserIdentity()

        return when (val result = revenueCatRepository.purchase(activity, coinPackage.storeProductId)) {
            is AppResult.Success -> {
                val credited = pollCoinBalance(screen, coinPackage.storeProductId, currentBalance)
                if (credited != null) {
                    PurchaseFlowResult.Toast(R.string.store_coins_credited, listOf(credited))
                } else {
                    PurchaseFlowResult.Toast(R.string.store_purchase_processing)
                }
            }

            is AppResult.Failure -> {
                PurchaseTracking.coinIapFail(
                    screen,
                    coinPackage.storeProductId,
                    result.error,
                    cancelled = result.error.isUserCancellation
                )
                if (result.error.isUserCancellation) {
                    PurchaseFlowResult.Toast(R.string.store_purchase_cancelled)
                } else {
                    PurchaseFlowResult.Error(result.error)
                }
            }
        }
    }

    suspend fun purchaseVip(
        activity: Activity,
        screen: String,
        plan: VipProduct
    ): PurchaseFlowResult {
        PurchaseTracking.vipIapIntent(screen, plan)
        revenueCatRepository.syncUserIdentity()

        return when (val result = revenueCatRepository.purchase(activity, plan.storeProductId)) {
            is AppResult.Success -> {
                if (pollBillingStatus(screen, plan.storeProductId)) {
                    PurchaseFlowResult.VipActivated
                } else {
                    PurchaseFlowResult.Toast(R.string.store_vip_processing)
                }
            }

            is AppResult.Failure -> {
                PurchaseTracking.vipIapFail(
                    screen,
                    plan.storeProductId,
                    result.error,
                    cancelled = result.error.isUserCancellation
                )
                if (result.error.isUserCancellation) {
                    PurchaseFlowResult.Toast(R.string.store_purchase_cancelled)
                } else {
                    PurchaseFlowResult.Error(result.error)
                }
            }
        }
    }

    suspend fun restoreVipPurchases(): PurchaseFlowResult {
        revenueCatRepository.syncUserIdentity()
        return when (val result = restoreRevenueCatPurchasesUseCase()) {
            is AppResult.Success -> {
                if (pollBillingStatus(screen = null, storeProductId = null)) {
                    PurchaseFlowResult.RestoreSuccess
                } else {
                    PurchaseFlowResult.Toast(R.string.store_vip_processing)
                }
            }

            is AppResult.Failure -> PurchaseFlowResult.Error(result.error)
        }
    }

    private suspend fun pollCoinBalance(
        screen: String,
        storeProductId: String,
        initialBalance: Int,
        maxAttempts: Int = 6,
        delayMs: Long = 2_000
    ): Int? {
        for (attempt in 1..maxAttempts) {
            when (val result = getCoinBalanceUseCase()) {
                is AppResult.Success -> {
                    val newBalance = result.data.balance
                    if (newBalance > initialBalance) {
                        PurchaseTracking.coinIapSuccess(
                            screen,
                            storeProductId,
                            initialBalance,
                            newBalance
                        )
                        return newBalance
                    }
                }

                is AppResult.Failure -> Unit
            }
            if (attempt < maxAttempts) delay(delayMs)
        }
        return null
    }

    private suspend fun pollBillingStatus(
        screen: String?,
        storeProductId: String?,
        maxAttempts: Int = 6,
        delayMs: Long = 2_000
    ): Boolean {
        for (attempt in 1..maxAttempts) {
            when (val result = getBillingStatusUseCase(forceRefresh = true)) {
                is AppResult.Success -> {
                    val jsonStr = result.data.vip?.json
                    if (jsonStr != null && jsonStr.contains("\"active\":true")) {
                        AppPurchase.getInstance().setPurchase(true)
                        if (screen != null && storeProductId != null) {
                            PurchaseTracking.vipIapSuccess(screen, storeProductId)
                        }
                        return true
                    }
                }

                is AppResult.Failure -> Unit
            }
            if (attempt < maxAttempts) delay(delayMs)
        }
        return false
    }
}
