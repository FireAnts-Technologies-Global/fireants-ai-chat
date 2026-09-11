package com.pegas.yuki.virtual.chat.app


import com.pegas.yuki.virtual.chat.ads.AdRemoteConfig
import com.pegas.yuki.virtual.chat.ads.inter_welcome_back
import com.pegas.yuki.virtual.chat.ads.native_welcome_back

enum class ResumeAdsEntryMode {
    OPEN_RESUME,
    WELCOME,
    NONE,
}

object ResumeAdsEntryRule {
    fun currentMode(): ResumeAdsEntryMode {
        if (!AdRemoteConfig.isInitialized()) return ResumeAdsEntryMode.NONE

        val canUseWelcome =
            AdRemoteConfig.native_welcome_back.isEnable && AdRemoteConfig.inter_welcome_back.isEnable
        if (canUseWelcome) return ResumeAdsEntryMode.WELCOME

        return ResumeAdsEntryMode.NONE
    }

    fun shouldEnableOpenResume(): Boolean = currentMode() == ResumeAdsEntryMode.OPEN_RESUME

    fun shouldShowWelcomeOnResume(): Boolean = currentMode() == ResumeAdsEntryMode.WELCOME
}
