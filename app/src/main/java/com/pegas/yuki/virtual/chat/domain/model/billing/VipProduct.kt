package com.pegas.yuki.virtual.chat.domain.model.billing

data class VipProduct(
    val id: String,
    val code: String,
    val storeProductId: String,
    val platform: String,
    val period: String,
    val displayName: String,
    val badge: String?,
    val sortOrder: Int,
)
