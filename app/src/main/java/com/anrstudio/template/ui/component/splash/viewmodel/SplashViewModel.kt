package com.anrstudio.template.ui.component.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anrstudio.ads.billing.AppPurchase
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PublicError
import com.anrstudio.template.domain.model.common.PublicMessageKey
import com.anrstudio.template.domain.usecase.auth.GetMeUseCase
import com.anrstudio.template.domain.usecase.auth.GuestLoginUseCase
import com.anrstudio.template.domain.usecase.auth.RefreshTokenUseCase
import com.anrstudio.template.domain.usecase.billing.GetBillingStatusUseCase
import com.anrstudio.template.domain.usecase.revenuecat.SyncRevenueCatUserUseCase
import com.anrstudio.template.ui.bases.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val guestLoginUseCase: GuestLoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val syncRevenueCatUserUseCase: SyncRevenueCatUserUseCase,
    private val getBillingStatusUseCase: GetBillingStatusUseCase
) : BaseViewModel() {

    private val _authBootstrapFinished = MutableLiveData(false)
    val authBootstrapFinished: LiveData<Boolean> = _authBootstrapFinished
    private val _authBootstrapError = MutableLiveData<PublicError?>(null)
    val authBootstrapError: LiveData<PublicError?> = _authBootstrapError

    fun bootstrapAuth(
        hasAccessToken: Boolean,
        hasRefreshToken: Boolean,
        hasResumeGuestToken: Boolean,
        hasNetwork: Boolean
    ) {
        if (_authBootstrapFinished.value == true) return
        if (hasAccessToken || !hasNetwork) {
            bgScope.launch {
                getMeUseCase()
                syncRevenueCatUserUseCase()
                if (hasNetwork) {
                    checkBillingStatus()
                }
                uiScope.launch {
                    _authBootstrapFinished.value = true
                }
            }
            return
        }

        bgScope.launch {
            val authCall = when {
                hasRefreshToken -> refreshTokenUseCase()
                hasResumeGuestToken -> AppResult.Failure(PublicError(PublicMessageKey.AUTH_SESSION_EXPIRED))
                else -> guestLoginUseCase()
            }

            when (authCall) {
                is AppResult.Success -> {
                    Timber.d("Auth bootstrap success")
                    getMeUseCase()
                    syncRevenueCatUserUseCase()
                    checkBillingStatus()
                }

                is AppResult.Failure -> {
                    Timber.w("Auth bootstrap failed: %s", authCall.error.messageKey)
                    _authBootstrapError.postValue(authCall.error)
                }
            }

            uiScope.launch {
                _authBootstrapFinished.value = true
            }
        }
    }

    private suspend fun checkBillingStatus() {
        when (val result = getBillingStatusUseCase()) {
            is AppResult.Success -> {
                val jsonStr = result.data.vip?.json
                val isActive = jsonStr != null && jsonStr.contains("\"active\":true")
                AppPurchase.getInstance().setPurchase(isActive)
                Timber.d("Billing status checked. VIP active = %b", isActive)
            }

            is AppResult.Failure -> {
                Timber.w("Billing status check failed: %s", result.error)
            }
        }
    }

    fun onAuthBootstrapErrorHandled() {
        _authBootstrapError.value = null
    }
}
