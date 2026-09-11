package com.pegas.yuki.virtual.chat.domain.usecase.coins

import com.pegas.yuki.virtual.chat.domain.model.coins.CoinTransactionPage
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery
import com.pegas.yuki.virtual.chat.domain.repository.CoinsRepository
import javax.inject.Inject

class GetCoinTransactionsUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {
    suspend operator fun invoke(query: PaginationQuery): AppResult<CoinTransactionPage> =
        coinsRepository.getTransactions(query)
}
