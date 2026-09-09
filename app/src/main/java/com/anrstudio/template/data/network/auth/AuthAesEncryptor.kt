package com.pegas.aura.aigirlfriend.soul.data.network.auth

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AuthAesEncryptor {

    fun encryptPayload(payloadJson: String, certificate: String): String? {
        return runCatching {
            val ivBytes = ByteArray(16)
            SecureRandom().nextBytes(ivBytes)

            encryptWithIvBytes(payloadJson, certificate, ivBytes)
        }.getOrNull()
    }

    private fun encryptWithIvBytes(payloadJson: String, certificate: String, ivBytes: ByteArray): String {
        val keyBytes = MessageDigest.getInstance("SHA-256")
            .digest(certificate.trim().toByteArray(Charsets.UTF_8))
        val secretKeySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(ivBytes))

        val encrypted = cipher.doFinal(payloadJson.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(ivBytes + encrypted, Base64.NO_WRAP)
    }
}
