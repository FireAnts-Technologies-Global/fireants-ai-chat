package com.pegas.aura.aigirlfriend.soul.data.network.model.promo

import com.pegas.aura.aigirlfriend.soul.domain.model.common.RawJsonPayload
import com.pegas.aura.aigirlfriend.soul.domain.model.promo.RedeemPromoCodeResult
import com.squareup.moshi.Moshi

private val moshi by lazy { Moshi.Builder().build() }
private val mapAdapter by lazy { moshi.adapter(Map::class.java) }

fun RedeemPromoCodeDto.toDomain(): RedeemPromoCodeResult = RedeemPromoCodeResult(
    success = success == true,
    coinsAwarded = coinsAwarded ?: 0,
    newBalance = newBalance,
    vipStatus = vipStatus?.let { RawJsonPayload(json = mapAdapter.toJson(it)) }
)
