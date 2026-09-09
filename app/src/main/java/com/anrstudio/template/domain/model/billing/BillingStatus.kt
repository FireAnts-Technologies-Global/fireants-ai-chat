package com.pegas.aura.aigirlfriend.soul.domain.model.billing

import com.pegas.aura.aigirlfriend.soul.domain.model.common.RawJsonPayload

data class BillingStatus(
    val vip: RawJsonPayload?,
    val iapEnabled: Boolean
)
