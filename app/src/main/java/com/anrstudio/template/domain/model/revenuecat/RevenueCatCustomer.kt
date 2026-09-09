package com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat

data class RevenueCatCustomer(
    val appUserId: String,
    val originalAppUserId: String,
    val activeEntitlements: List<String>,
    val originalPurchaseDate: String?,
    val firstSeen: String?,
    val latestExpirationDate: String?
)
