package com.anrstudio.template.ui.model

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.anrstudio.template.domain.model.common.PublicError
import com.anrstudio.template.domain.model.common.PublicMessageKey
import com.pegas.aura.aigirlfriend.soul.R

@StringRes
fun PublicError.messageRes(): Int = when (messageKey) {
    PublicMessageKey.NETWORK_UNAVAILABLE -> R.string.error_network_unavailable
    PublicMessageKey.AUTH_SESSION_EXPIRED -> R.string.error_auth_session_expired
    PublicMessageKey.AUTH_UNAVAILABLE -> R.string.error_auth_unavailable
    PublicMessageKey.INSUFFICIENT_COINS -> R.string.error_insufficient_coins
    PublicMessageKey.GENERIC_ERROR -> R.string.error_generic
}

@Composable
fun PublicError.asString(): String {
    return customMessage ?: stringResource(id = messageRes())
}

fun PublicError.asString(context: Context): String {
    return customMessage ?: context.getString(messageRes())
}
