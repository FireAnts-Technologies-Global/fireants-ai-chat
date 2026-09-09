package com.pegas.aura.aigirlfriend.soul.data.repository

import com.fireants.adsdk.billing.AppPurchase
import com.pegas.aura.aigirlfriend.soul.data.auth.AuthInfoProvider
import com.pegas.aura.aigirlfriend.soul.data.network.model.base.requireData
import com.pegas.aura.aigirlfriend.soul.data.network.model.promo.RedeemPromoCodeRequestDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.promo.toDomain
import com.pegas.aura.aigirlfriend.soul.data.network.service.PromoCodeService
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.promo.RedeemPromoCodeResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.BillingRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.PromoCodeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromoCodeRepositoryImpl @Inject constructor(
    private val promoCodeService: PromoCodeService,
    private val authInfoProvider: AuthInfoProvider,
    private val authRepository: AuthRepository,
    private val billingRepository: BillingRepository
) : PromoCodeRepository {

    override suspend fun redeemPromoCode(code: String): AppResult<RedeemPromoCodeResult> =
        apiResult("redeemPromoCode") {
            val deviceId = authInfoProvider.deviceId.ifBlank { authInfoProvider.clientId }
            val request = RedeemPromoCodeRequestDto(
                code = code.trim(),
                deviceId = deviceId,
                appVersion = authInfoProvider.appVersion
            )
            val result = promoCodeService.redeemPromoCode(request).requireData().toDomain()
            result.newBalance?.let { balance ->
                authRepository.updateLocalCoinBalance(balance)
            }

            val vipJson = result.vipStatus?.json
            val isVip = if (vipJson.isNullOrBlank() || vipJson == "{}" || vipJson == "null") {
                false
            } else {
                !vipJson.contains("\"active\":false") && !vipJson.contains("\"active\": false")
            }

            if (isVip) {
                AppPurchase.getInstance().setPurchase(true)
            } else {
                runCatching {
                    when (val billingResult = billingRepository.getBillingStatus()) {
                        is AppResult.Success -> {
                            val statusJson = billingResult.data.vip?.json
                            if (statusJson != null && (statusJson.contains("\"active\":true") || statusJson.contains(
                                    "\"active\": true"
                                ))
                            ) {
                                AppPurchase.getInstance().setPurchase(true)
                            }
                        }

                        else -> Unit
                    }
                }
            }

            result
        }
}
