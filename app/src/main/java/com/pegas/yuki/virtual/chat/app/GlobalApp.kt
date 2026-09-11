package com.pegas.yuki.virtual.chat.app

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.ProcessLifecycleOwner
import com.fireants.adsdk.admob.Admob
import com.fireants.adsdk.admob.AppOpenManager
import com.fireants.adsdk.ads.FireAntsAdSdk
import com.fireants.adsdk.application.AdsMultiDexApplication
import com.fireants.adsdk.config.AppsFlyerConfig
import com.fireants.adsdk.config.FireAntsAdSdkConfig
import com.fireants.devconfig.FireAntsDevConfig
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
        FireAntsDevConfig.init(
            context = this,
            fireantsAdsVersion = BuildConfig.ANR_STUDIO_VERSION,
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
            if (BuildConfig.DEBUG) FireAntsAdSdkConfig.ENVIRONMENT_DEVELOP else FireAntsAdSdkConfig.ENVIRONMENT_PRODUCTION
        mFireAntsAdSdkConfig = FireAntsAdSdkConfig(this, environment)
        val appsFlyerConfig =
            AppsFlyerConfig(true, resources.getString(R.string.appsflyer_key), BuildConfig.DEBUG)
        mFireAntsAdSdkConfig.appsFlyerConfig = appsFlyerConfig
        mFireAntsAdSdkConfig.facebookClientToken =
            resources.getString(R.string.facebook_client_token)
        applyInterstitialInterval(RemoteConfigUtils.DEFAULT_INTER_INTERVAL_SECONDS)
        mFireAntsAdSdkConfig.idAdResume = ""
        FireAntsAdSdk.getInstance().init(this, mFireAntsAdSdkConfig)
        Admob.getInstance().setDisableAdResumeWhenClickAds(true)
        Admob.getInstance().setOpenActivityAfterShowInterAds(true)
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)
        AppOpenManager.getInstance().disableAppResumeWithActivity(LanguageActivity::class.java)
        AppOpenManager.getInstance().disableAppResumeWithActivity(OnBoardingActivity::class.java)
        FireAntsAdSdk.getInstance().prepareLoadingAdsDialogLayout = R.layout.layout_prepare_ads
        FireAntsAdSdk.getInstance().resumeLoadingDialogLayout = R.layout.layout_welcome_back
    }

    fun applyInterstitialInterval(intervalSeconds: Int) {
        mFireAntsAdSdkConfig.intervalInterstitialAd = intervalSeconds
    }
}