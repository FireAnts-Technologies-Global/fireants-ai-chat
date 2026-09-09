package com.pegas.aura.aigirlfriend.soul.data.network.auth

import com.pegas.aura.aigirlfriend.soul.data.auth.AuthInfoProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authInfoProvider: AuthInfoProvider
) : Interceptor {

    private val authPayloadFactory by lazy { AuthPayloadFactory(authInfoProvider) }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val builder = request.newBuilder()
            .header("X-Package-Name", authInfoProvider.packageName)

        val bearer = when {
            AuthRoute.isAppConfig(path) -> null
            AuthRoute.isGuest(path) -> authPayloadFactory.buildGuestBearer(
                method = request.method,
                path = AuthRoute.GUEST_LOGIN,
                displayName = authInfoProvider.clientId
            )
            AuthRoute.isRefresh(path) -> authPayloadFactory.buildRefreshBearer(request.method, AuthRoute.REFRESH)
            else -> authPayloadFactory.buildAccessBearer(request.method, normalizePath(path))
        }

        if (!bearer.isNullOrBlank()) {
            builder.header("Authorization", "Bearer $bearer")
        }

        builder.tag(
            AuthDebugInfo::class.java,
            AuthDebugInfo(
                payloadJson = authPayloadFactory.lastPayloadJson,
                signature = authPayloadFactory.lastSignature,
                bearerLength = bearer?.length
            )
        )

        return chain.proceed(builder.build())
    }

    private fun normalizePath(path: String): String {
        val normalizedPath = if (path.startsWith("/api/")) path else "/api/v1$path"
        return if (normalizedPath.length > 1 && normalizedPath.endsWith("/")) {
            normalizedPath.dropLast(1)
        } else {
            normalizedPath
        }
    }
}
