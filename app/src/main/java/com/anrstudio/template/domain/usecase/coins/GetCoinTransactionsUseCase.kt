package com.pegas.aura.aigirlfriend.soul.domain.usecase.coins

import com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinTransactionPage
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery
import com.pegas.aura.aigirlfriend.soul.domain.repository.CoinsRepository
import javax.inject.Inject

class GetCoinTransactionsUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {
    suspend operator fun invoke(query: PaginationQuery): AppResult<CoinTransactionPage> =
        coinsRepository.getTransactions(query)
}
