package com.pegas.yuki.virtual.chat.data.network.service

import com.pegas.yuki.virtual.chat.data.network.model.promo.RedeemPromoCodeEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.model.promo.RedeemPromoCodeRequestDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PromoCodeService {

    @Headers("Content-Type: application/json")
    @POST("promo-codes/redeem")
    suspend fun redeemPromoCode(
        @Body body: RedeemPromoCodeRequestDto
    ): RedeemPromoCodeEnvelopeDto
}
