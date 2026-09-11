package com.pegas.yuki.virtual.chat.domain.model.coins

import com.pegas.yuki.virtual.chat.domain.model.common.RawJsonPayload

data class CoinTransaction(
    val id: String,
    val userId: String,
    val type: String,
    val amount: Int,
    val balanceAfter: Int,
    val idempotencyKey: String,
    val referenceType: String?,
    val referenceId: String?,
    val meta: RawJsonPayload?,
    val createdAt: String
)
