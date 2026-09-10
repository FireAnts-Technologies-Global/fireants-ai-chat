package com.pegas.aura.aigirlfriend.soul.data.repository

import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinBalanceEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinCheckInClaimEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinCheckInStateEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinPackagesEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.CoinTransactionsEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.WatchAdEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.toDomain
import com.pegas.aura.aigirlfriend.soul.data.network.model.coins.toRequestDto
import com.pegas.aura.aigirlfriend.soul.data.network.service.CoinsService
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinBalance
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinCheckInClaim
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinCheckInState
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinPackage
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinTransactionPage
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.WatchAdInput
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.WatchAdResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.CoinsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsRepositoryImpl @Inject constructor(
    private val coinsService: CoinsService,
    private val authRepository: AuthRepository
) : CoinsRepository {

    override suspend fun getBalance(): AppResult<CoinBalance> =
        apiResult("getBalance") {
            coinsService.getBalance().requireData().toDomain().also { balance ->
                authRepository.updateLocalCoinBalance(balance.balance)
            }
        }

    override suspend fun getCheckInState(): AppResult<CoinCheckInState> =
        apiResult("getCheckInState") {
            coinsService.getCheckInState().requireData().toDomain()
        }

    override suspend fun claimCheckIn(): AppResult<CoinCheckInClaim> =
        apiResult("claimCheckIn") {
            coinsService.claimCheckIn().requireData().toDomain().also { claim ->
                authRepository.updateLocalCoinBalance(claim.balance)
            }
        }

    private var cachedPackages: List<CoinPackage>? = null

    override fun getCachedPackages(): List<CoinPackage>? = cachedPackages

    override suspend fun getPackages(forceRefresh: Boolean): AppResult<List<CoinPackage>> {
        if (!forceRefresh) {
            cachedPackages?.let { return AppResult.Success(it) }
        }
        return apiResult("getPackages") {
            coinsService.getPackages().requireData().map { it.toDomain() }.also {
                cachedPackages = it
            }
        }
    }

    override suspend fun getTransactions(query: PaginationQuery): AppResult<CoinTransactionPage> =
        apiResult("getTransactions") {
            coinsService.getTransactions(query.page, query.limit).requireData().toDomain()
        }

    override suspend fun watchAd(input: WatchAdInput): AppResult<WatchAdResult> =
        apiResult("watchAd") {
            coinsService.watchAd(input.toRequestDto()).requireData().toDomain().also { adRes ->
                authRepository.updateLocalCoinBalance(adRes.balance)
            }
        }

    override suspend fun getAdsReward(): AppResult<com.pegas.aura.aigirlfriend.soul.domain.model.coins.AdsReward> =
        apiResult("getAdsReward") {
            coinsService.getAdsReward().requireData().toDomain()
        }
}

private fun CoinBalanceEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing coin balance data")

private fun CoinCheckInStateEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing check-in state data")

private fun CoinCheckInClaimEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing check-in claim data")

private fun CoinPackagesEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing coin packages data")

private fun CoinTransactionsEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing coin transactions data")

private fun WatchAdEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing watch ad result data")

private fun com.pegas.aura.aigirlfriend.soul.data.network.model.coins.AdsRewardEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing ads reward data")
