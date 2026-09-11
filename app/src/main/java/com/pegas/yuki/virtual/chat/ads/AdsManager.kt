package com.pegas.yuki.virtual.chat.ads

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.lifecycle.MutableLiveData
import com.facebook.shimmer.ShimmerFrameLayout
import com.fireants.adsdk.ads.FireAntsAdSdk
import com.fireants.adsdk.ads.wrapper.ApInterstitialAd
import com.fireants.adsdk.ads.wrapper.ApNativeAd
import com.fireants.adsdk.billing.AppPurchase
import com.fireants.adsdk.funtion.AdCallback
import com.fireants.adsdk.funtion.AdType
import com.fireants.adsdk.funtion.RewardCallback
import com.fireants.adsdk.util.AppConstant
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.ext.goneView
import com.pegas.yuki.virtual.chat.ui.bases.ext.isNetwork
import com.pegas.yuki.virtual.chat.ui.component.dialog.DialogLoading
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

    @SuppressLint("StaticFieldLeak")
    var nativeAdFullAll: ApNativeAd? = null

    private var timeoutHandler: Handler? = null
    private var timeoutRunnable: Runnable? = null

    private var lastTimeShowInter: Long = 0

    private var hasTimedOut = false


    @SuppressLint("StaticFieldLeak")
    private var loadingDialog: DialogLoading? = null

    private var adShowCounter = 0

    private const val MILLIS = 1000L

    interface RewardLoadListener {
        fun onLoadRewardSuccess()
        fun onLoadRewardFail()
    }

    fun setupTimeShowAd() {
        lastTimeShowInter = System.currentTimeMillis()
    }

    fun isTimeShowAd(): Boolean {
        return System.currentTimeMillis() - lastTimeShowInter >=
                RemoteConfigUtils.getInterInterval() * MILLIS
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

    fun loadInterAndShowInterHome(context: Activity, nextAction: () -> Unit) {
        val hasNetwork = isNetwork(context)
        val interHomeEnabled = AdRemoteConfig.inter_home.isEnable
        val isPurchased = AppPurchase.getInstance().isPurchased(context)
        val canShowByTime = isTimeShowAd()

        if (!hasNetwork || !interHomeEnabled || isPurchased) {
            nextAction.invoke()
            return
        }

        if (!canShowByTime) {
            nextAction.invoke()
            return
        }

        if (adShowCounter >= 2) {
            showNativeFullAllOverlay(context) {
                adShowCounter = 0
                lastTimeShowInter = System.currentTimeMillis()
                nextAction()
            }
            return
        }


        showLoadingDialog(context)
        hasTimedOut = false
        cancelTimeout()

        timeoutHandler = Handler(Looper.getMainLooper())
        timeoutRunnable = Runnable {
            hasTimedOut = true
            dismissLoadingDialog()
            nextAction.invoke()
        }
        timeoutHandler?.postDelayed(
            timeoutRunnable!!,
            RemoteConfigUtils.getInterInterval().toLong() * MILLIS
        )

        FireAntsAdSdk.getInstance().getInterstitialAds(
            context,
            AdRemoteConfig.inter_home.id,
            object : AdCallback() {
                override fun onApInterstitialLoad(apInterstitialAd: ApInterstitialAd?) {
                    super.onApInterstitialLoad(apInterstitialAd)
                    interHomeAd = apInterstitialAd

                    if (hasTimedOut) {
                        cancelTimeout()
                        dismissLoadingDialog()
                        interHomeAd = null
                        return
                    }

                    cancelTimeout()
                    dismissLoadingDialog()

                    interHomeAd?.let { interstitialAd ->
                        showInterInterval(context, interstitialAd) {
                            interHomeAd = null
                            nextAction.invoke()
                        }
                    } ?: run {
                        nextAction.invoke()
                    }
                }

                override fun onAdFailedToLoad(i: LoadAdError?) {
                    super.onAdFailedToLoad(i)
                    cancelTimeout()
                    dismissLoadingDialog()
                    interHomeAd = null
                    if (!hasTimedOut) {
                        nextAction.invoke()
                    }
                }

                override fun onAdFailedToShow(adError: AdError?) {
                    super.onAdFailedToShow(adError)
                    interHomeAd = null
                }
            })
    }

    fun loadAndShowInterBack(mContext: Activity, onNextAction: () -> Unit) {
        if (!isNetwork(mContext) || !AdRemoteConfig.inter_back.isEnable || AppPurchase.getInstance()
                .isPurchased(mContext)
        ) {
            onNextAction.invoke()
            return
        }

        if (!isTimeShowAd()) {
            onNextAction.invoke()
            return
        }

        if (adShowCounter >= 2) {
            showNativeFullAllOverlay(mContext) {
                adShowCounter = 0
                lastTimeShowInter = System.currentTimeMillis()
                onNextAction()
            }
            return
        }

        showLoadingDialog(mContext)
        hasTimedOut = false
        cancelTimeout()

        timeoutHandler = Handler(Looper.getMainLooper())
        timeoutRunnable = Runnable {
            hasTimedOut = true
            dismissLoadingDialog()
            onNextAction.invoke()
        }
        timeoutHandler?.postDelayed(
            timeoutRunnable!!,
            RemoteConfigUtils.getInterInterval().toLong() * MILLIS
        )

        FireAntsAdSdk.getInstance().getInterstitialAds(
            mContext,
            AdRemoteConfig.inter_back.id,
            object : AdCallback() {
                override fun onApInterstitialLoad(apInterstitialAd: ApInterstitialAd?) {
                    super.onApInterstitialLoad(apInterstitialAd)
                    interBackAd = apInterstitialAd

                    if (hasTimedOut) {
                        cancelTimeout()
                        dismissLoadingDialog()
                        interBackAd = null
                        return
                    }

                    cancelTimeout()
                    dismissLoadingDialog()

                    interBackAd?.let { interstitialAd ->
                        showInterInterval(mContext, interstitialAd) {
                            interBackAd = null
                            onNextAction.invoke()
                        }
                    } ?: run {
                        onNextAction.invoke()
                    }
                }

                override fun onAdFailedToLoad(i: LoadAdError?) {
                    super.onAdFailedToLoad(i)
                    cancelTimeout()
                    dismissLoadingDialog()
                    interBackAd = null
                    if (!hasTimedOut) {
                        onNextAction.invoke()
                    }
                }

                override fun onAdFailedToShow(adError: AdError?) {
                    super.onAdFailedToShow(adError)
                    interBackAd = null
                }
            })
    }

    private fun showInterInterval(
        context: Activity,
        interstitialAd: ApInterstitialAd,
        onNextAction: () -> Unit,
    ) {
        val hasNetwork = isNetwork(context)
        val isPurchased = AppPurchase.getInstance().isPurchased(context)
        if (!hasNetwork || isPurchased) {
            onNextAction.invoke()
            return
        }

        FireAntsAdSdk.getInstance().forceShowInterstitial(
            context,
            interstitialAd,
            object : AdCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    onNextAction.invoke()
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    adShowCounter++
                    lastTimeShowInter = System.currentTimeMillis()
                }

                override fun onAdFailedToLoad(i: LoadAdError?) {
                    super.onAdFailedToLoad(i)
                    onNextAction.invoke()
                }

                override fun onAdFailedToShow(adError: AdError?) {
                    super.onAdFailedToShow(adError)
                    onNextAction.invoke()
                }


            },
            false
        )
    }


    fun loadNativeFullAll(activity: Activity, onLoaded: (() -> Unit)? = null) {
        if (AdRemoteConfig.native_full_all.isEnable && !AppPurchase.getInstance()
                .isPurchased(activity)
        ) {
            FireAntsAdSdk.getInstance().loadNativeAdResultCallback(
                activity,
                AdRemoteConfig.native_full_all.id,
                R.layout.layout_native_ad_full,
                object : AdCallback() {
                    override fun onNativeAdLoaded(nativeAd: ApNativeAd) {
                        super.onNativeAdLoaded(nativeAd)
                        nativeAdFullAll = nativeAd
                        onLoaded?.invoke()
                    }


                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        onLoaded?.invoke()
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError?) {
                        super.onAdFailedToLoad(adError)
                        onLoaded?.invoke()
                    }

                    override fun onAdFailedToShow(adError: AdError?) {
                        super.onAdFailedToShow(adError)
                        onLoaded?.invoke()
                    }
                })
        } else {
            onLoaded?.invoke()
        }
    }

    private fun showNativeFullAllOverlay(activity: Activity, onNextAction: () -> Unit) {
        if (AppPurchase.getInstance().isPurchased(activity) ||
            !AdRemoteConfig.native_full_all.isEnable ||
            !isNetwork(activity)
        ) {
            onNextAction.invoke()
            return
        }

        val root = activity.findViewById<ViewGroup>(android.R.id.content)
        if (root == null) {
            onNextAction.invoke()
            return
        }

        try {
            val overlay = FrameLayout(activity).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor("#B3000000".toColorInt())
            }

            val adContainer = FrameLayout(activity).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            }

            val shimmer = LayoutInflater.from(activity).inflate(
                R.layout.layout_shimmer_native_full,
                null
            ) as ShimmerFrameLayout
            adContainer.addView(shimmer)
            var statusBarHeight = 0
            val resourceId =
                activity.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
            }
            val closeSize = (32 * activity.resources.displayMetrics.density).toInt()
            val closeParams = FrameLayout.LayoutParams(closeSize, closeSize).apply {
                gravity = Gravity.TOP or Gravity.END
                val margin = (16 * activity.resources.displayMetrics.density).toInt()
                setMargins(margin, margin + statusBarHeight, margin, margin)
            }
            val closeView = ImageView(activity).apply {
                layoutParams = closeParams
                setImageResource(R.drawable.ic_ad_close)
                setOnClickListener {
                    root.removeView(overlay)
                    onNextAction.invoke()
                }
            }

            overlay.addView(adContainer)
            overlay.addView(closeView)
            root.addView(overlay)

            if (nativeAdFullAll != null) {
                closeView.visibility = View.VISIBLE
                FireAntsAdSdk.getInstance().populateNativeAdView(
                    activity,
                    nativeAdFullAll!!,
                    adContainer,
                    shimmer
                )
            } else {
                closeView.visibility = View.GONE
                Handler(Looper.getMainLooper()).postDelayed({
                    closeView.visibility = View.VISIBLE
                }, 4000)

                loadNativeFullAll(activity) {
                    nativeAdFullAll?.let { ad ->
                        FireAntsAdSdk.getInstance().populateNativeAdView(
                            activity,
                            ad,
                            adContainer,
                            shimmer
                        )
                    } ?: run {
                        root.removeView(overlay)
                        onNextAction.invoke()
                    }
                }
            }
        } catch (e: Exception) {
            onNextAction.invoke()
        }
    }

    fun loadNativeLanguage(activity: Activity, isFirst: Boolean, layoutRes: Int) {
        val config =
            if (isFirst) AdRemoteConfig.native_language else AdRemoteConfig.native_language
        loadNativeInternal(
            activity,
            config,
            layoutRes,
            nativeLanguageAdLive,
            stateLiveData = nativeLanguageAdStateLive
        )
    }

    fun loadNativeLanguageClick(activity: Activity, isFirst: Boolean, layoutRes: Int) {
        val config =
            if (isFirst) AdRemoteConfig.native_language_click else AdRemoteConfig.native_language_click
        loadNativeInternal(
            activity,
            config,
            layoutRes,
            nativeLanguageClickAdLive,
            stateLiveData = nativeLanguageClickAdStateLive
        )
    }

    fun loadNativeOnboarding4(activity: Activity, isFirst: Boolean, layoutRes: Int) {
        val config =
            if (isFirst) AdRemoteConfig.native_onboarding_page4 else AdRemoteConfig.native_onboarding_page4
        loadNativeInternal(
            activity, config, layoutRes, nativeOnboarding4AdLive,
            FireAntsAdSdk.getInstance()
                .shouldDisplayNativeOnboardingNormal2,
            nativeOnboarding4AdStateLive
        )
    }

    fun loadNativeOnboardingFullAfterPage1(activity: Activity, isFirst: Boolean, layoutRes: Int) {
        val config =
            if (isFirst) AdRemoteConfig.native_onboarding_fullscreen12 else AdRemoteConfig.native_onboarding_fullscreen12
        loadNativeInternal(
            activity, config, layoutRes, nativeOnboardingFullAfterPage1AdLive,
            FireAntsAdSdk.getInstance().shouldDisplayNativeOnboardingFull1,
            nativeOnboardingFullAfterPage1AdStateLive
        )
    }

    fun loadNativeOnboardingFullAfterPage3(activity: Activity, isFirst: Boolean, layoutRes: Int) {
        val config =
            if (isFirst) AdRemoteConfig.native_onboarding_fullscreen23 else AdRemoteConfig.native_onboarding_fullscreen23
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
            val container =
                frAds.findViewById<FrameLayout>(com.fireants.adsdk.R.id.banner_container)
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

    private fun showLoadingDialog(context: Context) {
        try {
            if (loadingDialog != null && loadingDialog!!.isShowing) {
                return
            }

            dismissLoadingDialog()

            loadingDialog = DialogLoading(context)
            loadingDialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dismissLoadingDialog() {
        try {
            loadingDialog?.dismiss()
            loadingDialog = null
        } catch (e: Exception) {
            e.printStackTrace()
            loadingDialog = null
        }
    }

    private fun cancelTimeout() {
        timeoutRunnable?.let {
            timeoutHandler?.removeCallbacks(it)
        }
        timeoutRunnable = null
        timeoutHandler = null
    }
}
