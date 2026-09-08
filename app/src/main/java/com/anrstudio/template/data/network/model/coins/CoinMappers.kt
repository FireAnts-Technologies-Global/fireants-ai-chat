package com.anrstudio.template.data.network.model.coins

import com.anrstudio.template.data.network.model.base.toDomain
import com.anrstudio.template.domain.model.coins.AdsReward
import com.anrstudio.template.domain.model.coins.CoinAdsBalance
import com.anrstudio.template.domain.model.coins.CoinBalance
import com.anrstudio.template.domain.model.coins.CoinCheckInClaim
import com.anrstudio.template.domain.model.coins.CoinCheckInState
import com.anrstudio.template.domain.model.coins.CoinPackage
import com.anrstudio.template.domain.model.coins.CoinTransaction
import com.anrstudio.template.domain.model.coins.CoinTransactionPage
import com.anrstudio.template.domain.model.coins.WatchAdInput
import com.anrstudio.template.domain.model.coins.WatchAdResult
import com.anrstudio.template.domain.model.common.RawJsonPayload
import com.squareup.moshi.Moshi

private val moshi by lazy { Moshi.Builder().build() }
private val mapAdapter by lazy { moshi.adapter(Map::class.java) }

fun CoinBalanceDto.toDomain(): CoinBalance = CoinBalance(
    balance = balance ?: 0,
    enabled = enabled == true,
    chatCost = chatCost ?: 0,
    ads = ads?.toDomain() ?: CoinAdsBalance(false, 0, 0, 0, 0, false, false),
    checkIn = checkIn?.toDomain() ?: CoinCheckInState(false, false, 0, 0, 0, emptyList())
)

fun CoinAdsBalanceDto.toDomain(): CoinAdsBalance = CoinAdsBalance(
    enabled = enabled == true,
    watchedToday = watchedToday ?: 0,
    remainingToday = remainingToday ?: 0,
    coinsPerView = coinsPerView ?: 0,
    dailyLimit = dailyLimit ?: 0,
    ssvEnabled = ssvEnabled == true,
    requireSsv = requireSsv == true
)

fun CoinCheckInStateDto.toDomain(): CoinCheckInState = CoinCheckInState(
    enabled = enabled == true,
    claimedToday = claimedToday == true,
    dayIndex = dayIndex ?: 0,
    cycleDays = cycleDays ?: 0,
    reward = reward ?: 0,
    rewards = rewards.orEmpty(),
    utcDay = utcDay,
    vipBonus = vipBonus ?: 0
)

fun CoinCheckInClaimDto.toDomain(): CoinCheckInClaim = CoinCheckInClaim(
    balance = balance ?: 0,
    credited = credited ?: 0,
    dayIndex = dayIndex ?: 0,
    cycleDays = cycleDays ?: 0,
    reward = reward ?: 0,
    vipBonus = vipBonus ?: 0
)

fun CoinPackageDto.toDomain(): CoinPackage = CoinPackage(
    id = id.orEmpty(),
    code = code.orEmpty(),
    storeProductId = storeProductId.orEmpty(),
    platform = platform.orEmpty(),
    coinAmount = coinAmount ?: 0,
    bonusCoins = bonusCoins ?: 0,
    totalCoins = totalCoins ?: 0,
    displayName = displayName.orEmpty(),
    badge = badge,
    sortOrder = sortOrder ?: 0,
    isPopular = isPopular == true
)

fun CoinTransactionPageDto.toDomain(): CoinTransactionPage = toDomain { it.toDomain() }

fun CoinTransactionDto.toDomain(): CoinTransaction = CoinTransaction(
    id = id.orEmpty(),
    userId = userId.orEmpty(),
    type = type.orEmpty(),
    amount = amount ?: 0,
    balanceAfter = balanceAfter ?: 0,
    idempotencyKey = idempotencyKey.orEmpty(),
    referenceType = referenceType,
    referenceId = referenceId,
    meta = meta?.let { RawJsonPayload(json = mapAdapter.toJson(it)) },
    createdAt = createdAt.orEmpty()
)

fun WatchAdInput.toRequestDto(): WatchAdRequestDto = WatchAdRequestDto(
    impressionId = impressionId,
    adNetwork = adNetwork
)

fun WatchAdResultDto.toDomain(): WatchAdResult = WatchAdResult(
    balance = balance ?: 0,
    credited = credited ?: 0,
    adsWatchedToday = adsWatchedToday ?: 0,
    adsRemainingToday = adsRemainingToday ?: 0
)

fun AdsRewardDto.toDomain(): AdsReward =
    AdsReward(
        coinsPerView = coinsPerView ?: 0,
        remainingToday = remainingToday ?: 0
    )
