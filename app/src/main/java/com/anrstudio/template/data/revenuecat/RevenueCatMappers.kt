package com.pegas.aura.aigirlfriend.soul.data.revenuecat

import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatCustomer
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatOffering
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatPackage
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatStoreProduct
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Offering
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.models.StoreProduct

fun CustomerInfo.toDomain(appUserId: String): RevenueCatCustomer = RevenueCatCustomer(
    appUserId = appUserId,
    originalAppUserId = originalAppUserId,
    activeEntitlements = entitlements.active.keys.toList(),
    originalPurchaseDate = originalPurchaseDate?.toString(),
    firstSeen = firstSeen.toString(),
    latestExpirationDate = latestExpirationDate?.toString()
)

fun Offering.toDomain(): RevenueCatOffering = RevenueCatOffering(
    identifier = identifier,
    serverDescription = serverDescription,
    packages = availablePackages.map { it.toDomain() }
)

fun Package.toDomain(): RevenueCatPackage = RevenueCatPackage(
    identifier = identifier,
    packageType = packageType.name,
    product = product.toDomain()
)

fun StoreProduct.toDomain(): RevenueCatStoreProduct = RevenueCatStoreProduct(
    id = id,
    title = title,
    description = description,
    priceFormatted = price.formatted,
    priceAmountMicros = price.amountMicros,
    currencyCode = price.currencyCode,
    type = type.name,
    period = period?.toString()
)
