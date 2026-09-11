package com.pegas.yuki.virtual.chat.domain.repository

import android.app.Activity
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.revenuecat.RevenueCatCustomer
import com.pegas.yuki.virtual.chat.domain.model.revenuecat.RevenueCatOffering
import com.pegas.yuki.virtual.chat.domain.model.revenuecat.RevenueCatStoreProduct

interface RevenueCatRepository {
    suspend fun configureIfNeeded(): AppResult<Unit>

    suspend fun syncUserIdentity(): AppResult<Unit>

    suspend fun getOfferings(forceRefresh: Boolean = false): AppResult<List<RevenueCatOffering>>

    suspend fun getProducts(productIds: List<String>): AppResult<List<RevenueCatStoreProduct>>

    suspend fun getCustomerInfo(): AppResult<RevenueCatCustomer>

    suspend fun restorePurchases(): AppResult<RevenueCatCustomer>

    suspend fun purchase(activity: Activity, storeProductId: String): AppResult<RevenueCatCustomer>

    fun getCachedOfferings(): List<RevenueCatOffering>? = null
}
