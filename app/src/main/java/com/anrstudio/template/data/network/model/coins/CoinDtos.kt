package com.pegas.aura.aigirlfriend.soul.data.network.model.coins

import com.pegas.aura.aigirlfriend.soul.data.network.model.base.ApiEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.base.PageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias CoinBalanceEnvelopeDto = ApiEnvelopeDto<CoinBalanceDto>
typealias CoinCheckInStateEnvelopeDto = ApiEnvelopeDto<CoinCheckInStateDto>
typealias CoinCheckInClaimEnvelopeDto = ApiEnvelopeDto<CoinCheckInClaimDto>
typealias CoinPackagesEnvelopeDto = ApiEnvelopeDto<List<CoinPackageDto>>
typealias CoinTransactionsEnvelopeDto = ApiEnvelopeDto<CoinTransactionPageDto>
typealias WatchAdEnvelopeDto = ApiEnvelopeDto<WatchAdResultDto>
typealias CoinTransactionPageDto = PageDto<CoinTransactionDto>
typealias AdsRewardEnvelopeDto = ApiEnvelopeDto<AdsRewardDto>

@JsonClass(generateAdapter = true)
data class CoinBalanceDto(
    @Json(name = "balance") val balance: Int? = null,
    @Json(name = "enabled") val enabled: Boolean? = null,
    @Json(name = "chatCost") val chatCost: Int? = null,
    @Json(name = "ads") val ads: CoinAdsBalanceDto? = null,
    @Json(name = "checkIn") val checkIn: CoinCheckInStateDto? = null
)

@JsonClass(generateAdapter = true)
data class CoinAdsBalanceDto(
    @Json(name = "enabled") val enabled: Boolean? = null,
    @Json(name = "watchedToday") val watchedToday: Int? = null,
    @Json(name = "remainingToday") val remainingToday: Int? = null,
    @Json(name = "coinsPerView") val coinsPerView: Int? = null,
    @Json(name = "dailyLimit") val dailyLimit: Int? = null,
    @Json(name = "ssvEnabled") val ssvEnabled: Boolean? = null,
    @Json(name = "requireSsv") val requireSsv: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class CoinCheckInStateDto(
    @Json(name = "enabled") val enabled: Boolean? = null,
    @Json(name = "claimedToday") val claimedToday: Boolean? = null,
    @Json(name = "dayIndex") val dayIndex: Int? = null,
    @Json(name = "cycleDays") val cycleDays: Int? = null,
    @Json(name = "reward") val reward: Int? = null,
    @Json(name = "rewards") val rewards: List<Int>? = null,
    @Json(name = "utcDay") val utcDay: String? = null,
    @Json(name = "vipBonus") val vipBonus: Int? = null
)

@JsonClass(generateAdapter = true)
data class CoinCheckInClaimDto(
    @Json(name = "balance") val balance: Int? = null,
    @Json(name = "credited") val credited: Int? = null,
    @Json(name = "dayIndex") val dayIndex: Int? = null,
    @Json(name = "cycleDays") val cycleDays: Int? = null,
    @Json(name = "reward") val reward: Int? = null,
    @Json(name = "vipBonus") val vipBonus: Int? = null
)

@JsonClass(generateAdapter = true)
data class CoinPackageDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "code") val code: String? = null,
    @Json(name = "storeProductId") val storeProductId: String? = null,
    @Json(name = "platform") val platform: String? = null,
    @Json(name = "coinAmount") val coinAmount: Int? = null,
    @Json(name = "bonusCoins") val bonusCoins: Int? = null,
    @Json(name = "totalCoins") val totalCoins: Int? = null,
    @Json(name = "displayName") val displayName: String? = null,
    @Json(name = "badge") val badge: String? = null,
    @Json(name = "sortOrder") val sortOrder: Int? = null,
    @Json(name = "isPopular") val isPopular: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class CoinTransactionDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "userId") val userId: String? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "amount") val amount: Int? = null,
    @Json(name = "balanceAfter") val balanceAfter: Int? = null,
    @Json(name = "idempotencyKey") val idempotencyKey: String? = null,
    @Json(name = "referenceType") val referenceType: String? = null,
    @Json(name = "referenceId") val referenceId: String? = null,
    @Json(name = "meta") val meta: Map<String, Any?>? = null,
    @Json(name = "createdAt") val createdAt: String? = null
)

@JsonClass(generateAdapter = true)
data class WatchAdRequestDto(
    @Json(name = "impressionId") val impressionId: String,
    @Json(name = "adNetwork") val adNetwork: String
)

@JsonClass(generateAdapter = true)
data class WatchAdResultDto(
    @Json(name = "balance") val balance: Int? = null,
    @Json(name = "credited") val credited: Int? = null,
    @Json(name = "adsWatchedToday") val adsWatchedToday: Int? = null,
    @Json(name = "adsRemainingToday") val adsRemainingToday: Int? = null
)

@JsonClass(generateAdapter = true)
data class AdsRewardDto(
    @Json(name = "coinsPerView") val coinsPerView: Int? = null,
    @Json(name = "remainingToday") val remainingToday: Int? = null
)
