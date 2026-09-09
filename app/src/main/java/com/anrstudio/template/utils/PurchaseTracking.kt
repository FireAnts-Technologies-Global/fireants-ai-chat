package com.pegas.aura.aigirlfriend.soul.utils

import android.os.Bundle
import com.pegas.aura.aigirlfriend.soul.domain.model.billing.VipProduct
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackground
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinPackage
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.QuickPrompt

object PurchaseTracking {
    private const val SCREEN = "screen"
    private const val PRODUCT_ID = "product_id"
    private const val PACKAGE_ID = "package_id"
    private const val PLAN_ID = "plan_id"
    private const val BACKGROUND_ID = "background_id"
    private const val ACTION_ID = "action_id"
    private const val CHARACTER_ID = "character_id"
    private const val COIN_COST = "coin_cost"
    private const val COIN_AMOUNT = "coin_amount"
    private const val BONUS_COINS = "bonus_coins"
    private const val TOTAL_COINS = "total_coins"
    private const val COIN_BALANCE = "coin_balance"
    private const val PERIOD = "period"
    private const val ERROR_KEY = "error_key"
    private const val ERROR_CANCELLED = "error_cancelled"

    fun coinIapIntent(screen: String, pkg: CoinPackage, coinBalance: Int) {
        FireAntsTrackingHelper.logEvent(
            "coin_iap_intent",
            Bundle().apply {
                putString(SCREEN, screen)
                putString(PRODUCT_ID, pkg.storeProductId)
                putString(PACKAGE_ID, pkg.id)
                putInt(COIN_AMOUNT, pkg.coinAmount)
                putInt(BONUS_COINS, pkg.bonusCoins)
                putInt(TOTAL_COINS, pkg.totalCoins)
                putInt(COIN_BALANCE, coinBalance)
            }
        )
    }

    fun coinIapSuccess(
        screen: String,
        storeProductId: String,
        initialBalance: Int,
        newBalance: Int
    ) {
        FireAntsTrackingHelper.logEvent(
            "coin_iap_success",
            Bundle().apply {
                putString(SCREEN, screen)
                putString(PRODUCT_ID, storeProductId)
                putInt("initial_balance", initialBalance)
                putInt(COIN_BALANCE, newBalance)
            }
        )
    }

    fun coinIapFail(
        screen: String,
        storeProductId: String,
        error: PublicError,
        cancelled: Boolean = false
    ) {
        FireAntsTrackingHelper.logEvent(
            if (cancelled) "coin_iap_cancel" else "coin_iap_fail",
            failureParams(screen, storeProductId, error)
        )
    }

    fun vipIapIntent(screen: String, plan: VipProduct) {
        FireAntsTrackingHelper.logEvent(
            "vip_iap_intent",
            Bundle().apply {
                putString(SCREEN, screen)
                putString(PRODUCT_ID, plan.storeProductId)
                putString(PLAN_ID, plan.id)
                putString(PERIOD, plan.period)
            }
        )
    }

    fun vipIapSuccess(screen: String, storeProductId: String) {
        FireAntsTrackingHelper.logEvent(
            "vip_iap_success",
            Bundle().apply {
                putString(SCREEN, screen)
                putString(PRODUCT_ID, storeProductId)
            }
        )
    }

    fun vipIapFail(
        screen: String,
        storeProductId: String,
        error: PublicError,
        cancelled: Boolean = false
    ) {
        FireAntsTrackingHelper.logEvent(
            if (cancelled) "vip_iap_cancel" else "vip_iap_fail",
            failureParams(screen, storeProductId, error)
        )
    }

    fun backgroundPurchaseIntent(
        screen: String,
        characterId: String,
        background: CharacterBackground,
        coinBalance: Int
    ) {
        FireAntsTrackingHelper.logEvent(
            "background_purchase_intent",
            backgroundParams(screen, characterId, background, coinBalance)
        )
    }

    fun backgroundPurchaseSuccess(
        screen: String,
        characterId: String,
        background: CharacterBackground,
        coinBalance: Int
    ) {
        FireAntsTrackingHelper.logEvent(
            "background_purchase_success",
            backgroundParams(screen, characterId, background, coinBalance)
        )
    }

    fun backgroundPurchaseFail(
        screen: String,
        characterId: String,
        background: CharacterBackground,
        coinBalance: Int,
        error: PublicError
    ) {
        FireAntsTrackingHelper.logEvent(
            "background_purchase_fail",
            backgroundParams(screen, characterId, background, coinBalance).apply {
                putString(ERROR_KEY, error.messageKey.name)
                putBoolean(ERROR_CANCELLED, error.isUserCancellation)
            }
        )
    }

    fun chatActionIntent(
        screen: String,
        characterId: String?,
        prompt: QuickPrompt,
        coinBalance: Int
    ) {
        FireAntsTrackingHelper.logEvent(
            "chat_action_intent",
            actionParams(screen, characterId, prompt, coinBalance)
        )
    }

    fun chatActionSuccess(
        screen: String,
        characterId: String?,
        prompt: QuickPrompt?,
        promptId: String,
        coinBalance: Int
    ) {
        FireAntsTrackingHelper.logEvent(
            "chat_action_success",
            actionParams(screen, characterId, prompt, promptId, coinBalance)
        )
    }

    fun chatActionFail(
        screen: String,
        characterId: String?,
        prompt: QuickPrompt?,
        promptId: String,
        coinBalance: Int,
        error: PublicError
    ) {
        FireAntsTrackingHelper.logEvent(
            "chat_action_fail",
            actionParams(screen, characterId, prompt, promptId, coinBalance).apply {
                putString(ERROR_KEY, error.messageKey.name)
                putBoolean(ERROR_CANCELLED, error.isUserCancellation)
            }
        )
    }

    private fun backgroundParams(
        screen: String,
        characterId: String,
        background: CharacterBackground,
        coinBalance: Int
    ) = Bundle().apply {
        putString(SCREEN, screen)
        putString(CHARACTER_ID, characterId)
        putString(BACKGROUND_ID, background.id)
        putInt(COIN_COST, background.priceCoins)
        putInt(COIN_BALANCE, coinBalance)
    }

    private fun actionParams(
        screen: String,
        characterId: String?,
        prompt: QuickPrompt,
        coinBalance: Int
    ) = actionParams(screen, characterId, prompt, prompt.id, coinBalance)

    private fun actionParams(
        screen: String,
        characterId: String?,
        prompt: QuickPrompt?,
        promptId: String,
        coinBalance: Int
    ) = Bundle().apply {
        putString(SCREEN, screen)
        putString(CHARACTER_ID, characterId.orEmpty())
        putString(ACTION_ID, promptId)
        putInt(COIN_COST, prompt?.coinCost ?: 0)
        putInt(COIN_BALANCE, coinBalance)
    }

    private fun failureParams(screen: String, storeProductId: String, error: PublicError) =
        Bundle().apply {
            putString(SCREEN, screen)
            putString(PRODUCT_ID, storeProductId)
            putString(ERROR_KEY, error.messageKey.name)
            putBoolean(ERROR_CANCELLED, error.isUserCancellation)
        }
}
