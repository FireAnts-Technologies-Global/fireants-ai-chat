package com.anrstudio.template.data.network.auth

import android.content.Context
import com.anrstudio.signaturelib.ANRSignatureValidator

object AppSignatureHelper {
    @Volatile
    private var cachedSignatureHash: String? = null

    fun getSignatureHash(context: Context): String? {
        return cachedSignatureHash ?: synchronized(this) {
            cachedSignatureHash ?: ANRSignatureValidator.getSignatureHash(context)?.also {
                if (it.isNotBlank() && it != "null" && it != "error") {
                    cachedSignatureHash = it
                }
            }
        }
    }
}
