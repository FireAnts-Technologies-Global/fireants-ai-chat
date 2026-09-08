package com.anrstudio.template.data.auth

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.anrstudio.template.data.pref.AppSharedPref
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInfoProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSharedPref: AppSharedPref
) : AuthInfoProvider {

    override val packageName: String
        get() = context.packageName

    override val appVersion: String
        get() = runCatching {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        }.getOrNull() ?: "1.0.0"

    override val deviceName: String
        get() = "${Build.MANUFACTURER} ${Build.MODEL}".trim()

    override val accessToken: String
        get() = appSharedPref.accessToken

    override val refreshToken: String
        get() = appSharedPref.refreshToken

    override val resumeGuestToken: String
        get() = appSharedPref.resumeGuestToken

    override val userId: String
        get() = appSharedPref.userId

    override val clientId: String
        get() {
            val cached = appSharedPref.clientId
            if (cached.isNotBlank()) return cached
            return UUID.randomUUID().toString().also { appSharedPref.clientId = it }
        }

    override val pushToken: String
        get() = appSharedPref.pushToken

    override val deviceId: String
        get() {
            val cached = appSharedPref.deviceId
            if (cached.isNotBlank()) return cached
            val generated = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ).orEmpty()
            appSharedPref.deviceId = generated
            return generated
        }

    override fun persistGuestSession(
        accessToken: String,
        refreshToken: String,
        resumeGuestToken: String,
        userId: String
    ) {
        appSharedPref.accessToken = accessToken
        appSharedPref.refreshToken = refreshToken
        appSharedPref.resumeGuestToken = resumeGuestToken
        appSharedPref.userId = userId
    }

    override fun persistRefreshedTokens(
        accessToken: String,
        refreshToken: String,
        resumeGuestToken: String
    ) {
        appSharedPref.accessToken = accessToken
        appSharedPref.refreshToken = refreshToken
        if (resumeGuestToken.isNotBlank()) {
            appSharedPref.resumeGuestToken = resumeGuestToken
        }
    }

    override fun clearAuth(clearResumeGuestToken: Boolean) {
        appSharedPref.accessToken = ""
        appSharedPref.refreshToken = ""
        if (clearResumeGuestToken) {
            appSharedPref.resumeGuestToken = ""
        }
        appSharedPref.userId = ""
    }
}
