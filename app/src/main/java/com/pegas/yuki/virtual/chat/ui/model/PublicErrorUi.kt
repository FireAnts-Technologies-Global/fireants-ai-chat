package com.pegas.yuki.virtual.chat.ui.model

import androidx.annotation.StringRes
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.domain.model.common.PublicMessageKey

@StringRes
fun PublicError.messageRes(): Int = when (messageKey) {
    PublicMessageKey.NETWORK_UNAVAILABLE -> R.string.error_network_unavailable
    PublicMessageKey.AUTH_SESSION_EXPIRED -> R.string.error_auth_session_expired
    PublicMessageKey.AUTH_UNAVAILABLE -> R.string.error_auth_unavailable
    PublicMessageKey.INSUFFICIENT_COINS -> R.string.error_insufficient_coins
    PublicMessageKey.GENERIC_ERROR -> R.string.error_generic
}

@androidx.compose.runtime.Composable
fun PublicError.asString(): String {
    return customMessage ?: androidx.compose.ui.res.stringResource(id = messageRes())
}

fun PublicError.asString(context: android.content.Context): String {
    return customMessage ?: context.getString(messageRes())
}
