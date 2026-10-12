package com.pegas.yuki.virtual.chat.app

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.ProcessLifecycleOwner
import com.anrstudio.ads.admob.Admob
import com.anrstudio.ads.admob.AppOpenManager
import com.anrstudio.ads.ads.ANRAdSdk
import com.anrstudio.ads.application.AdsMultiDexApplication
import com.anrstudio.ads.config.ANRAdSdkConfig
import com.anrstudio.ads.config.AdjustConfig
import com.anrstudio.config.ANRConfig
import com.google.android.gms.ads.MobileAds
import com.pegas.yuki.virtual.chat.BuildConfig
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ads.AdRemoteConfig
import com.pegas.yuki.virtual.chat.ads.RemoteConfigUtils
import com.pegas.yuki.virtual.chat.ui.component.language.LanguageActivity
import com.pegas.yuki.virtual.chat.ui.component.onboarding.OnBoardingActivity
import com.pegas.yuki.virtual.chat.ui.component.splash.SplashActivity
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GlobalApp : AdsMultiDexApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: GlobalApp

        @SuppressLint("StaticFieldLeak")
        var currentActivity: Activity? = null
    }

    override fun onCreate() {
        super.onCreate()
        ANRConfig.init(
            context = this,
            anrstudioAdsVersion = BuildConfig.ANR_STUDIO_VERSION,
            playServicesAdsVersion = BuildConfig.PLAY_SERVICES_ADS_VERSION,
            gdprModuleVersion = BuildConfig.GDPR_MODULE_VERSION
        )
        MobileAds.initialize(this) {}
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initAdRemoteConfig()
        initAds()

        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
        registerActivityLifecycleCallbacks(AppActivityLifecycleCallbacks())
    }

    private fun initAdRemoteConfig() {
        AdRemoteConfig.initializeFromAssets(this)
    }

    private fun initAds() {

        val environment =
            if (BuildConfig.DEBUG) ANRAdSdkConfig.ENVIRONMENT_DEVELOP else ANRAdSdkConfig.ENVIRONMENT_PRODUCTION
        mANRAdSdkConfig = ANRAdSdkConfig(this, environment)
        val adjustConfig = AdjustConfig(
            true,
            resources.getString(R.string.adjust_token)
        )
        mANRAdSdkConfig.adjustConfig = adjustConfig
        mANRAdSdkConfig.facebookClientToken =
            resources.getString(R.string.facebook_client_token)
        applyInterstitialInterval(RemoteConfigUtils.DEFAULT_INTER_INTERVAL_SECONDS)
        mANRAdSdkConfig.idAdResume = ""
        ANRAdSdk.getInstance().init(this, mANRAdSdkConfig)
        Admob.getInstance().setDisableAdResumeWhenClickAds(true)
        Admob.getInstance().setOpenActivityAfterShowInterAds(true)
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)
        AppOpenManager.getInstance().disableAppResumeWithActivity(LanguageActivity::class.java)
        AppOpenManager.getInstance().disableAppResumeWithActivity(OnBoardingActivity::class.java)
        ANRAdSdk.getInstance().prepareLoadingAdsDialogLayout = R.layout.layout_prepare_ads
        ANRAdSdk.getInstance().resumeLoadingDialogLayout = R.layout.layout_welcome_back
    }

    fun applyInterstitialInterval(intervalSeconds: Int) {
        mANRAdSdkConfig.intervalInterstitialAd = intervalSeconds
    }
}