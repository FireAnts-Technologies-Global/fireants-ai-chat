package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.promo.RedeemPromoCodeResult

interface PromoCodeRepository {
    suspend fun redeemPromoCode(code: String): AppResult<RedeemPromoCodeResult>
}
