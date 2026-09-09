package com.pegas.aura.aigirlfriend.soul.domain.model.promo

import com.pegas.aura.aigirlfriend.soul.domain.model.common.RawJsonPayload

data class RedeemPromoCodeResult(
    val success: Boolean,
    val coinsAwarded: Int,
    val newBalance: Int?,
    val vipStatus: RawJsonPayload?
)
