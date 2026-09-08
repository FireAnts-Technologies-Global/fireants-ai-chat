package com.anrstudio.template.domain.repository

import com.anrstudio.template.domain.model.coins.AdsReward
import com.anrstudio.template.domain.model.coins.CoinBalance
import com.anrstudio.template.domain.model.coins.CoinCheckInClaim
import com.anrstudio.template.domain.model.coins.CoinCheckInState
import com.anrstudio.template.domain.model.coins.CoinPackage
import com.anrstudio.template.domain.model.coins.CoinTransactionPage
import com.anrstudio.template.domain.model.coins.WatchAdInput
import com.anrstudio.template.domain.model.coins.WatchAdResult
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PaginationQuery

interface CoinsRepository {
    suspend fun getBalance(): AppResult<CoinBalance>

    suspend fun getCheckInState(): AppResult<CoinCheckInState>

    suspend fun claimCheckIn(): AppResult<CoinCheckInClaim>

    suspend fun getPackages(): AppResult<List<CoinPackage>>

    suspend fun getTransactions(query: PaginationQuery): AppResult<CoinTransactionPage>

    suspend fun watchAd(input: WatchAdInput): AppResult<WatchAdResult>

    suspend fun getAdsReward(): AppResult<AdsReward>
}
