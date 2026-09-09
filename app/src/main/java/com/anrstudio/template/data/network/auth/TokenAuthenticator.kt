package com.pegas.aura.aigirlfriend.soul.data.network.auth

import com.pegas.aura.aigirlfriend.soul.data.auth.AuthInfoProvider
import com.pegas.aura.aigirlfriend.soul.data.network.model.auth.AuthSessionDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.auth.toTokenSession
import com.pegas.aura.aigirlfriend.soul.data.network.model.base.envelopeAdapter
import com.pegas.aura.aigirlfriend.soul.domain.model.auth.TokenSession
import com.squareup.moshi.Moshi
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.TimeUnit
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authInfoProvider: AuthInfoProvider,
    private val baseUrl: String
) : Authenticator {

    private val authPayloadFactory by lazy { AuthPayloadFactory(authInfoProvider) }
    private val moshi by lazy { Moshi.Builder().build() }
    private val refreshAdapter by lazy { moshi.envelopeAdapter<AuthSessionDto>() }
    private val refreshClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val path = response.request.url.encodedPath
        if (AuthRoute.isGuest(path) || AuthRoute.isRefresh(path) || responseCount(response) >= 2) {
            return null
        }

        val previousAccessToken = authInfoProvider.accessToken

        synchronized(this) {
            val currentAccessToken = authInfoProvider.accessToken
            if (currentAccessToken != previousAccessToken && currentAccessToken.isNotBlank()) {
                return retryWith(response, currentAccessToken)
            }

            val refreshed = refreshTokens()
            if (refreshed != null) {
                authInfoProvider.persistRefreshedTokens(
                    accessToken = refreshed.accessToken,
                    refreshToken = refreshed.refreshToken,
                    resumeGuestToken = refreshed.resumeGuestToken
                )
                return retryWith(response, refreshed.accessToken)
            }

            val guestSession = guestLoginSync()
            if (guestSession != null) {
                authInfoProvider.persistGuestSession(
                    accessToken = guestSession.accessToken.orEmpty(),
                    refreshToken = guestSession.refreshToken.orEmpty(),
                    resumeGuestToken = guestSession.resumeGuestToken.orEmpty(),
                    userId = guestSession.user?.id.orEmpty()
                )
                return retryWith(response, guestSession.accessToken.orEmpty())
            }

            authInfoProvider.clearAuth(clearResumeGuestToken = false)
            return null
        }
    }

    private fun refreshTokens(): TokenSession? {
        val refreshToken = authInfoProvider.refreshToken
        if (refreshToken.isBlank()) return null
        val bearer = authPayloadFactory.buildRefreshBearer("POST", AuthRoute.REFRESH, refreshToken) ?: return null
        val request = Request.Builder()
            .url(baseUrl.trimEnd('/') + "/auth/refresh")
            .post("{}".toRequestBody("application/json".toMediaType()))
            .header("Authorization", "Bearer $bearer")
            .header("X-Package-Name", authInfoProvider.packageName)
            .build()

        return runCatching {
            refreshClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return null
                val raw = response.body?.string().orEmpty()
                refreshAdapter.fromJson(raw)?.data?.toTokenSession()
            }
        }.onFailure { Timber.w(it) }.getOrNull()
    }

    private fun retryWith(response: Response, accessToken: String): Request? {
        if (accessToken.isBlank()) return null
        val path = response.request.url.encodedPath
        val bearer = authPayloadFactory.buildAccessBearer(response.request.method, normalizePath(path), accessToken) ?: return null
        return response.request.newBuilder()
            .header("X-Package-Name", authInfoProvider.packageName)
            .header("Authorization", "Bearer $bearer")
            .build()
    }

    private fun normalizePath(path: String): String = if (path.startsWith("/api/")) path else "/api/v1$path"

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }

    private fun guestLoginSync(): AuthSessionDto? {
        val payload = authPayloadFactory.buildGuestBearer(
            method = "POST",
            path = AuthRoute.GUEST_LOGIN,
            displayName = authInfoProvider.clientId
        ) ?: return null

        val request = Request.Builder()
            .url(baseUrl.trimEnd('/') + "/auth/guest")
            .post("{}".toRequestBody("application/json".toMediaType()))
            .header("Authorization", "Bearer $payload")
            .header("X-Package-Name", authInfoProvider.packageName)
            .build()

        return runCatching {
            refreshClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return null
                val raw = response.body?.string().orEmpty()
                refreshAdapter.fromJson(raw)?.data
            }
        }.onFailure { Timber.w(it) }.getOrNull()
    }
}
