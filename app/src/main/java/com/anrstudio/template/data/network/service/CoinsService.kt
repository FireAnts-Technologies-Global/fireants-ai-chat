package com.pegas.aura.aigirlfriend.soul.data.network.service

import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.AdsRewardEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinBalanceEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinCheckInClaimEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinCheckInStateEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinPackagesEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinTransactionsEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.WatchAdEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.WatchAdRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface CoinsService {

    @GET("coins/balance")
    suspend fun getBalance(): CoinBalanceEnvelopeDto

    @GET("coins/check-in/state")
    suspend fun getCheckInState(): CoinCheckInStateEnvelopeDto

    @POST("coins/check-in/claim")
    suspend fun claimCheckIn(): CoinCheckInClaimEnvelopeDto

    @GET("coins/packages")
    suspend fun getPackages(): CoinPackagesEnvelopeDto

    @GET("coins/transactions")
    suspend fun getTransactions(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): CoinTransactionsEnvelopeDto

    @Headers("Content-Type: application/json")
    @POST("coins/ads/watch")
    suspend fun watchAd(
        @Body body: WatchAdRequestDto
    ): WatchAdEnvelopeDto

    @GET("coins/ads/reward")
    suspend fun getAdsReward(): AdsRewardEnvelopeDto
}
