package com.anrstudio.template.domain.model.billing

import com.anrstudio.template.domain.model.common.RawJsonPayload

data class BillingStatus(
    val vip: RawJsonPayload?,
    val iapEnabled: Boolean
)
