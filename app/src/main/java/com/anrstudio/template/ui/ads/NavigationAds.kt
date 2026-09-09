package com.pegas.aura.aigirlfriend.soul.ui.ads

import android.content.Context
import androidx.navigation.NavHostController
import com.pegas.aura.aigirlfriend.soul.ads.AdsManager
import com.pegas.aura.aigirlfriend.soul.ui.bases.ext.findActivity

fun NavHostController.navigateWithHomeInterstitial(
    context: Context,
    route: String
) {
    val activity = context.findActivity()
    if (activity != null) {
        AdsManager.loadInterAndShowInterHome(activity) {
            navigate(route)
        }
    } else {
        navigate(route)
    }
}

fun NavHostController.popBackStackWithBackInterstitial(context: Context) {
    val activity = context.findActivity()
    if (activity != null) {
        AdsManager.loadAndShowInterBack(activity) {
            popBackStackIfPossible()
        }
    } else {
        popBackStackIfPossible()
    }
}

private fun NavHostController.popBackStackIfPossible() {
    if (previousBackStackEntry != null) {
        popBackStack()
    }
}
