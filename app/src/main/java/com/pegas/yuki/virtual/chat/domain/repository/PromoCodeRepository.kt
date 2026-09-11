package com.pegas.yuki.virtual.chat.domain.repository

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.promo.RedeemPromoCodeResult

interface PromoCodeRepository {
    suspend fun redeemPromoCode(code: String): AppResult<RedeemPromoCodeResult>
}
