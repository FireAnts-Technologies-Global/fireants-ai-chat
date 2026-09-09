package com.pegas.aura.aigirlfriend.soul.app


import com.pegas.aura.aigirlfriend.soul.ads.AdRemoteConfig
import com.pegas.aura.aigirlfriend.soul.ads.inter_welcome_back
import com.pegas.aura.aigirlfriend.soul.ads.native_welcome_back

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
