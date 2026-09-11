package com.pegas.yuki.virtual.chat.domain.model.revenuecat

data class RevenueCatStoreProduct(
    val id: String,
    val title: String,
    val description: String,
    val priceFormatted: String,
    val priceAmountMicros: Long,
    val currencyCode: String,
    val type: String,
    val period: String?
)
