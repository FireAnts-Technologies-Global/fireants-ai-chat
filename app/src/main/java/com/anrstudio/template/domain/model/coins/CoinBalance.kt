package com.anrstudio.template.domain.model.coins

data class CoinBalance(
    val balance: Int,
    val enabled: Boolean,
    val chatCost: Int,
    val ads: CoinAdsBalance,
    val checkIn: CoinCheckInState
)
