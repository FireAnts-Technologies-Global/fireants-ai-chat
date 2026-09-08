package com.anrstudio.template.data.network.model.coins


import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.anrstudio.template.data.network.model.base.PageDto
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
    @param:Json(name = "balance") val balance: Int? = null,
    @param:Json(name = "enabled") val enabled: Boolean? = null,
    @param:Json(name = "chatCost") val chatCost: Int? = null,
    @param:Json(name = "ads") val ads: CoinAdsBalanceDto? = null,
    @param:Json(name = "checkIn") val checkIn: CoinCheckInStateDto? = null
)

@JsonClass(generateAdapter = true)
data class CoinAdsBalanceDto(
    @param:Json(name = "enabled") val enabled: Boolean? = null,
    @param:Json(name = "watchedToday") val watchedToday: Int? = null,
    @param:Json(name = "remainingToday") val remainingToday: Int? = null,
    @param:Json(name = "coinsPerView") val coinsPerView: Int? = null,
    @param:Json(name = "dailyLimit") val dailyLimit: Int? = null,
    @param:Json(name = "ssvEnabled") val ssvEnabled: Boolean? = null,
    @param:Json(name = "requireSsv") val requireSsv: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class CoinCheckInStateDto(
    @param:Json(name = "enabled") val enabled: Boolean? = null,
    @param:Json(name = "claimedToday") val claimedToday: Boolean? = null,
    @param:Json(name = "dayIndex") val dayIndex: Int? = null,
    @param:Json(name = "cycleDays") val cycleDays: Int? = null,
    @param:Json(name = "reward") val reward: Int? = null,
    @param:Json(name = "rewards") val rewards: List<Int>? = null,
    @param:Json(name = "utcDay") val utcDay: String? = null,
    @param:Json(name = "vipBonus") val vipBonus: Int? = null
)

@JsonClass(generateAdapter = true)
data class CoinCheckInClaimDto(
    @param:Json(name = "balance") val balance: Int? = null,
    @param:Json(name = "credited") val credited: Int? = null,
    @param:Json(name = "dayIndex") val dayIndex: Int? = null,
    @param:Json(name = "cycleDays") val cycleDays: Int? = null,
    @param:Json(name = "reward") val reward: Int? = null,
    @param:Json(name = "vipBonus") val vipBonus: Int? = null
)

@JsonClass(generateAdapter = true)
data class CoinPackageDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "code") val code: String? = null,
    @param:Json(name = "storeProductId") val storeProductId: String? = null,
    @param:Json(name = "platform") val platform: String? = null,
    @param:Json(name = "coinAmount") val coinAmount: Int? = null,
    @param:Json(name = "bonusCoins") val bonusCoins: Int? = null,
    @param:Json(name = "totalCoins") val totalCoins: Int? = null,
    @param:Json(name = "displayName") val displayName: String? = null,
    @param:Json(name = "badge") val badge: String? = null,
    @param:Json(name = "sortOrder") val sortOrder: Int? = null,
    @param:Json(name = "isPopular") val isPopular: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class CoinTransactionDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "userId") val userId: String? = null,
    @param:Json(name = "type") val type: String? = null,
    @param:Json(name = "amount") val amount: Int? = null,
    @param:Json(name = "balanceAfter") val balanceAfter: Int? = null,
    @param:Json(name = "idempotencyKey") val idempotencyKey: String? = null,
    @param:Json(name = "referenceType") val referenceType: String? = null,
    @param:Json(name = "referenceId") val referenceId: String? = null,
    @param:Json(name = "meta") val meta: Map<String, Any?>? = null,
    @param:Json(name = "createdAt") val createdAt: String? = null
)

@JsonClass(generateAdapter = true)
data class WatchAdRequestDto(
    @param:Json(name = "impressionId") val impressionId: String,
    @param:Json(name = "adNetwork") val adNetwork: String
)

@JsonClass(generateAdapter = true)
data class WatchAdResultDto(
    @param:Json(name = "balance") val balance: Int? = null,
    @param:Json(name = "credited") val credited: Int? = null,
    @param:Json(name = "adsWatchedToday") val adsWatchedToday: Int? = null,
    @param:Json(name = "adsRemainingToday") val adsRemainingToday: Int? = null
)

@JsonClass(generateAdapter = true)
data class AdsRewardDto(
    @param:Json(name = "coinsPerView") val coinsPerView: Int? = null,
    @param:Json(name = "remainingToday") val remainingToday: Int? = null
)
