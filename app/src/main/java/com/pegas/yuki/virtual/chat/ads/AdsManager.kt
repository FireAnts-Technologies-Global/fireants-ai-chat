package com.pegas.yuki.virtual.chat.ads

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.fireants.adsdk.ads.FireAntsAdSdk
import com.fireants.adsdk.ads.wrapper.ApInterstitialAd
import com.fireants.adsdk.ads.wrapper.ApNativeAd
import com.fireants.adsdk.billing.AppPurchase
import com.fireants.adsdk.dialog.PrepareLoadingAdsDialog
import com.fireants.adsdk.funtion.AdCallback
import com.fireants.adsdk.funtion.AdType
import com.fireants.adsdk.funtion.RewardCallback
import com.fireants.adsdk.util.AppConstant
import com.fireants.adsdk.util.SharePreferenceUtils
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.pegas.yuki.virtual.chat.ui.bases.ext.goneView
import timber.log.Timber

@SuppressLint("StaticFieldLeak")
object AdsManager {

    enum class NativeAdLoadState {
        IDLE,
        LOADING,
        LOADED,
        FAILED
    }

    val nativeLanguageAdLive = MutableLiveData<ApNativeAd?>()
    val nativeLanguageClickAdLive = MutableLiveData<ApNativeAd?>()
    val nativeLanguageAdStateLive = MutableLiveData(NativeAdLoadState.IDLE)
    val nativeLanguageClickAdStateLive = MutableLiveData(NativeAdLoadState.IDLE)
    val nativeOnboarding4AdLive = MutableLiveData<ApNativeAd?>()
    val nativeOnboardingFullAfterPage1AdLive = MutableLiveData<ApNativeAd?>()
    val nativeOnboardingFullAfterPage3AdLive = MutableLiveData<ApNativeAd?>()
    val nativeOnboarding4AdStateLive = MutableLiveData(NativeAdLoadState.IDLE)
    val nativeOnboardingFullAfterPage1AdStateLive = MutableLiveData(NativeAdLoadState.IDLE)
    val nativeOnboardingFullAfterPage3AdStateLive = MutableLiveData(NativeAdLoadState.IDLE)
    val nativeWelcomeAdLive = MutableLiveData<ApNativeAd?>()
    val nativeWelcomeAdStateLive = MutableLiveData(NativeAdLoadState.IDLE)

    private val adConfigMap = mutableMapOf<ApNativeAd, AdUnitConfig>()
    fun getAdConfig(ad: ApNativeAd): AdUnitConfig? = adConfigMap[ad]

    private var interSplashAd: ApInterstitialAd? = null
    private var interHomeAd: ApInterstitialAd? = null
    private var interWelcomeBackAd: ApInterstitialAd? = null
    private var interBackAd: ApInterstitialAd? = null

    private var rewardCheckinAd: RewardedAd? = null
    private var rewardAddcoinAd: RewardedAd? = null

    interface RewardLoadListener {
        fun onLoadRewardSuccess()
        fun onLoadRewardFail()
    }

    private fun loadNativeInternal(
        activity: Activity,
        config: AdUnitConfig,
        layoutRes: Int,
        liveData: MutableLiveData<ApNativeAd?>,
        shouldDisplay: Boolean = true,
        stateLiveData: MutableLiveData<NativeAdLoadState>? = null,
    ) {
        if (!config.isEnable
            || AppPurchase.getInstance().isPurchased(activity)
            || !activity.isNetworkAvailable()
            || !shouldDisplay
        ) {
            liveData.postValue(null)
            stateLiveData?.postValue(NativeAdLoadState.FAILED)
            return
        }
        stateLiveData?.postValue(NativeAdLoadState.LOADING)
        FireAntsAdSdk.getInstance()
            .loadNativeAdResultCallback(activity, config.id, layoutRes, object : AdCallback() {
                override fun onNativeAdLoaded(nativeAd: ApNativeAd) {
                    super.onNativeAdLoaded(nativeAd)
                    adConfigMap[nativeAd] = config
                    liveData.postValue(nativeAd)
                    stateLiveData?.postValue(NativeAdLoadState.LOADED)
                }

                override fun onAdFailedToLoad(adError: LoadAdError?) {
                    super.onAdFailedToLoad(adError)
                    liveData.postValue(null)
                    stateLiveData?.postValue(NativeAdLoadState.FAILED)
                }
            })
    }

    fun loadNativeLanguage(activity: Activity, layoutRes: Int) {
        val config =
            AdRemoteConfig.native_language
        loadNativeInternal(
            activity,
            config,
            layoutRes,
            nativeLanguageAdLive,
            stateLiveData = nativeLanguageAdStateLive
        )
    }

    fun loadNativeLanguageClick(activity: Activity,  layoutRes: Int) {
        val config =
           AdRemoteConfig.native_language_click
        loadNativeInternal(
            activity,
            config,
            layoutRes,
            nativeLanguageClickAdLive,
            stateLiveData = nativeLanguageClickAdStateLive
        )
    }

    fun loadNativeOnboarding4(activity: Activity, layoutRes: Int) {
        val config =
          AdRemoteConfig.native_onboarding_page4
        loadNativeInternal(
            activity, config, layoutRes, nativeOnboarding4AdLive,
            FireAntsAdSdk.getInstance()
                .shouldDisplayNativeOnboardingNormal2,
            nativeOnboarding4AdStateLive
        )
    }

    fun loadNativeOnboardingFullAfterPage1(activity: Activity, layoutRes: Int) {
        val config =
            AdRemoteConfig.native_onboarding_fullscreen12
        loadNativeInternal(
            activity, config, layoutRes, nativeOnboardingFullAfterPage1AdLive,
            FireAntsAdSdk.getInstance().shouldDisplayNativeOnboardingFull1,
            nativeOnboardingFullAfterPage1AdStateLive
        )
    }

    fun loadNativeOnboardingFullAfterPage3(activity: Activity,  layoutRes: Int) {
        val config =
         AdRemoteConfig.native_onboarding_fullscreen23
        loadNativeInternal(
            activity, config, layoutRes, nativeOnboardingFullAfterPage3AdLive,
            FireAntsAdSdk.getInstance().shouldDisplayNativeOnboardingFull2,
            nativeOnboardingFullAfterPage3AdStateLive
        )
    }
    
    fun loadNativeWelcome(activity: Activity, layoutRes: Int) {
        loadNativeInternal(
            activity, AdRemoteConfig.native_welcome_back, layoutRes, nativeWelcomeAdLive,
            FireAntsAdSdk.getInstance()
                .shouldDisplayNativeWelcomeBack,
            nativeWelcomeAdStateLive
        )
    }

    fun loadInterWelcome(context: Context, ignoreLimit: Boolean = false) {
        val config = AdRemoteConfig.inter_welcome_back
        if (!config.isEnable
            || AppPurchase.getInstance().isPurchased(context)
            || (!ignoreLimit && !FireAntsAdSdk.getInstance()
                .shouldDisplayInterWelcomeBack)
        ) {
            interWelcomeBackAd = null
            return
        }
        interWelcomeBackAd =
            FireAntsAdSdk.getInstance()
                .getInterstitialAds(context, config.id, object : AdCallback() {})
    }

    fun showInterWelcome(context: Context, ignoreLimit: Boolean = false, onAction: () -> Unit) {
        val interstitial = interWelcomeBackAd
        if (interstitial != null && interstitial.isReady && !AppPurchase.getInstance()
                .isPurchased(context) && (ignoreLimit ||
                    FireAntsAdSdk.getInstance()
                        .shouldDisplayInterWelcomeBack)
        ) {
            FireAntsAdSdk.getInstance()
                .forceShowInterstitial(context, interstitial, object : AdCallback() {
                    override fun onNextAction() {
                        super.onNextAction()
                        onAction()
                    }
                }, false)
        } else {
            onAction()
        }
    }

    fun loadInterAndShowInterHome(context: Context, onAction: () -> Unit) {
        if (System.currentTimeMillis() - SharePreferenceUtils.getLastImpressionInterstitialTime(
                context
            )
            < FireAntsAdSdk.getInstance().adConfig.intervalInterstitialAd * 1000L
        ) {
            onAction()
            return
        }

        val config = AdRemoteConfig.inter_home
        if (!config.isEnable || AppPurchase.getInstance().isPurchased(context)) {
            onAction()
            return
        }

        val readyAd = interHomeAd
        if (readyAd != null && readyAd.isReady) {
            var isActionCalled = false
            fun safeAction() {
                if (!isActionCalled) {
                    isActionCalled = true
                    onAction()
                }
            }
            FireAntsAdSdk.getInstance()
                .forceShowInterstitial(
                    context,
                    readyAd,
                    object : AdCallback() {
                        override fun onNextAction() {
                            super.onNextAction()
                            safeAction()
                        }

                        override fun onAdFailedToShow(adError: AdError?) {
                            super.onAdFailedToShow(adError)
                            safeAction()
                        }
                    },
                    true
                )
            return
        }

        val dialog = PrepareLoadingAdsDialog(context)
        dialog.show()
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setDimAmount(0.6f)

        var isActionCalled = false
        fun safeAction() {
            if (!isActionCalled) {
                isActionCalled = true
                try {
                    if (dialog.isShowing) dialog.dismiss()
                } catch (_: Exception) {
                }
                onAction()
            }
        }

        val handler = Handler(Looper.getMainLooper())
        val timeoutRunnable = Runnable {
            safeAction()
        }

        handler.postDelayed(timeoutRunnable, 15_000)
        FireAntsAdSdk.getInstance().getInterstitialAds(context, config.id, object : AdCallback() {
            override fun onApInterstitialLoad(apInterstitialAd: ApInterstitialAd?) {
                super.onApInterstitialLoad(apInterstitialAd)
                interHomeAd = apInterstitialAd
                handler.removeCallbacks(timeoutRunnable)
                try {
                    if (dialog.isShowing) dialog.dismiss()
                } catch (_: Exception) {
                }
                if (apInterstitialAd != null && apInterstitialAd.isReady) {
                    FireAntsAdSdk.getInstance()
                        .forceShowInterstitial(
                            context,
                            apInterstitialAd,
                            object : AdCallback() {
                                override fun onNextAction() {
                                    super.onNextAction()
                                    safeAction()
                                }

                                override fun onAdFailedToShow(adError: AdError?) {
                                    super.onAdFailedToShow(adError)
                                    safeAction()
                                }
                            },
                            false
                        )
                } else {
                    safeAction()
                }
            }

            override fun onAdFailedToLoad(i: LoadAdError?) {
                super.onAdFailedToLoad(i)
                handler.removeCallbacks(timeoutRunnable)
                safeAction()
            }
        })
    }

    fun loadAndShowInterBack(context: Context, onAction: () -> Unit) {
        if (System.currentTimeMillis() - SharePreferenceUtils.getLastImpressionInterstitialTime(
                context
            )
            < FireAntsAdSdk.getInstance().adConfig.intervalInterstitialAd * 1000L
        ) {
            onAction()
            return
        }

        val config = AdRemoteConfig.inter_back
        if (!config.isEnable || AppPurchase.getInstance().isPurchased(context)) {
            onAction()
            return
        }

        val readyAd = interBackAd
        if (readyAd != null && readyAd.isReady) {
            var isActionCalled = false
            fun safeAction() {
                if (!isActionCalled) {
                    isActionCalled = true
                    onAction()
                }
            }
            FireAntsAdSdk.getInstance()
                .forceShowInterstitial(
                    context,
                    readyAd,
                    object : AdCallback() {
                        override fun onNextAction() {
                            super.onNextAction()
                            safeAction()
                        }

                        override fun onAdFailedToShow(adError: AdError?) {
                            super.onAdFailedToShow(adError)
                            safeAction()
                        }
                    },
                    true
                )
            return
        }

        val dialog = PrepareLoadingAdsDialog(context)
        dialog.show()
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setDimAmount(0.6f)

        var isActionCalled = false
        fun safeAction() {
            if (!isActionCalled) {
                isActionCalled = true
                try {
                    if (dialog.isShowing) dialog.dismiss()
                } catch (_: Exception) {
                }
                onAction()
            }
        }

        val handler = Handler(Looper.getMainLooper())
        val timeoutRunnable = Runnable {
            safeAction()
        }

        handler.postDelayed(timeoutRunnable, 15_000)
        FireAntsAdSdk.getInstance().getInterstitialAds(context, config.id, object : AdCallback() {
            override fun onApInterstitialLoad(apInterstitialAd: ApInterstitialAd?) {
                super.onApInterstitialLoad(apInterstitialAd)
                interBackAd = apInterstitialAd
                handler.removeCallbacks(timeoutRunnable)
                try {
                    if (dialog.isShowing) dialog.dismiss()
                } catch (_: Exception) {
                }
                if (apInterstitialAd != null && apInterstitialAd.isReady) {
                    FireAntsAdSdk.getInstance()
                        .forceShowInterstitial(
                            context,
                            apInterstitialAd,
                            object : AdCallback() {
                                override fun onNextAction() {
                                    super.onNextAction()
                                    safeAction()
                                }

                                override fun onAdFailedToShow(adError: AdError?) {
                                    super.onAdFailedToShow(adError)
                                    safeAction()
                                }
                            },
                            false
                        )
                } else {
                    safeAction()
                }
            }

            override fun onAdFailedToLoad(i: LoadAdError?) {
                super.onAdFailedToLoad(i)
                handler.removeCallbacks(timeoutRunnable)
                safeAction()
            }
        })
    }

    fun loadBanner(
        activity: AppCompatActivity,
        adUnitConfig: AdUnitConfig,
        frAds: FrameLayout,
        isCollapse: Boolean,
    ) {
        if (adUnitConfig.isEnable) {
            removeBannerView(activity, frAds)
            if (isCollapse) FireAntsAdSdk.getInstance().loadCollapsibleBanner(
                activity,
                adUnitConfig.id,
                AppConstant.CollapsibleGravity.BOTTOM,
                object : AdCallback() {
                    override fun onAdFailedToLoad(i: LoadAdError?) {
                        super.onAdFailedToLoad(i)
                        frAds.goneView()
                        Timber.tag("AdsManager_Banner")
                            .d("Load banner on ${activity.javaClass.simpleName} failed by : ${i?.message}")
                    }
                })
            else FireAntsAdSdk.getInstance()
                .loadBanner(activity, adUnitConfig.id, object : AdCallback() {
                    override fun onAdFailedToLoad(i: LoadAdError?) {
                        super.onAdFailedToLoad(i)
                        frAds.goneView()
                        Timber.tag("AdsManager_Banner")
                            .d("Load banner on ${activity.javaClass.simpleName} failed by : ${i?.message}")
                    }
                })
        } else {
            frAds.removeAllViews()
            frAds.goneView()
        }
    }

    @SuppressLint("InflateParams")
    private fun removeBannerView(activity: Activity, frAds: FrameLayout) {
        try {
            val container = frAds.findViewById<FrameLayout>(com.fireants.adsdk.R.id.banner_container)
            if (container != null) {
                for (i in 0 until container.childCount) {
                    val view = container.getChildAt(i)
                    if (view is AdView) {
                        view.destroy()
                        container.removeView(view)
                    }
                }
            }
            val shimmerFrameLayout = LayoutInflater.from(activity)
                .inflate(com.fireants.adsdk.R.layout.layout_banner_control, null)
            frAds.removeAllViews()
            frAds.addView(shimmerFrameLayout)
        } catch (_: Exception) {
        }
    }

    fun loadRewardCheckin(activity: Activity, listener: RewardLoadListener) {
        val config = AdRemoteConfig.reward_checkin
        if (!config.isEnable || AppPurchase.getInstance().isPurchased(activity)) {
            listener.onLoadRewardFail()
            return
        }
        FireAntsAdSdk.getInstance().initRewardAds(activity, config.id, object : AdCallback() {
            override fun onRewardAdLoaded(rewardedAd: RewardedAd) {
                super.onRewardAdLoaded(rewardedAd)
                rewardCheckinAd = rewardedAd
                listener.onLoadRewardSuccess()
            }

            override fun onAdFailedToLoad(adError: LoadAdError?) {
                super.onAdFailedToLoad(adError)
                listener.onLoadRewardFail()
            }
        })
    }

    fun showRewardCheckin(activity: Activity, onNextAction: () -> Unit, onFailed: () -> Unit) {
        val rewardedAd = rewardCheckinAd
        if (rewardedAd == null || AppPurchase.getInstance().isPurchased(activity)) {
            onFailed()
            return
        }
        var isEarned = false
        FireAntsAdSdk.getInstance().showRewardAds(activity, rewardedAd, object : RewardCallback {
            override fun onUserEarnedReward(var1: RewardItem?) {
                isEarned = true
            }

            override fun onRewardedAdClosed() {
                if (isEarned) {
                    onNextAction()
                } else {
                    onFailed()
                }
                rewardCheckinAd = null
            }

            override fun onRewardedAdFailedToShow(codeError: Int) {
                onFailed()
                rewardCheckinAd = null
            }

            override fun onAdClicked() {}
            override fun onAdClicked(
                p0: String?,
                p1: String?,
                p2: AdType?
            ) {

            }

            override fun onAdImpression() {
            }

            override fun onAdLogRev(
                p0: AdValue?,
                p1: String?,
                p2: String?,
                p3: AdType?
            ) {
            }
        })
    }

    fun loadRewardAddcoin(activity: Activity, listener: RewardLoadListener) {
        val config = AdRemoteConfig.reward_addcoin
        if (!config.isEnable || AppPurchase.getInstance().isPurchased(activity)) {
            listener.onLoadRewardFail()
            return
        }
        FireAntsAdSdk.getInstance().initRewardAds(activity, config.id, object : AdCallback() {
            override fun onRewardAdLoaded(rewardedAd: RewardedAd) {
                super.onRewardAdLoaded(rewardedAd)
                rewardAddcoinAd = rewardedAd
                listener.onLoadRewardSuccess()
            }

            override fun onAdFailedToLoad(adError: LoadAdError?) {
                super.onAdFailedToLoad(adError)
                listener.onLoadRewardFail()
            }
        })
    }

    fun showRewardAddcoin(
        activity: Activity,
        ssvUserId: String? = null,
        onNextAction: () -> Unit,
        onFailed: () -> Unit
    ) {
        val rewardedAd = rewardAddcoinAd
        if (rewardedAd == null || AppPurchase.getInstance().isPurchased(activity)) {
            onFailed()
            return
        }
        if (!ssvUserId.isNullOrBlank()) {
            rewardedAd.setServerSideVerificationOptions(
                ServerSideVerificationOptions.Builder()
                    .setUserId(ssvUserId)
                    .build()
            )
        }
        var isEarned = false
        FireAntsAdSdk.getInstance().showRewardAds(activity, rewardedAd, object : RewardCallback {
            override fun onUserEarnedReward(var1: RewardItem?) {
                isEarned = true
            }

            override fun onRewardedAdClosed() {
                if (isEarned) {
                    onNextAction()
                } else {
                    onFailed()
                }
                rewardAddcoinAd = null
            }

            override fun onRewardedAdFailedToShow(codeError: Int) {
                onFailed()
                rewardAddcoinAd = null
            }

            override fun onAdClicked() {

            }

            override fun onAdClicked(
                p0: String?,
                p1: String?,
                p2: AdType?
            ) {

            }

            override fun onAdImpression() {

            }

            override fun onAdLogRev(
                p0: AdValue?,
                p1: String?,
                p2: String?,
                p3: AdType?
            ) {

            }
        })
    }

    fun clearAll() {
        nativeLanguageAdLive.postValue(null)
        nativeLanguageClickAdLive.postValue(null)
        nativeLanguageAdStateLive.postValue(NativeAdLoadState.IDLE)
        nativeLanguageClickAdStateLive.postValue(
            NativeAdLoadState.IDLE
        )
        nativeOnboarding4AdLive.postValue(null)
        nativeOnboardingFullAfterPage1AdLive.postValue(null)
        nativeOnboardingFullAfterPage3AdLive.postValue(null)
        nativeOnboarding4AdStateLive.postValue(NativeAdLoadState.IDLE)
        nativeOnboardingFullAfterPage1AdStateLive.postValue(
            NativeAdLoadState.IDLE
        )
        nativeOnboardingFullAfterPage3AdStateLive.postValue(
            NativeAdLoadState.IDLE
        )
        nativeWelcomeAdLive.postValue(null)
        nativeWelcomeAdStateLive.postValue(NativeAdLoadState.IDLE)
        interSplashAd = null
        interHomeAd = null
        interWelcomeBackAd = null
        interBackAd = null
        rewardCheckinAd = null
        rewardAddcoinAd = null
    }

    private fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}
