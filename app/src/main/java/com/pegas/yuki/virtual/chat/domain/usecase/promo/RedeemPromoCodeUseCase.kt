package com.pegas.yuki.virtual.chat.domain.usecase.promo

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.promo.RedeemPromoCodeResult
import com.pegas.yuki.virtual.chat.domain.repository.PromoCodeRepository
import javax.inject.Inject

class RedeemPromoCodeUseCase @Inject constructor(
    private val promoCodeRepository: PromoCodeRepository
) {
    suspend operator fun invoke(code: String): AppResult<RedeemPromoCodeResult> {
        return promoCodeRepository.redeemPromoCode(code)
    }
}
