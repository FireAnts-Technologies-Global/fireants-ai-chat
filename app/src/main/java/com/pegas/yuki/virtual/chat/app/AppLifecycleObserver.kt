package com.pegas.yuki.virtual.chat.app

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.fireants.adsdk.admob.AppOpenManager
import com.fireants.adsdk.ads.FireAntsAdSdk
import com.fireants.adsdk.billing.AppPurchase
import com.pegas.yuki.virtual.chat.ui.component.language.LanguageActivity
import com.pegas.yuki.virtual.chat.ui.component.onboarding.OnBoardingActivity
import com.pegas.yuki.virtual.chat.ui.component.splash.SplashActivity
import com.pegas.yuki.virtual.chat.ui.component.welcome.WelcomeActivity
import com.pegas.yuki.virtual.chat.utils.Routes

class AppLifecycleObserver : DefaultLifecycleObserver {

    private val listActivityDisableResume = arrayListOf(
        SplashActivity::class.java,
        LanguageActivity::class.java,
        OnBoardingActivity::class.java,
        WelcomeActivity::class.java,
    )

    override fun onStart(owner: LifecycleOwner) {
        val currentActivity = GlobalApp.currentActivity
        if (currentActivity != null) {
            val isDisable = listActivityDisableResume.any { clazz ->
                clazz.name == currentActivity.javaClass.name
            } || !currentActivity.javaClass.name.startsWith("com.pegas.yuki.virtual.chat")
            if (!isDisable && ResumeAdsEntryRule.shouldShowWelcomeOnResume() && !AppOpenManager.getInstance().isInterstitialShowing && !AppPurchase.getInstance()
                    .isPurchased(currentActivity.applicationContext) && FireAntsAdSdk.getInstance()
                    .shouldDisplayInterWelcomeBack
            ) {
                Routes.startWelcomeActivity(currentActivity)
            }
        }
    }

    override fun onStop(owner: LifecycleOwner) {}
}