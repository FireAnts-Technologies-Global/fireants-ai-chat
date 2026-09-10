package com.pegas.aura.aigirlfriend.soul.ui.component.screen.reward

import android.app.Activity
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.coins.WatchAdInput
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.usecase.auth.GetAuthUserFlowUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.coins.ClaimCheckInUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.coins.GetCheckInStateUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.coins.GetCoinBalanceUseCase
import com.pegas.aura.aigirlfriend.soul.domain.usecase.coins.WatchAdUseCase
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseComposeViewModel
import com.pegas.aura.aigirlfriend.soul.ui.reward.RewardAdsCoordinator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    private val getCheckInStateUseCase: GetCheckInStateUseCase,
    private val claimCheckInUseCase: ClaimCheckInUseCase,
    private val watchAdUseCase: WatchAdUseCase,
    private val getCoinBalanceUseCase: GetCoinBalanceUseCase,
    private val getAuthUserFlowUseCase: GetAuthUserFlowUseCase,
    private val rewardAdsCoordinator: RewardAdsCoordinator
) : BaseComposeViewModel<RewardUiState, RewardIntent, RewardEffect>(RewardUiState()) {

    private companion object {
        const val MIN_LOADING_TIME_ON_ERROR_MS = 1000L
    }

    private var loadCheckInJob: Job? = null

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
        launchUI {
            rewardAdsCoordinator.preloadRewardAds(
                activity = activity,
                claimedToday = currentState.claimedToday,
                adsConfigLoaded = currentState.adsConfigLoaded,
                adsRemainingToday = currentState.adsRemainingToday,
                onAddCoinLoaded = { isLoaded ->
                    updateState { copy(isAdPreloaded = isLoaded) }
                }
            )
        }
    }

    private fun loadCheckInState() {
        loadCheckInJob?.cancel()
        loadCheckInJob = launchIO {
            val startTime = System.currentTimeMillis()
            updateState { copy(isLoading = true, error = null) }
            val isVip = rewardAdsCoordinator.isVip()
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
                    ensureMinimumLoading(startTime)
                    updateState { copy(isLoading = false, error = result.error, isVip = isVip) }
                }
            }
        }
    }

    private fun loadCoinBalanceAndAds() {
        launchIO {
            val isVip = rewardAdsCoordinator.isVip()
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

        if (rewardAdsCoordinator.shouldSkipCheckInAd(activity)) {
            performCheckInApiCall()
            return
        }

        launchUI {
            rewardAdsCoordinator.showRewardCheckInAd(
                activity = activity,
                onFinished = { performCheckInApiCall() }
            )
        }
    }

    private fun performCheckInApiCall() {
        launchIO {
            val startTime = System.currentTimeMillis()
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
                    ensureMinimumLoading(startTime)
                    updateState { copy(isCheckingIn = false) }
                    sendEffect(RewardEffect.ShowToast(result.error))
                }
            }
        }
    }

    private fun watchAd(activity: Activity) {
        if (currentState.isWatchingAd) return

        updateState { copy(isWatchingAd = true) }

        if (!rewardAdsCoordinator.isRewardAddCoinEnabled()) {
            launchIO {
                delay(MIN_LOADING_TIME_ON_ERROR_MS)
                updateState { copy(isWatchingAd = false) }
                sendEffect(RewardEffect.ShowMessage(R.string.reward_ads_unavailable))
            }
            return
        }

        launchIO {
            val startTime = System.currentTimeMillis()
            when (val result = getCoinBalanceUseCase()) {
                is AppResult.Success -> {
                    applyCoinBalance(
                        balance = result.data,
                        isVip = rewardAdsCoordinator.isVip()
                    )
                    if (result.data.ads.remainingToday <= 0) {
                        updateState { copy(isWatchingAd = false) }
                        return@launchIO
                    }
                    showRewardAd(activity, startTime)
                }

                is AppResult.Failure -> {
                    ensureMinimumLoading(startTime)
                    updateState { copy(isWatchingAd = false, adsConfigLoaded = false) }
                    sendEffect(RewardEffect.ShowToast(result.error))
                }
            }
        }
    }

    private fun showRewardAd(activity: Activity, startTime: Long = System.currentTimeMillis()) {
        launchUI {
            rewardAdsCoordinator.showRewardAddCoinAd(
                activity = activity,
                ssvUserId = currentState.authUserId.takeIf { currentState.adsRequireSsv },
                onRewarded = {
                    onUserEarnedRewardAd(activity)
                },
                onUnavailable = {
                    launchIO {
                        ensureMinimumLoading(startTime)
                        updateState { copy(isWatchingAd = false) }
                        sendNetworkUnavailable()
                    }
                },
                onAdPreloaded = { isPreloaded ->
                    updateState { copy(isAdPreloaded = isPreloaded) }
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
        balance: com.pegas.aura.aigirlfriend.soul.domain.model.coins.CoinBalance,
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
                com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError(
                    com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicMessageKey.NETWORK_UNAVAILABLE
                )
            )
        )
    }

    private suspend fun ensureMinimumLoading(
        startedAtMillis: Long,
        minLoadingMs: Long = MIN_LOADING_TIME_ON_ERROR_MS
    ) {
        val elapsed = System.currentTimeMillis() - startedAtMillis
        val remaining = minLoadingMs - elapsed
        if (remaining > 0) {
            delay(remaining)
        }
    }
}
