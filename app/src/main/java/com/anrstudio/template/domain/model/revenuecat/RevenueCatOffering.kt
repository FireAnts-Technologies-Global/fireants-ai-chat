package com.anrstudio.template.domain.model.revenuecat

data class RevenueCatOffering(
    val identifier: String,
    val serverDescription: String,
    val packages: List<RevenueCatPackage>
)
