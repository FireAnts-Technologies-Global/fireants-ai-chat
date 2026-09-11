package com.pegas.yuki.virtual.chat.data.network.auth

import com.pegas.yuki.virtual.chat.data.auth.AuthInfoProvider
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

data class AuthBearerResult(
    val bearer: String?,
    val payloadJson: String?,
    val signature: String?
)

@Singleton
class AuthPayloadFactory @Inject constructor(
    private val authInfoProvider: AuthInfoProvider
) {

    private val moshi = Moshi.Builder().build()
    private val adapter = moshi.adapter(Map::class.java)

    fun buildGuestBearerResult(method: String, path: String, displayName: String? = null): AuthBearerResult {
        val payload = linkedMapOf<String, Any?>(
            "timestamp" to System.currentTimeMillis(),
            "method" to method,
            "path" to path,
            "clientId" to authInfoProvider.clientId,
            "pushToken" to authInfoProvider.pushToken
        )
        if (!displayName.isNullOrBlank()) payload["displayName"] = displayName
        if (authInfoProvider.resumeGuestToken.isNotBlank()) {
            payload["resumeGuestToken"] = authInfoProvider.resumeGuestToken
        }
        return encryptWithResult(payload)
    }

    fun buildRefreshBearerResult(method: String, path: String, refreshToken: String = authInfoProvider.refreshToken): AuthBearerResult {
        if (refreshToken.isBlank()) return AuthBearerResult(null, null, null)
        return encryptWithResult(
            linkedMapOf(
                "refreshToken" to refreshToken,
                "timestamp" to System.currentTimeMillis(),
                "method" to method,
                "path" to path
            )
        )
    }

    fun buildAccessBearerResult(method: String, path: String, accessToken: String = authInfoProvider.accessToken): AuthBearerResult {
        if (accessToken.isBlank()) return AuthBearerResult(null, null, null)
        return encryptWithResult(
            linkedMapOf(
                "accessToken" to accessToken,
                "timestamp" to System.currentTimeMillis(),
                "method" to method,
                "path" to path
            )
        )
    }

    fun buildGuestBearer(method: String, path: String, displayName: String? = null): String? =
        buildGuestBearerResult(method, path, displayName).bearer

    fun buildRefreshBearer(method: String, path: String, refreshToken: String = authInfoProvider.refreshToken): String? =
        buildRefreshBearerResult(method, path, refreshToken).bearer

    fun buildAccessBearer(method: String, path: String, accessToken: String = authInfoProvider.accessToken): String? =
        buildAccessBearerResult(method, path, accessToken).bearer

    @Volatile
    var lastPayloadJson: String? = null

    @Volatile
    var lastSignature: String? = null

    @Volatile
    var lastEncryptedBearer: String? = null

    private fun encryptWithResult(payload: Map<String, Any?>): AuthBearerResult {
        val json = adapter.toJson(payload)
        val context = com.pegas.yuki.virtual.chat.app.GlobalApp.instance
        val signature = AppSignatureHelper.getSignatureHash(context)

        lastPayloadJson = json
        lastSignature = signature

        if (signature.isNullOrBlank() || signature == "null" || signature == "error") {
            lastEncryptedBearer = null
            return AuthBearerResult(null, json, signature)
        }
        val encrypted = AuthAesEncryptor.encryptPayload(json, signature)
        lastEncryptedBearer = encrypted
        return AuthBearerResult(encrypted, json, signature)
    }
}
