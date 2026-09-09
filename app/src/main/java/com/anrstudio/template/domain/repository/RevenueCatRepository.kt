package com.pegas.aura.aigirlfriend.soul.domain.repository

import android.app.Activity
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatCustomer
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatOffering
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatStoreProduct

interface RevenueCatRepository {
    suspend fun configureIfNeeded(): AppResult<Unit>

    suspend fun syncUserIdentity(): AppResult<Unit>

    suspend fun getOfferings(): AppResult<List<RevenueCatOffering>>

    suspend fun getProducts(productIds: List<String>): AppResult<List<RevenueCatStoreProduct>>

    suspend fun getCustomerInfo(): AppResult<RevenueCatCustomer>

    suspend fun restorePurchases(): AppResult<RevenueCatCustomer>

    suspend fun purchase(activity: Activity, storeProductId: String): AppResult<RevenueCatCustomer>
}
