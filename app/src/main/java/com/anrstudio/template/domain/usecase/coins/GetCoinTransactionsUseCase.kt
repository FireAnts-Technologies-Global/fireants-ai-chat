package com.anrstudio.template.domain.usecase.coins

import com.anrstudio.template.domain.model.coins.CoinTransactionPage
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PaginationQuery
import com.anrstudio.template.domain.repository.CoinsRepository
import javax.inject.Inject

class GetCoinTransactionsUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {
    suspend operator fun invoke(query: PaginationQuery): AppResult<CoinTransactionPage> =
        coinsRepository.getTransactions(query)
}
