package com.pegas.aura.aigirlfriend.soul.data.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicMessageKey
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

internal suspend fun <T> apiResult(
    operation: String,
    block: suspend () -> T
): AppResult<T> = try {
    AppResult.Success(block())
} catch (throwable: Throwable) {
    Timber.w(throwable, "Repository operation failed: %s", operation)
    AppResult.Failure(throwable.toPublicError())
}

private fun Throwable.toPublicError(): PublicError = when (this) {
    is IOException -> PublicError(PublicMessageKey.NETWORK_UNAVAILABLE)
    is HttpException -> {
        var customMsg: String? = null
        var errorCode: String? = null
        try {
            val errorBodyStr = response()?.errorBody()?.string()
            if (!errorBodyStr.isNullOrBlank()) {
                val jsonObject = org.json.JSONObject(errorBodyStr)
                customMsg = jsonObject.optString("message").takeIf { it.isNotBlank() }
                errorCode = jsonObject.optString("code").takeIf { it.isNotBlank() }
            }
        } catch (e: Exception) {
            // Ignore parsing errors
        }

        when (code()) {
            401, 403 -> PublicError(PublicMessageKey.AUTH_SESSION_EXPIRED, customMessage = customMsg)
            402 -> if (errorCode == "INSUFFICIENT_COINS") {
                PublicError(PublicMessageKey.INSUFFICIENT_COINS, customMessage = customMsg)
            } else {
                PublicError(PublicMessageKey.GENERIC_ERROR, customMessage = customMsg)
            }
            else -> PublicError(PublicMessageKey.GENERIC_ERROR, customMessage = customMsg)
        }
    }
    else -> PublicError(PublicMessageKey.GENERIC_ERROR)
}
