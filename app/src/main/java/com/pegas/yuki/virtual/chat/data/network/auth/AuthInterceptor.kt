package com.pegas.yuki.virtual.chat.data.network.auth

import com.pegas.yuki.virtual.chat.data.auth.AuthInfoProvider
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

        val authResult = when {
            AuthRoute.isAppConfig(path) -> null
            AuthRoute.isGuest(path) -> authPayloadFactory.buildGuestBearerResult(
                method = request.method,
                path = AuthRoute.GUEST_LOGIN,
                displayName = authInfoProvider.clientId
            )
            AuthRoute.isRefresh(path) -> authPayloadFactory.buildRefreshBearerResult(request.method, AuthRoute.REFRESH)
            else -> authPayloadFactory.buildAccessBearerResult(request.method, normalizePath(path))
        }

        val bearer = authResult?.bearer
        if (!bearer.isNullOrBlank()) {
            builder.header("Authorization", "Bearer $bearer")
        }

        builder.tag(
            AuthDebugInfo::class.java,
            AuthDebugInfo(
                payloadJson = authResult?.payloadJson,
                signature = authResult?.signature,
                bearerLength = bearer?.length,
                bearerToken = bearer
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
