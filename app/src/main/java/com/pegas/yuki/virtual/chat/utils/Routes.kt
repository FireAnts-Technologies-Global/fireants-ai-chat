package com.pegas.yuki.virtual.chat.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.pegas.yuki.virtual.chat.app.AppConstants
import com.pegas.yuki.virtual.chat.ui.component.language.LanguageActivity
import com.pegas.yuki.virtual.chat.ui.component.main.MainActivity
import com.pegas.yuki.virtual.chat.ui.component.onboarding.OnBoardingActivity
import com.pegas.yuki.virtual.chat.ui.component.splash.SplashActivity
import com.pegas.yuki.virtual.chat.ui.component.welcome.WelcomeActivity

object Routes {
    fun startMainActivity(fromActivity: Activity) =
        Intent(fromActivity, MainActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun startWelcomeActivity(fromActivity: Activity) {
        if (fromActivity is WelcomeActivity) return
        val intent = Intent(fromActivity, WelcomeActivity::class.java)
        fromActivity.startActivity(intent)
    }

    fun startOnBoardingActivity(fromActivity: Activity) =
        Intent(fromActivity, OnBoardingActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun startLanguageActivity(fromActivity: Activity, bundle: Bundle?) =
        Intent(fromActivity, LanguageActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            bundle?.let { putExtras(it) }
            fromActivity.startActivity(this)
        }

    fun startSplashActivity(fromActivity: Activity) =
        Intent(fromActivity, SplashActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun addTrackingMoveScreen(fromActivity: String, toActivity: String) {
        FireAntsTrackingHelper.fromScreenToScreen(fromActivity, toActivity)
    }
}