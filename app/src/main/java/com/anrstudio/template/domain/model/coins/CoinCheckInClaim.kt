package com.pegas.aura.aigirlfriend.soul.domain.model.coins

data class CoinCheckInClaim(
    val balance: Int,
    val credited: Int,
    val dayIndex: Int,
    val cycleDays: Int,
    val reward: Int,
    val vipBonus: Int = 0
)
