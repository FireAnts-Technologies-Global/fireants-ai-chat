package com.anrstudio.template.domain.model.common

data class PublicError(
    val messageKey: PublicMessageKey,
    val isUserCancellation: Boolean = false,
    val customMessage: String? = null
)

enum class PublicMessageKey {
    NETWORK_UNAVAILABLE,
    AUTH_SESSION_EXPIRED,
    AUTH_UNAVAILABLE,
    GENERIC_ERROR,
    INSUFFICIENT_COINS
}
