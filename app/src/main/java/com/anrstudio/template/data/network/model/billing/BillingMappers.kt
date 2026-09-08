package com.anrstudio.template.data.network.model.billing

import com.anrstudio.template.domain.model.billing.BillingStatus
import com.anrstudio.template.domain.model.billing.VipProduct
import com.anrstudio.template.domain.model.common.RawJsonPayload
import com.squareup.moshi.Moshi

private val moshi by lazy { Moshi.Builder().build() }
private val mapAdapter by lazy { moshi.adapter(Map::class.java) }

fun BillingStatusDto.toDomain(): BillingStatus = BillingStatus(
    vip = vip?.let { RawJsonPayload(json = mapAdapter.toJson(it)) },
    iapEnabled = iapEnabled == true
)

fun VipProductDto.toDomain(): VipProduct = VipProduct(
    id = id.orEmpty(),
    code = code.orEmpty(),
    storeProductId = storeProductId.orEmpty(),
    platform = platform.orEmpty(),
    period = period.orEmpty(),
    displayName = displayName.orEmpty(),
    badge = badge,
    sortOrder = sortOrder ?: 0
)
