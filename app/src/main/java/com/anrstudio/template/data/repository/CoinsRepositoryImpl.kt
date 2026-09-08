package com.anrstudio.template.data.repository

import com.anrstudio.template.data.network.model.coins.AdsRewardEnvelopeDto
import com.anrstudio.template.data.network.model.coins.CoinBalanceEnvelopeDto
import com.anrstudio.template.data.network.model.coins.CoinCheckInClaimEnvelopeDto
import com.anrstudio.template.data.network.model.coins.CoinCheckInStateEnvelopeDto
import com.anrstudio.template.data.network.model.coins.CoinPackagesEnvelopeDto
import com.anrstudio.template.data.network.model.coins.CoinTransactionsEnvelopeDto
import com.anrstudio.template.data.network.model.coins.WatchAdEnvelopeDto
import com.anrstudio.template.data.network.model.coins.toDomain
import com.anrstudio.template.data.network.model.coins.toRequestDto
import com.anrstudio.template.data.network.service.CoinsService
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
import com.anrstudio.template.domain.repository.AuthRepository
import com.anrstudio.template.domain.repository.CoinsRepository
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

    override suspend fun getPackages(): AppResult<List<CoinPackage>> =
        apiResult("getPackages") {
            coinsService.getPackages().requireData().map { it.toDomain() }
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

    override suspend fun getAdsReward(): AppResult<AdsReward> =
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

private fun AdsRewardEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing ads reward data")
