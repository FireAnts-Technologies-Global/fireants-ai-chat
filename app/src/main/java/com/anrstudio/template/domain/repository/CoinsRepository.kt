package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinBalance
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinCheckInClaim
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinCheckInState
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinPackage
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinTransactionPage
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.WatchAdInput
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.WatchAdResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery

interface CoinsRepository {
    suspend fun getBalance(): AppResult<CoinBalance>

    suspend fun getCheckInState(): AppResult<CoinCheckInState>

    suspend fun claimCheckIn(): AppResult<CoinCheckInClaim>

    suspend fun getPackages(): AppResult<List<CoinPackage>>

    suspend fun getTransactions(query: PaginationQuery): AppResult<CoinTransactionPage>

    suspend fun watchAd(input: WatchAdInput): AppResult<WatchAdResult>

    suspend fun getAdsReward(): AppResult<com.pegas.aura.aigirlfriend.soul.domain.model.coins.AdsReward>
}
