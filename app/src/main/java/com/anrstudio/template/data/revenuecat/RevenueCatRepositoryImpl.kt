package com.pegas.aura.aigirlfriend.soul.data.revenuecat

import android.app.Activity
import android.content.Context
import com.pegas.aura.aigirlfriend.soul.BuildConfig
import com.pegas.aura.aigirlfriend.soul.data.auth.AuthInfoProvider
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicMessageKey
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatCustomer
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatOffering
import com.pegas.aura.aigirlfriend.soul.domain.model.revenuecat.RevenueCatStoreProduct
import com.pegas.aura.aigirlfriend.soul.domain.repository.RevenueCatRepository
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.awaitCustomerInfo
import com.revenuecat.purchases.awaitGetProducts
import com.revenuecat.purchases.awaitLogIn
import com.revenuecat.purchases.awaitOfferings
import com.revenuecat.purchases.awaitRestore
import com.revenuecat.purchases.interfaces.PurchaseCallback
import com.revenuecat.purchases.models.StoreTransaction
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PurchaseCancelledException : Exception("User cancelled purchase")

@Singleton
class RevenueCatRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authInfoProvider: AuthInfoProvider
) : RevenueCatRepository {

    override suspend fun configureIfNeeded(): AppResult<Unit> =
        revenueCatResult("configureIfNeeded") {
            val apiKey = BuildConfig.REVENUECAT_PUBLIC_API_KEY
            if (apiKey.isBlank()) {
                Timber.w("RevenueCat public API key is empty; skipping configure")
                return@revenueCatResult
            }

            if (!Purchases.isConfigured) {
                if (BuildConfig.DEBUG) {
                    Purchases.logLevel = LogLevel.DEBUG
                }
                Purchases.configure(
                    PurchasesConfiguration.Builder(context, apiKey).build()
                )
            }
        }

    override suspend fun syncUserIdentity(): AppResult<Unit> =
        revenueCatResult("syncUserIdentity") {
            ensureConfiguredForUsage()
            val userId = authInfoProvider.userId
            if (userId.isBlank()) return@revenueCatResult

            if (Purchases.sharedInstance.appUserID == userId) return@revenueCatResult
            Purchases.sharedInstance.awaitLogIn(userId)
        }

    private var cachedOfferings: List<RevenueCatOffering>? = null

    override fun getCachedOfferings(): List<RevenueCatOffering>? = cachedOfferings

    override suspend fun getOfferings(forceRefresh: Boolean): AppResult<List<RevenueCatOffering>> {
        if (!forceRefresh) {
            cachedOfferings?.let { return AppResult.Success(it) }
        }
        return revenueCatResult("getOfferings") {
            ensureConfiguredForUsage()
            Purchases.sharedInstance.awaitOfferings().all.values.map { it.toDomain() }.also {
                cachedOfferings = it
            }
        }
    }

    override suspend fun getProducts(productIds: List<String>): AppResult<List<RevenueCatStoreProduct>> =
        revenueCatResult("getProducts") {
            ensureConfiguredForUsage()
            if (productIds.isEmpty()) return@revenueCatResult emptyList()
            Purchases.sharedInstance.awaitGetProducts(productIds).map { it.toDomain() }
        }

    override suspend fun getCustomerInfo(): AppResult<RevenueCatCustomer> =
        revenueCatResult("getCustomerInfo") {
            ensureConfiguredForUsage()
            Purchases.sharedInstance.awaitCustomerInfo()
                .toDomain(Purchases.sharedInstance.appUserID)
        }

    override suspend fun restorePurchases(): AppResult<RevenueCatCustomer> =
        revenueCatResult("restorePurchases") {
            ensureConfiguredForUsage()
            Purchases.sharedInstance.awaitRestore().toDomain(Purchases.sharedInstance.appUserID)
        }

    override suspend fun purchase(
        activity: Activity,
        storeProductId: String
    ): AppResult<RevenueCatCustomer> =
        revenueCatResult("purchase") {
            ensureConfiguredForUsage()
            val offerings = Purchases.sharedInstance.awaitOfferings()
            val rcPackage =
                offerings.current?.availablePackages?.firstOrNull { pkg ->
                    pkg.product.id == storeProductId || pkg.product.id.startsWith("$storeProductId:") || storeProductId.startsWith(
                        "${pkg.product.id}:"
                    )
                } ?: offerings.all.values.flatMap { it.availablePackages }
                    .firstOrNull { pkg ->
                        pkg.product.id == storeProductId || pkg.product.id.startsWith("$storeProductId:") || storeProductId.startsWith(
                            "${pkg.product.id}:"
                        )
                    }

            suspendCancellableCoroutine { continuation ->
                val callback = object : PurchaseCallback {
                    override fun onCompleted(
                        storeTransaction: StoreTransaction,
                        customerInfo: CustomerInfo
                    ) {
                        if (continuation.isActive) {
                            continuation.resume(customerInfo.toDomain(Purchases.sharedInstance.appUserID))
                        }
                    }

                    override fun onError(error: PurchasesError, userCancelled: Boolean) {
                        if (continuation.isActive) {
                            if (userCancelled) {
                                continuation.resumeWithException(PurchaseCancelledException())
                            } else {
                                continuation.resumeWithException(Exception(error.message))
                            }
                        }
                    }
                }

                if (rcPackage != null) {
                    Purchases.sharedInstance.purchase(
                        PurchaseParams.Builder(activity, rcPackage).build(),
                        callback
                    )
                } else {
                    continuation.resumeWithException(Exception("Product not found in RevenueCat offerings: $storeProductId"))
                }
            }
        }

    private suspend fun ensureConfiguredForUsage() {
        val configured = configureIfNeeded()
        if (configured is AppResult.Failure || !Purchases.isConfigured) {
            error("RevenueCat is not configured")
        }
    }

    private suspend fun <T> revenueCatResult(
        operation: String,
        block: suspend () -> T
    ): AppResult<T> = try {
        AppResult.Success(block())
    } catch (throwable: Throwable) {
        if (throwable is PurchaseCancelledException) {
            Timber.i("RevenueCat operation cancelled: %s", operation)
        } else {
            Timber.w(throwable, "RevenueCat operation failed: %s", operation)
        }
        AppResult.Failure(throwable.toPublicError())
    }

    private fun Throwable.toPublicError(): PublicError = when (this) {
        is PurchaseCancelledException -> PublicError(
            PublicMessageKey.GENERIC_ERROR,
            isUserCancellation = true
        )
        is IOException -> PublicError(PublicMessageKey.NETWORK_UNAVAILABLE)
        else -> PublicError(PublicMessageKey.GENERIC_ERROR)
    }
}
