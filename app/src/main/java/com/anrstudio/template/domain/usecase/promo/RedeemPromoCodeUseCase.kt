package com.pegas.aura.aigirlfriend.soul.domain.usecase.promo

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.promo.RedeemPromoCodeResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.PromoCodeRepository
import javax.inject.Inject

class RedeemPromoCodeUseCase @Inject constructor(
    private val promoCodeRepository: PromoCodeRepository
) {
    suspend operator fun invoke(code: String): AppResult<RedeemPromoCodeResult> {
        return promoCodeRepository.redeemPromoCode(code)
    }
}
