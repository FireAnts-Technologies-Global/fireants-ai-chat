package com.pegas.aura.aigirlfriend.soul.data.network.interceptor

import android.content.Context
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

        if (!response.isSuccessful) {
            writeErrorLog(request, response)
        }
        return response
    }

    private fun writeErrorLog(request: okhttp3.Request, response: Response) {
        try {
            val file = File(context.filesDir, "api_error_logs.txt")
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
            val time = sdf.format(Date())
            val responseBody = response.peekBody(Long.MAX_VALUE).string()
            val authDebugInfo = request.tag(AuthDebugInfo::class.java)
            val requestId = response.header("x-request-id")

            val logStr = """
                |--- [$time] ---
                |Method: ${request.method}
                |URL: ${request.url}
                |Path: ${request.url.encodedPath}
                |Response Code: ${response.code}
                |Request ID: ${requestId.orEmpty()}
                |Auth Payload JSON:
                |${authDebugInfo?.payloadJson.redactAuthPayload()}
                |Auth Signature: ${authDebugInfo?.signature.orEmpty()}
                |Bearer Length: ${authDebugInfo?.bearerLength ?: 0}
                |Response Body:
                |$responseBody
                |----------------------
                |
            """.trimMargin()

            FileWriter(file, true).use { writer ->
                writer.append(logStr)
            }
            Timber.d("ErrorFileLoggingInterceptor: Logged API error to ${file.absolutePath}")
        } catch (e: Exception) {
            Timber.e(e, "ErrorFileLoggingInterceptor: Failed to write log")
        }
    }

    private fun String?.redactAuthPayload(): String {
        if (this.isNullOrBlank()) return ""
        return this
            .replace(Regex("\"accessToken\"\\s*:\\s*\"[^\"]*\""), "\"accessToken\":\"***\"")
            .replace(Regex("\"refreshToken\"\\s*:\\s*\"[^\"]*\""), "\"refreshToken\":\"***\"")
            .replace(
                Regex("\"resumeGuestToken\"\\s*:\\s*\"[^\"]*\""),
                "\"resumeGuestToken\":\"***\""
            )
    }
}
