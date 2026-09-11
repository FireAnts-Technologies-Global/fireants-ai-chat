package com.pegas.yuki.virtual.chat.domain.repository

import com.pegas.yuki.virtual.chat.domain.model.coins.CoinBalance
import com.pegas.yuki.virtual.chat.domain.model.coins.CoinCheckInClaim
import com.pegas.yuki.virtual.chat.domain.model.coins.CoinCheckInState
import com.pegas.yuki.virtual.chat.domain.model.coins.CoinPackage
import com.pegas.yuki.virtual.chat.domain.model.coins.CoinTransactionPage
import com.pegas.yuki.virtual.chat.domain.model.coins.WatchAdInput
import com.pegas.yuki.virtual.chat.domain.model.coins.WatchAdResult
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery

interface CoinsRepository {
    suspend fun getBalance(): AppResult<CoinBalance>

    suspend fun getCheckInState(): AppResult<CoinCheckInState>

    suspend fun claimCheckIn(): AppResult<CoinCheckInClaim>

    suspend fun getPackages(forceRefresh: Boolean = false): AppResult<List<CoinPackage>>

    fun getCachedPackages(): List<CoinPackage>? = null

    suspend fun getTransactions(query: PaginationQuery): AppResult<CoinTransactionPage>

    suspend fun watchAd(input: WatchAdInput): AppResult<WatchAdResult>

    suspend fun getAdsReward(): AppResult<com.pegas.yuki.virtual.chat.domain.model.coins.AdsReward>
}
