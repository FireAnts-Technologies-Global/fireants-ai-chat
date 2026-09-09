package com.pegas.aura.aigirlfriend.soul.data.network.auth

import com.pegas.aura.aigirlfriend.soul.data.auth.AuthInfoProvider
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPayloadFactory @Inject constructor(
    private val authInfoProvider: AuthInfoProvider
) {

    private val moshi = Moshi.Builder().build()
    private val adapter = moshi.adapter(Map::class.java)

    fun buildGuestBearer(method: String, path: String, displayName: String? = null): String? {
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
        return encrypt(payload)
    }

    fun buildRefreshBearer(method: String, path: String, refreshToken: String = authInfoProvider.refreshToken): String? {
        if (refreshToken.isBlank()) return null
        return encrypt(
            linkedMapOf(
                "refreshToken" to refreshToken,
                "timestamp" to System.currentTimeMillis(),
                "method" to method,
                "path" to path
            )
        )
    }

    fun buildAccessBearer(method: String, path: String, accessToken: String = authInfoProvider.accessToken): String? {
        if (accessToken.isBlank()) return null
        return encrypt(
            linkedMapOf(
                "accessToken" to accessToken,
                "timestamp" to System.currentTimeMillis(),
                "method" to method,
                "path" to path
            )
        )
    }

    @Volatile
    var lastPayloadJson: String? = null

    @Volatile
    var lastSignature: String? = null

    @Volatile
    var lastEncryptedBearer: String? = null

    private fun encrypt(payload: Map<String, Any?>): String? {
        val json = adapter.toJson(payload)
        val context = com.pegas.aura.aigirlfriend.soul.app.GlobalApp.instance
        val signature = AppSignatureHelper.getSignatureHash(context)

        lastPayloadJson = json
        lastSignature = signature
        
        if (signature.isNullOrBlank() || signature == "null" || signature == "error") {
            lastEncryptedBearer = null
            return null
        }
        val encrypted = AuthAesEncryptor.encryptPayload(json, signature)
        lastEncryptedBearer = encrypted
        return encrypted
    }
}
