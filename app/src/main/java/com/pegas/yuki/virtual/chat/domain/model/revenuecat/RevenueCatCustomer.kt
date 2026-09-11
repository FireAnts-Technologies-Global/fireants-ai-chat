package com.pegas.yuki.virtual.chat.domain.model.revenuecat

data class RevenueCatCustomer(
    val appUserId: String,
    val originalAppUserId: String,
    val activeEntitlements: List<String>,
    val originalPurchaseDate: String?,
    val firstSeen: String?,
    val latestExpirationDate: String?
)
