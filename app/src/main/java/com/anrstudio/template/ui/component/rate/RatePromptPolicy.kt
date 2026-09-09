package com.pegas.aura.aigirlfriend.soul.ui.component.rate

import com.pegas.aura.aigirlfriend.soul.data.pref.AppSharedPref

object RatePromptPolicy {
    const val CHAT_SUCCESS_THRESHOLD = 6
    const val MAIN_DELAY_MS = 90_000L

    fun canShowAfterChatSuccess(appSharedPref: AppSharedPref): Boolean =
        canShow(
            appSharedPref = appSharedPref,
            minSuccessfulChatCount = CHAT_SUCCESS_THRESHOLD
        )

    fun canShowAfterAppEngagement(appSharedPref: AppSharedPref): Boolean =
        canShow(
            appSharedPref = appSharedPref,
            minSuccessfulChatCount = 1
        )

    fun canShowAfterPremiumAction(appSharedPref: AppSharedPref): Boolean =
        canShow(appSharedPref = appSharedPref)

    fun markShownInSession(appSharedPref: AppSharedPref) {
        appSharedPref.isRateShownInSession = true
    }

    private fun canShow(
        appSharedPref: AppSharedPref,
        minSuccessfulChatCount: Int = 0
    ): Boolean = !appSharedPref.isRate &&
            !appSharedPref.isRateShownInSession &&
            appSharedPref.openAppCount >= 1 &&
            appSharedPref.successfulChatMessageCount >= minSuccessfulChatCount
}
