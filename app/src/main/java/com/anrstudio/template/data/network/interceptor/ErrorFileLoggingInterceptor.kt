package com.pegas.aura.aigirlfriend.soul.data.network.interceptor

import android.content.Context
import android.util.Log
import com.pegas.aura.aigirlfriend.soul.BuildConfig
import com.pegas.aura.aigirlfriend.soul.data.network.auth.AuthAesEncryptor
import com.pegas.aura.aigirlfriend.soul.data.network.auth.AuthDebugInfo
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ErrorFileLoggingInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!BuildConfig.DEBUG) {
            return response
        }

        if (!response.isSuccessful) {
            writeErrorLog(request, response)
        }
        return response
    }

    private fun writeErrorLog(request: okhttp3.Request, response: Response) {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
            val time = sdf.format(Date())
            val responseBody = response.peekBody(1024 * 1024).string()
            val authDebugInfo = request.tag(AuthDebugInfo::class.java)
            val requestId = response.header("x-request-id").orEmpty()
            val bearerToken = authDebugInfo?.bearerToken
                ?: request.header("Authorization")?.removePrefix("Bearer ")?.trim()

            val isDecryptError = responseBody.contains("Decrypted payload", ignoreCase = true) ||
                responseBody.contains("not valid JSON", ignoreCase = true)

            var clientSelfDecryptInfo = "N/A"
            if (!bearerToken.isNullOrBlank() && !authDebugInfo?.signature.isNullOrBlank()) {
                val selfDecrypt = AuthAesEncryptor.decryptPayload(bearerToken, authDebugInfo.signature)
                clientSelfDecryptInfo = if (selfDecrypt.isSuccess) {
                    val text = selfDecrypt.getOrNull().orEmpty()
                    val matches = text == authDebugInfo.payloadJson
                    "SUCCESS (matches original: $matches)\nDecrypted: $text"
                } else {
                    "FAILED (${selfDecrypt.exceptionOrNull()?.message})"
                }
            }

            val generalLogFile = File(context.filesDir, "api_error_logs.txt")
            val decryptErrorFile = File(context.filesDir, "api_decrypt_errors.log")

            val generalLogStr = """
                |--- [$time] ---
                |Method: ${request.method}
                |URL: ${request.url}
                |Path: ${request.url.encodedPath}
                |Response Code: ${response.code}
                |Request ID: $requestId
                |Auth Payload JSON: ${authDebugInfo?.payloadJson.orEmpty()}
                |Auth Signature: ${authDebugInfo?.signature.orEmpty()}
                |Bearer Length: ${bearerToken?.length ?: 0}
                |Self-Decrypt Test: $clientSelfDecryptInfo
                |Response Body:
                |$responseBody
                |----------------------
                |
            """.trimMargin()

            appendToFile(generalLogFile, generalLogStr)

            if (isDecryptError) {
                val decryptLogStr = """
                    |================================================================================
                    |[$time] DECRYPT PAYLOAD ERROR DETECTED
                    |URL: ${request.method} ${request.url}
                    |Response Code: ${response.code}
                    |Request ID: $requestId
                    |Response Body: $responseBody
                    |Auth Signature Used: ${authDebugInfo?.signature.orEmpty()}
                    |Auth Payload (Plaintext):
                    |${authDebugInfo?.payloadJson.orEmpty()}
                    |Sent Bearer Token:
                    |$bearerToken
                    |Client Self-Decrypt Result:
                    |$clientSelfDecryptInfo
                    |Log File Path: ${decryptErrorFile.absolutePath}
                    |================================================================================
                    |
                """.trimMargin()

                appendToFile(decryptErrorFile, decryptLogStr)

                Log.e("API_DECRYPT_TRACE", decryptLogStr)
                Timber.tag("API_DECRYPT_TRACE").e(decryptLogStr)
            } else {
                Timber.d("ErrorFileLoggingInterceptor: Logged API error to ${generalLogFile.absolutePath}")
            }
        } catch (e: Exception) {
            Timber.e(e, "ErrorFileLoggingInterceptor: Failed to write log")
        }
    }

    @Synchronized
    private fun appendToFile(file: File, content: String) {
        if (file.exists() && file.length() > 5 * 1024 * 1024) {
            val backupFile = File(file.parentFile, "${file.name}.bak")
            if (backupFile.exists()) backupFile.delete()
            file.renameTo(backupFile)
        }
        FileWriter(file, true).use { writer ->
            writer.append(content)
        }
    }
}
