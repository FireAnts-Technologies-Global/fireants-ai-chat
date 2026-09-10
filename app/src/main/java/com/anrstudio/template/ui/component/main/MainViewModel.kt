package com.pegas.aura.aigirlfriend.soul.ui.component.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pegas.aura.aigirlfriend.soul.domain.repository.RevenueCatRepository
import com.pegas.aura.aigirlfriend.soul.domain.usecase.auth.GetAuthUserFlowUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.billing.GetBillingStatusUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.billing.GetVipProductsUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.coins.GetCoinPackagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAuthUserFlowUseCase: GetAuthUserFlowUseCase,
    private val getCoinPackagesUseCase: GetCoinPackagesUseCase,
    private val getVipProductsUseCase: GetVipProductsUseCase,
    private val getBillingStatusUseCase: GetBillingStatusUseCase,
    private val revenueCatRepository: RevenueCatRepository
) : ViewModel() {
    val coinBalance = getAuthUserFlowUseCase().map { it?.coinBalance ?: 0 }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    init {
        prefetchStoreAndSubscription()
    }

    private fun prefetchStoreAndSubscription() {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                runCatching {
                    revenueCatRepository.configureIfNeeded()
                    revenueCatRepository.getOfferings(forceRefresh = true)
                }
            }
            launch {
                runCatching {
                    getCoinPackagesUseCase(forceRefresh = true)
                }
            }
            launch {
                runCatching {
                    getVipProductsUseCase(forceRefresh = true)
                }
            }
            launch {
                runCatching {
                    getBillingStatusUseCase(forceRefresh = true)
                }
            }
        }
    }
}
