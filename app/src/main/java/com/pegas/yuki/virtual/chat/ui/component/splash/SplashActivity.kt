package com.pegas.yuki.virtual.chat.ui.component.splash

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.fireants.adsdk.admob.Admob
import com.fireants.adsdk.ads.FireAntsAdSdk
import com.fireants.adsdk.funtion.AdCallback
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ads.AdRemoteConfig
import com.pegas.yuki.virtual.chat.ads.AdUnitConfig
import com.pegas.yuki.virtual.chat.ads.AdsManager
import com.pegas.yuki.virtual.chat.ads.AdsManager.loadNativeLanguage
import com.pegas.yuki.virtual.chat.ads.RemoteConfigUtils
import com.pegas.yuki.virtual.chat.ads.banner_splash
import com.pegas.yuki.virtual.chat.ads.inter_splash
import com.pegas.yuki.virtual.chat.app.AppConstants
import com.pegas.yuki.virtual.chat.app.GlobalApp
import com.pegas.yuki.virtual.chat.databinding.ActivitySplashBinding
import com.pegas.yuki.virtual.chat.ui.bases.BannerConfig
import com.pegas.yuki.virtual.chat.ui.bases.BaseActivityWithBanner
import com.pegas.yuki.virtual.chat.ui.bases.ConsentHandler
import com.pegas.yuki.virtual.chat.ui.bases.StatusBarConfig
import com.pegas.yuki.virtual.chat.ui.bases.ext.goneView
import com.pegas.yuki.virtual.chat.ui.bases.ext.isNetwork
import com.pegas.yuki.virtual.chat.ui.bases.ext.showToastByString
import com.pegas.yuki.virtual.chat.ui.component.splash.viewmodel.SplashViewModel
import com.pegas.yuki.virtual.chat.ui.model.asString
import com.pegas.yuki.virtual.chat.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivityWithBanner<ActivitySplashBinding>(), RemoteConfigUtils.Listener {
    private val viewModel: SplashViewModel by viewModels()
    private var authBootstrapFinished = false

    override var bannerConfig: BannerConfig = BannerConfig(
        adUnitConfig = AdUnitConfig(
            id = "",
            isEnable = false,
            reloadIntervalSeconds = 0
        ),
        isCollapse = false
    )

    override val statusBarConfig: StatusBarConfig
        get() = StatusBarConfig(
            applyPadding = false
        )

    private var getConfigSuccess = false
    private lateinit var consentHandler: ConsentHandler

    override fun getLayoutActivity() = R.layout.activity_splash

    override fun initViews() {
        super.initViews()
        Glide.with(this@SplashActivity).load(R.drawable.bg_splash).centerCrop().into(mBinding.imgBg)
        observeAuthBootstrap()
        viewModel.bootstrapAuth(
            hasAccessToken = appSharedPref.accessToken.isNotBlank(),
            hasRefreshToken = appSharedPref.refreshToken.isNotBlank(),
            hasResumeGuestToken = appSharedPref.resumeGuestToken.isNotBlank(),
            hasNetwork = isNetwork()
        )
        appSharedPref.isRateShownInSession = false
        appSharedPref.openAppCount = appSharedPref.openAppCount + 1
        AdsManager.clearAll()
        RemoteConfigUtils.init(this, this)
        consentHandler = ConsentHandler(
            activity = this,
            appSharedPref = appSharedPref,
            trackingSuffix = 1,
            onConsentFlowCompleted = { loadingRemoteConfig() }
        )
        if (appSharedPref.isConfirmConsent.not() && appSharedPref.isUserGlobal.not() && isNetwork()) {
            consentHandler.requestConsent()
        } else {
            loadingRemoteConfig()
        }

    }

    private fun observeAuthBootstrap() {
        viewModel.authBootstrapFinished.observe(this) { finished ->
            authBootstrapFinished = finished == true
        }
        viewModel.authBootstrapError.observe(this) { error ->
            if (error == null) return@observe
            showToastByString(error.asString(this))
            viewModel.onAuthBootstrapErrorHandled()
        }
    }


    private fun loadingRemoteConfig() {
        val totalTime = AppConstants.DEFAULT_TIME_SPLASH
        mBinding.progressSplash.max = 100
        mBinding.progressSplash.progress = 0
        object : CountDownTimer(totalTime, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsed = totalTime - millisUntilFinished
                mBinding.progressSplash.progress = (elapsed * 100 / totalTime).toInt()
                if (getConfigSuccess && authBootstrapFinished && millisUntilFinished < AppConstants.DEFAULT_LIMIT_TIME_SPLASH) {
                    checkRemoteConfigResult()
                    cancel()
                }
            }

            override fun onFinish() {
                mBinding.progressSplash.progress = 100
                if (!getConfigSuccess) {
                    checkRemoteConfigResult()
                }
            }
        }.start()
    }


    private fun checkRemoteConfigResult() {
        (application as? GlobalApp)?.applyInterstitialInterval(RemoteConfigUtils.getInterInterval())

        if (AdRemoteConfig.banner_splash.isEnable) {
            bannerConfig = BannerConfig(
                AdRemoteConfig.banner_splash,
                false
            )
            loadBanner()
        } else {
            mBinding.frBanner.goneView()
        }
        AdRemoteConfig.initialize(this, RemoteConfigUtils.getAdRemoteConfig())
        loadNativeLanguage(this, appSharedPref.firstLanguage, R.layout.layout_native_language)

        if (AdRemoteConfig.inter_splash.isEnable && isNetwork(this@SplashActivity)) {
            Admob.getInstance().setOpenActivityAfterShowInterAds(false)
            FireAntsAdSdk.getInstance().loadSplashInterstitialAds(
                this,
                AdRemoteConfig.inter_splash.id,
                30000,
                5000,
                object : AdCallback() {
                    override fun onNextAction() {
                        super.onNextAction()
                        moveActivity()
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                        AdsManager.setupTimeShowAd()
                    }
                })
        } else {
            moveActivity()
        }

    }


    private fun moveActivity() {
        Routes.startLanguageActivity(this, null)
        finish()
    }


    override fun onResume() {
        super.onResume()
        FireAntsAdSdk.getInstance()
            .onCheckShowSplashWhenFail(this@SplashActivity, object : AdCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    moveActivity()
                }
            }, 1000)
    }

    override fun loadSuccess() {
        getConfigSuccess = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::consentHandler.isInitialized) {
            consentHandler.clear()
        }
    }
}
