package com.pegas.aura.aigirlfriend.soul.domain.model.coins

data class CoinAdsBalance(
    val enabled: Boolean,
    val watchedToday: Int,
    val remainingToday: Int,
    val coinsPerView: Int,
    val dailyLimit: Int,
    val ssvEnabled: Boolean,
    val requireSsv: Boolean
)
