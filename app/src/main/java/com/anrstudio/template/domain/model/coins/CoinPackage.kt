package com.anrstudio.template.domain.model.coins

data class CoinPackage(
    val id: String,
    val code: String,
    val storeProductId: String,
    val platform: String,
    val coinAmount: Int,
    val bonusCoins: Int,
    val totalCoins: Int,
    val displayName: String,
    val badge: String?,
    val sortOrder: Int,
    val isPopular: Boolean
)
