package com.anrstudio.template.ui.component.reward

import android.app.Activity
import android.content.Context
import com.anrstudio.ads.billing.AppPurchase
import com.anrstudio.template.ads.AdRemoteConfig
import com.anrstudio.template.ads.AdsManager
import com.anrstudio.template.ads.reward_addcoin
import com.anrstudio.template.ads.reward_checkin
import com.anrstudio.template.domain.model.coins.CoinBalance
import com.anrstudio.template.domain.model.coins.WatchAdInput
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PublicError
import com.anrstudio.template.domain.model.common.PublicMessageKey
import com.anrstudio.template.domain.usecase.auth.GetAuthUserFlowUseCase
import com.anrstudio.template.domain.usecase.coins.ClaimCheckInUseCase
import com.anrstudio.template.domain.usecase.coins.GetCheckInStateUseCase
import com.anrstudio.template.domain.usecase.coins.GetCoinBalanceUseCase
import com.anrstudio.template.domain.usecase.coins.WatchAdUseCase
import com.anrstudio.template.ui.bases.compose.mvi.BaseComposeViewModel
import com.pegas.aura.aigirlfriend.soul.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCheckInStateUseCase: GetCheckInStateUseCase,
    private val claimCheckInUseCase: ClaimCheckInUseCase,
    private val watchAdUseCase: WatchAdUseCase,
    private val getCoinBalanceUseCase: GetCoinBalanceUseCase,
    private val getAuthUserFlowUseCase: GetAuthUserFlowUseCase
) : BaseComposeViewModel<RewardUiState, RewardIntent, RewardEffect>(RewardUiState()) {

    init {
        observeAuthUser()
        loadCheckInState()
        loadCoinBalanceAndAds()
    }

    override fun handleIntent(intent: RewardIntent) {
        when (intent) {
            RewardIntent.Initialize -> {
                loadCheckInState()
                loadCoinBalanceAndAds()
            }

            is RewardIntent.PreloadAd -> preloadAd(intent.activity)
            is RewardIntent.ClaimCheckIn -> claimCheckIn(intent.activity)
            is RewardIntent.WatchAd -> watchAd(intent.activity)
            RewardIntent.DismissClaimDialog -> updateState { copy(showClaimDialog = false) }
            RewardIntent.DismissCheckInDialog -> updateState { copy(showCheckInDialog = false) }
            RewardIntent.OpenStore -> sendEffect(RewardEffect.NavigateToStore)
        }
    }

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private fun observeAuthUser() {
        launchIO {
            getAuthUserFlowUseCase().collect { user ->
                updateState {
                    copy(
                        authUserId = user?.id,
                        coinBalance = user?.coinBalance ?: coinBalance
                    )
                }
            }
        }
    }

    private fun preloadAd(activity: Activity) {
        preloadRewardCheckInAd(activity)
        preloadRewardAddCoinAd(activity)
    }

    private fun preloadRewardAddCoinAd(activity: Activity) {
        if (!currentState.adsConfigLoaded) return
        if (currentState.adsRemainingToday <= 0) return
        if (!AdRemoteConfig.reward_addcoin.isEnable) return
        launchUI {
            AdsManager.loadRewardAddcoin(activity, object : AdsManager.RewardLoadListener {
                override fun onLoadRewardSuccess() {
                    updateState { copy(isAdPreloaded = true) }
                }

                override fun onLoadRewardFail() {
                    updateState { copy(isAdPreloaded = false) }
                }
            })
        }
    }

    private fun preloadRewardCheckInAd(activity: Activity) {
        if (currentState.claimedToday) return
        if (!AdRemoteConfig.reward_checkin.isEnable) return
        if (AppPurchase.getInstance().isPurchased(activity)) return
        launchUI {
            AdsManager.loadRewardCheckin(activity, object : AdsManager.RewardLoadListener {
                override fun onLoadRewardSuccess() = Unit

                override fun onLoadRewardFail() = Unit
            })
        }
    }

    private fun loadCheckInState() {
        launchIO {
            updateState { copy(isLoading = true, error = null) }
            val isVip = AppPurchase.getInstance().isPurchased(context)
            when (val result = getCheckInStateUseCase()) {
                is AppResult.Success -> {
                    val checkIn = result.data
                    updateState {
                        copy(
                            isLoading = false,
                            dayIndex = checkIn.dayIndex,
                            claimedToday = checkIn.claimedToday,
                            isVip = isVip,
                            rewards = checkIn.rewards
                        )
                    }
                }

                is AppResult.Failure -> {
                    updateState { copy(isLoading = false, error = result.error, isVip = isVip) }
                }
            }
        }
    }

    private fun loadCoinBalanceAndAds() {
        launchIO {
            val isVip = AppPurchase.getInstance().isPurchased(context)
            when (val result = getCoinBalanceUseCase()) {
                is AppResult.Success -> {
                    applyCoinBalance(result.data, isVip)
                }

                is AppResult.Failure -> {
                    updateState { copy(adsConfigLoaded = false, isVip = isVip) }
                }
            }
        }
    }

    private fun claimCheckIn(activity: Activity) {
        if (currentState.claimedToday) return
        if (currentState.isCheckingIn) return

        updateState { copy(isCheckingIn = true) }

        if (AppPurchase.getInstance().isPurchased(activity)) {
            performCheckInApiCall()
            return
        }

        if (!AdRemoteConfig.reward_checkin.isEnable) {
            performCheckInApiCall()
            return
        }

        launchUI {
            AdsManager.showRewardCheckin(
                activity = activity,
                onNextAction = {
                    performCheckInApiCall()
                },
                onFailed = {
                    AdsManager.loadRewardCheckin(activity, object : AdsManager.RewardLoadListener {
                        override fun onLoadRewardSuccess() {
                            AdsManager.showRewardCheckin(
                                activity = activity,
                                onNextAction = {
                                    performCheckInApiCall()
                                },
                                onFailed = {
                                    performCheckInApiCall()
                                }
                            )
                        }

                        override fun onLoadRewardFail() {
                            performCheckInApiCall()
                        }
                    })
                }
            )
        }
    }

    private fun performCheckInApiCall() {
        launchIO {
            when (val result = claimCheckInUseCase()) {
                is AppResult.Success -> {
                    val claim = result.data
                    updateState {
                        copy(
                            isCheckingIn = false,
                            claimedToday = true,
                            showCheckInDialog = true,
                            checkInRewardAmount = claim.credited
                        )
                    }
                }

                is AppResult.Failure -> {
                    updateState { copy(isCheckingIn = false) }
                    sendEffect(RewardEffect.ShowToast(result.error))
                }
            }
        }
    }

    private fun watchAd(activity: Activity) {
        if (currentState.isWatchingAd) return

        if (!AdRemoteConfig.reward_addcoin.isEnable) {
            sendEffect(RewardEffect.ShowMessage(R.string.reward_ads_unavailable))
            return
        }

        updateState { copy(isWatchingAd = true) }

        launchIO {
            when (val result = getCoinBalanceUseCase()) {
                is AppResult.Success -> {
                    applyCoinBalance(
                        balance = result.data,
                        isVip = AppPurchase.getInstance()
                            .isPurchased(context)
                    )
                    if (result.data.ads.remainingToday <= 0) {
                        updateState { copy(isWatchingAd = false) }
                        return@launchIO
                    }
                    showRewardAd(activity)
                }

                is AppResult.Failure -> {
                    updateState { copy(isWatchingAd = false, adsConfigLoaded = false) }
                    sendEffect(RewardEffect.ShowToast(result.error))
                }
            }
        }
    }

    private fun showRewardAd(activity: Activity) {
        launchUI {
            AdsManager.showRewardAddcoin(
                activity = activity,
                ssvUserId = currentState.authUserId.takeIf { currentState.adsRequireSsv },
                onNextAction = {
                    onUserEarnedRewardAd(activity)
                },
                onFailed = {
                    AdsManager.loadRewardAddcoin(activity, object : AdsManager.RewardLoadListener {
                        override fun onLoadRewardSuccess() {
                            updateState { copy(isAdPreloaded = true) }
                            AdsManager.showRewardAddcoin(
                                activity = activity,
                                ssvUserId = currentState.authUserId.takeIf { currentState.adsRequireSsv },
                                onNextAction = {
                                    onUserEarnedRewardAd(activity)
                                },
                                onFailed = {
                                    updateState { copy(isWatchingAd = false) }
                                    sendNetworkUnavailable()
                                }
                            )
                        }

                        override fun onLoadRewardFail() {
                            updateState { copy(isWatchingAd = false, isAdPreloaded = false) }
                            sendNetworkUnavailable()
                        }
                    })
                }
            )
        }
    }

    private fun onUserEarnedRewardAd(activity: Activity) {
        launchIO {
            if (currentState.adsRequireSsv) {
                waitForSsvRewardCredit(activity)
                return@launchIO
            }
            val input = WatchAdInput(
                impressionId = UUID.randomUUID().toString(),
                adNetwork = "AdMob"
            )
            when (val result = watchAdUseCase(input)) {
                is AppResult.Success -> {
                    val adRes = result.data
                    updateState {
                        copy(
                            isWatchingAd = false,
                            adsWatchedToday = adRes.adsWatchedToday,
                            adsRemainingToday = adRes.adsRemainingToday,
                            showClaimDialog = true,
                            claimedRewardAmount = adRes.credited,
                            isAdPreloaded = false
                        )
                    }
                    preloadAd(activity)
                }

                is AppResult.Failure -> {
                    updateState { copy(isWatchingAd = false) }
                    sendEffect(RewardEffect.ShowToast(result.error))
                }
            }
        }
    }

    private suspend fun waitForSsvRewardCredit(
        activity: Activity,
        maxAttempts: Int = 6,
        delayMs: Long = 2000L
    ) {
        val initialBalance = currentState.coinBalance
        for (attempt in 1..maxAttempts) {
            if (attempt > 1) delay(delayMs)
            when (val result = getCoinBalanceUseCase()) {
                is AppResult.Success -> {
                    val balance = result.data
                    val credited = balance.balance - initialBalance
                    updateState {
                        copy(
                            coinBalance = balance.balance,
                            adsWatchedToday = balance.ads.watchedToday,
                            adsRemainingToday = balance.ads.remainingToday,
                            coinsPerView = balance.ads.coinsPerView,
                            adsConfigLoaded = true,
                            adsRequireSsv = balance.ads.requireSsv
                        )
                    }
                    if (credited > 0) {
                        updateState {
                            copy(
                                isWatchingAd = false,
                                showClaimDialog = true,
                                claimedRewardAmount = credited,
                                isAdPreloaded = false
                            )
                        }
                        preloadAd(activity)
                        return
                    }
                }

                is AppResult.Failure -> Unit
            }
        }

        updateState { copy(isWatchingAd = false, isAdPreloaded = false) }
        sendEffect(RewardEffect.ShowMessage(R.string.reward_processing))
    }

    private fun applyCoinBalance(
        balance: CoinBalance,
        isVip: Boolean
    ) {
        updateState {
            copy(
                coinBalance = balance.balance,
                adsWatchedToday = balance.ads.watchedToday,
                adsRemainingToday = balance.ads.remainingToday,
                coinsPerView = balance.ads.coinsPerView,
                adsConfigLoaded = true,
                adsRequireSsv = balance.ads.requireSsv,
                isVip = isVip
            )
        }
    }

    private fun sendNetworkUnavailable() {
        sendEffect(
            RewardEffect.ShowToast(
                PublicError(
                    PublicMessageKey.NETWORK_UNAVAILABLE
                )
            )
        )
    }
}
