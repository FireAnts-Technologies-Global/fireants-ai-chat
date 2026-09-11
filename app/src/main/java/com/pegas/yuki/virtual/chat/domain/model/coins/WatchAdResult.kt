package com.pegas.yuki.virtual.chat.domain.model.coins

data class WatchAdResult(
    val balance: Int,
    val credited: Int,
    val adsWatchedToday: Int,
    val adsRemainingToday: Int
)
