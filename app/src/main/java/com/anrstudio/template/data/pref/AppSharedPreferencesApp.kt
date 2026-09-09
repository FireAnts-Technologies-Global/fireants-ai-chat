package com.pegas.aura.aigirlfriend.soul.data.pref

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreferencesApp(context: Context) : AppSharedPref {

    companion object {

        private const val PREFERENCE_FILE_KEY = "app_shared_preferences_app"

        private const val LANGUAGE_CODE = "language_code"

        private const val FIRST_LANGUAGE = "first_language"

        private const val FIRST_ONBOARDING = "first_onboarding"

        private const val IS_CONFIRM_CONSENT = "is_confirm_consent"

        private const val IS_USER_GLOBAL = "is_user_global"

        private const val IS_RATE = "is_rate"

        private const val ACCESS_TOKEN = "access_token"

        private const val REFRESH_TOKEN = "refresh_token"

        private const val RESUME_GUEST_TOKEN = "resume_guest_token"

        private const val USER_ID = "user_id"

        private const val DEVICE_ID = "device_id"

        private const val CLIENT_ID = "client_id"

        private const val PUSH_TOKEN = "push_token"
        private const val IS_RATE_SHOWN_IN_SESSION = "is_rate_shown_in_session"
        private const val OPEN_APP_COUNT = "open_app_count"
        private const val SUCCESSFUL_CHAT_MESSAGE_COUNT = "successful_chat_message_count"

    }

    override val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

    override val editor: SharedPreferences.Editor
        get() = sharedPref.edit()

    override var languageCode: String
        get() = sharedPref.getString(LANGUAGE_CODE, "en") ?: "en"
        set(value) = editor.putString(LANGUAGE_CODE, value).apply()

    override var firstLanguage: Boolean
        get() = sharedPref.getBoolean(FIRST_LANGUAGE, true)
        set(value) = editor.putBoolean(FIRST_LANGUAGE, value).apply()

    override var firstOnBoarding: Boolean
        get() = sharedPref.getBoolean(FIRST_ONBOARDING, true)
        set(value) = editor.putBoolean(FIRST_ONBOARDING, value).apply()

    override var isConfirmConsent: Boolean
        get() = sharedPref.getBoolean(IS_CONFIRM_CONSENT, false)
        set(value) = editor.putBoolean(IS_CONFIRM_CONSENT, value).apply()

    override var isUserGlobal: Boolean
        get() = sharedPref.getBoolean(IS_USER_GLOBAL, false)
        set(value) = editor.putBoolean(IS_USER_GLOBAL, value).apply()

    override var isRate: Boolean
        get() = sharedPref.getBoolean(IS_RATE, false)
        set(value) = editor.putBoolean(IS_RATE, value).apply()

    override var accessToken: String
        get() = sharedPref.getString(ACCESS_TOKEN, "") ?: ""
        set(value) = editor.putString(ACCESS_TOKEN, value).apply()

    override var refreshToken: String
        get() = sharedPref.getString(REFRESH_TOKEN, "") ?: ""
        set(value) = editor.putString(REFRESH_TOKEN, value).apply()

    override var resumeGuestToken: String
        get() = sharedPref.getString(RESUME_GUEST_TOKEN, "") ?: ""
        set(value) = editor.putString(RESUME_GUEST_TOKEN, value).apply()

    override var userId: String
        get() = sharedPref.getString(USER_ID, "") ?: ""
        set(value) = editor.putString(USER_ID, value).apply()

    override var deviceId: String
        get() = sharedPref.getString(DEVICE_ID, "") ?: ""
        set(value) = editor.putString(DEVICE_ID, value).apply()

    override var clientId: String
        get() = sharedPref.getString(CLIENT_ID, "") ?: ""
        set(value) = editor.putString(CLIENT_ID, value).apply()

    override var pushToken: String
        get() = sharedPref.getString(PUSH_TOKEN, "") ?: ""
        set(value) = editor.putString(PUSH_TOKEN, value).apply()

    override var isRateShownInSession: Boolean
        get() = sharedPref.getBoolean(IS_RATE_SHOWN_IN_SESSION, false)
        set(value) = editor.putBoolean(IS_RATE_SHOWN_IN_SESSION, value).apply()

    override var openAppCount: Int
        get() = sharedPref.getInt(OPEN_APP_COUNT, 0)
        set(value) = editor.putInt(OPEN_APP_COUNT, value).apply()

    override var successfulChatMessageCount: Int
        get() = sharedPref.getInt(SUCCESSFUL_CHAT_MESSAGE_COUNT, 0)
        set(value) = editor.putInt(SUCCESSFUL_CHAT_MESSAGE_COUNT, value).apply()

}
