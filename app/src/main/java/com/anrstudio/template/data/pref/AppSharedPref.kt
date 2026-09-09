package com.pegas.aura.aigirlfriend.soul.data.pref

import android.content.SharedPreferences

interface AppSharedPref {

    val sharedPref: SharedPreferences

    val editor: SharedPreferences.Editor

    var languageCode: String

    var firstLanguage: Boolean

    var firstOnBoarding: Boolean

    var isConfirmConsent: Boolean

    var isUserGlobal: Boolean

    var isRate: Boolean

    var accessToken: String

    var refreshToken: String

    var resumeGuestToken: String

    var userId: String

    var deviceId: String

    var clientId: String

    var pushToken: String

    var isRateShownInSession: Boolean

    var openAppCount: Int

    var successfulChatMessageCount: Int

}
