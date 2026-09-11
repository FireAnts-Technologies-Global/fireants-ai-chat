package com.pegas.yuki.virtual.chat.domain.model.coins

data class CoinCheckInState(
    val enabled: Boolean,
    val claimedToday: Boolean,
    val dayIndex: Int,
    val cycleDays: Int,
    val reward: Int,
    val rewards: List<Int>,
    val utcDay: String? = null,
    val vipBonus: Int = 0
)
