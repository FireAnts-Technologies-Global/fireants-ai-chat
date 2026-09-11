package com.pegas.yuki.virtual.chat.domain.model.promo

import com.pegas.yuki.virtual.chat.domain.model.common.RawJsonPayload

data class RedeemPromoCodeResult(
    val success: Boolean,
    val coinsAwarded: Int,
    val newBalance: Int?,
    val vipStatus: RawJsonPayload?
)
