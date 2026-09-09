package com.pegas.aura.aigirlfriend.soul.domain.model.coins

data class CoinBalance(
    val balance: Int,
    val enabled: Boolean,
    val chatCost: Int,
    val ads: CoinAdsBalance,
    val checkIn: CoinCheckInState
)
