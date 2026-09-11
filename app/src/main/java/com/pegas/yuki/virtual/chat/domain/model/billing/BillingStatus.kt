package com.pegas.yuki.virtual.chat.domain.model.billing

import com.pegas.yuki.virtual.chat.domain.model.common.RawJsonPayload

data class BillingStatus(
    val vip: RawJsonPayload?,
    val iapEnabled: Boolean
)
