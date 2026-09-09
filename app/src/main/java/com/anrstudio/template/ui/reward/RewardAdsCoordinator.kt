package com.pegas.aura.aigirlfriend.soul.ui.reward

import android.app.Activity
import android.content.Context
import com.fireants.adsdk.billing.AppPurchase
import com.pegas.aura.aigirlfriend.soul.ads.AdRemoteConfig
import com.pegas.aura.aigirlfriend.soul.ads.AdsManager
import com.pegas.aura.aigirlfriend.soul.ads.reward_addcoin
import com.pegas.aura.aigirlfriend.soul.ads.reward_checkin
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RewardAdsCoordinator @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun isVip(): Boolean = AppPurchase.getInstance().isPurchased(context)

    fun preloadRewardAds(
        activity: Activity,
        claimedToday: Boolean,
        adsConfigLoaded: Boolean,
        adsRemainingToday: Int,
        onAddCoinLoaded: (Boolean) -> Unit
    ) {
        preloadRewardCheckInAd(activity, claimedToday)
        preloadRewardAddCoinAd(activity, adsConfigLoaded, adsRemainingToday, onAddCoinLoaded)
    }

    fun shouldSkipCheckInAd(activity: Activity): Boolean =
        AppPurchase.getInstance().isPurchased(activity) || !AdRemoteConfig.reward_checkin.isEnable

    fun isRewardAddCoinEnabled(): Boolean = AdRemoteConfig.reward_addcoin.isEnable

    fun showRewardCheckInAd(activity: Activity, onFinished: () -> Unit) {
        AdsManager.showRewardCheckin(
            activity = activity,
            onNextAction = onFinished,
            onFailed = {
                AdsManager.loadRewardCheckin(activity, object : AdsManager.RewardLoadListener {
                    override fun onLoadRewardSuccess() {
                        AdsManager.showRewardCheckin(
                            activity = activity,
                            onNextAction = onFinished,
                            onFailed = onFinished
                        )
                    }

                    override fun onLoadRewardFail() = onFinished()
                })
            }
        )
    }

    fun showRewardAddCoinAd(
        activity: Activity,
        ssvUserId: String?,
        onRewarded: () -> Unit,
        onUnavailable: () -> Unit,
        onAdPreloaded: (Boolean) -> Unit
    ) {
        AdsManager.showRewardAddcoin(
            activity = activity,
            ssvUserId = ssvUserId,
            onNextAction = onRewarded,
            onFailed = {
                AdsManager.loadRewardAddcoin(activity, object : AdsManager.RewardLoadListener {
                    override fun onLoadRewardSuccess() {
                        onAdPreloaded(true)
                        AdsManager.showRewardAddcoin(
                            activity = activity,
                            ssvUserId = ssvUserId,
                            onNextAction = onRewarded,
                            onFailed = onUnavailable
                        )
                    }

                    override fun onLoadRewardFail() {
                        onAdPreloaded(false)
                        onUnavailable()
                    }
                })
            }
        )
    }

    private fun preloadRewardAddCoinAd(
        activity: Activity,
        adsConfigLoaded: Boolean,
        adsRemainingToday: Int,
        onLoaded: (Boolean) -> Unit
    ) {
        if (!adsConfigLoaded) return
        if (adsRemainingToday <= 0) return
        if (!AdRemoteConfig.reward_addcoin.isEnable) return
        AdsManager.loadRewardAddcoin(activity, object : AdsManager.RewardLoadListener {
            override fun onLoadRewardSuccess() = onLoaded(true)

            override fun onLoadRewardFail() = onLoaded(false)
        })
    }

    private fun preloadRewardCheckInAd(activity: Activity, claimedToday: Boolean) {
        if (claimedToday) return
        if (!AdRemoteConfig.reward_checkin.isEnable) return
        if (AppPurchase.getInstance().isPurchased(activity)) return
        AdsManager.loadRewardCheckin(activity, object : AdsManager.RewardLoadListener {
            override fun onLoadRewardSuccess() = Unit

            override fun onLoadRewardFail() = Unit
        })
    }
}
