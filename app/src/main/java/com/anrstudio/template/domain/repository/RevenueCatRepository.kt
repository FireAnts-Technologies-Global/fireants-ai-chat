package com.anrstudio.template.domain.repository

import android.app.Activity
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.revenuecat.RevenueCatCustomer
import com.anrstudio.template.domain.model.revenuecat.RevenueCatOffering
import com.anrstudio.template.domain.model.revenuecat.RevenueCatStoreProduct

interface RevenueCatRepository {
    suspend fun configureIfNeeded(): AppResult<Unit>

    suspend fun syncUserIdentity(): AppResult<Unit>

    suspend fun getOfferings(): AppResult<List<RevenueCatOffering>>

    suspend fun getProducts(productIds: List<String>): AppResult<List<RevenueCatStoreProduct>>

    suspend fun getCustomerInfo(): AppResult<RevenueCatCustomer>

    suspend fun restorePurchases(): AppResult<RevenueCatCustomer>

    suspend fun purchase(activity: Activity, storeProductId: String): AppResult<RevenueCatCustomer>
}
