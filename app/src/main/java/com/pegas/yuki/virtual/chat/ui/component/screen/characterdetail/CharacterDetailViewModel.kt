package com.pegas.yuki.virtual.chat.ui.component.screen.characterdetail

import androidx.lifecycle.SavedStateHandle
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.data.pref.AppSharedPref
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.domain.model.common.PublicMessageKey
import com.pegas.yuki.virtual.chat.domain.model.conversation.CreateConversationInput
import com.pegas.yuki.virtual.chat.domain.usecase.auth.GetAuthUserFlowUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.character.GetFullCharacterDetailUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.GetOrCreateConversationUseCase
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseComposeViewModel
import com.pegas.yuki.virtual.chat.ui.bases.navigation.AppRoutes
import com.pegas.yuki.virtual.chat.ui.billing.BackgroundPurchaseCoordinator
import com.pegas.yuki.virtual.chat.ui.billing.BackgroundPurchaseResult
import com.pegas.yuki.virtual.chat.ui.component.rate.RatePromptPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFullCharacterDetailUseCase: GetFullCharacterDetailUseCase,
    private val getOrCreateConversationUseCase: GetOrCreateConversationUseCase,
    private val getAuthUserFlowUseCase: GetAuthUserFlowUseCase,
    private val appSharedPref: AppSharedPref,
    private val backgroundPurchaseCoordinator: BackgroundPurchaseCoordinator
) : BaseComposeViewModel<CharacterDetailUiState, CharacterDetailIntent, CharacterDetailEffect>(
    CharacterDetailUiState()
) {
    private companion object {
        const val SCREEN_NAME = "CharacterDetailScreen"
        const val MIN_START_CHAT_LOADING_MS = 500L
    }

    private val characterSlug: String = savedStateHandle.get<String>(AppRoutes.CHARACTER_SLUG_ARG).orEmpty()

    init {
        launchIO {
            getAuthUserFlowUseCase().collect { user ->
                updateState { copy(coinBalance = user?.coinBalance ?: 0) }
            }
        }
    }

    override fun handleIntent(intent: CharacterDetailIntent) {
        when (intent) {
            CharacterDetailIntent.Initialize -> {
                if (currentState.characterDetail != null || currentState.isLoading) return
                loadCharacter()
            }

            CharacterDetailIntent.Retry -> loadCharacter(forceReload = true)

            CharacterDetailIntent.StartChat -> startChat()

            is CharacterDetailIntent.PurchaseBackground -> purchaseBackground(intent.backgroundId)

            CharacterDetailIntent.RateSubmitted -> {
                appSharedPref.isRate = true
            }
        }
    }

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private fun loadCharacter(forceReload: Boolean = false) {
        if (!forceReload && currentState.characterDetail != null) return

        if (characterSlug.isBlank()) {
            updateState {
                copy(
                    isLoading = false,
                    error = PublicError(PublicMessageKey.GENERIC_ERROR)
                )
            }
            return
        }

        launchIO {
            if (!forceReload) {
                updateState {
                    copy(
                        isLoading = currentState.characterDetail == null,
                        error = null
                    )
                }
            }

            when (val result = getFullCharacterDetailUseCase(characterSlug)) {
                is AppResult.Failure -> {
                    updateState {
                        copy(
                            isLoading = false,
                            error = result.error
                        )
                    }
                }

                is AppResult.Success -> {
                    updateState {
                        copy(
                            isLoading = false,
                            error = null,
                            characterDetail = result.data
                        )
                    }
                }
            }
        }
    }

    private fun startChat() {
        val character = currentState.character ?: return
        if (currentState.isStartingChat) return

        val loadingStartedAt = System.currentTimeMillis()
        updateState { copy(isStartingChat = true, error = null) }

        launchIO {
            when (
                val result = getOrCreateConversationUseCase(
                    CreateConversationInput(
                        characterId = character.id,
                        title = character.name
                    )
                )
            ) {
                is AppResult.Failure -> {
                    ensureMinimumStartChatLoading(loadingStartedAt)
                    updateState {
                        copy(
                            isStartingChat = false,
                            error = result.error
                        )
                    }
                }

                is AppResult.Success -> {
                    ensureMinimumStartChatLoading(loadingStartedAt)
                    updateState { copy(isStartingChat = false, error = null) }
                    sendEffect(CharacterDetailEffect.NavigateToChat(result.data.id))
                }
            }
        }
    }

    private fun purchaseBackground(backgroundId: String) {
        val characterId = currentState.character?.id ?: return
        if (currentState.purchasingBackgroundId != null) return
        val background = currentState.backgroundsData?.backgrounds?.find { it.id == backgroundId }
        if (background == null || !background.isLocked) return

        updateState { copy(purchasingBackgroundId = backgroundId) }

        launchIO {
            when (
                val result = backgroundPurchaseCoordinator.purchase(
                    screen = SCREEN_NAME,
                    characterId = characterId,
                    background = background,
                    coinBalance = currentState.coinBalance
                )
            ) {
                is BackgroundPurchaseResult.Failure -> {
                    updateState { copy(purchasingBackgroundId = null, error = result.error) }
                }

                is BackgroundPurchaseResult.Success -> {
                    val updatedBg = result.result.background
                    val updatedBackgrounds = currentState.backgroundsData?.backgrounds?.map { bg ->
                        if (bg.id == updatedBg?.id) {
                            updatedBg
                        } else {
                            bg
                        }
                    }
                    updateState {
                        val currentDetail = characterDetail
                        val currentBgsData = currentDetail?.backgroundsData
                        copy(
                            purchasingBackgroundId = null,
                            characterDetail = currentDetail?.copy(
                                backgroundsData = currentBgsData?.copy(
                                    backgrounds = updatedBackgrounds ?: currentBgsData.backgrounds
                                )
                            )
                        )
                    }
                    sendEffect(CharacterDetailEffect.ShowToast(R.string.character_detail_purchase_success))
                    maybeShowRateDialogAfterPremiumAction()
                }
            }
        }
    }

    private fun maybeShowRateDialogAfterPremiumAction() {
        if (!RatePromptPolicy.canShowAfterPremiumAction(appSharedPref)) return

        RatePromptPolicy.markShownInSession(appSharedPref)
        launchIO {
            sendEffect(CharacterDetailEffect.ShowRateDialog)
        }
    }

    private suspend fun ensureMinimumStartChatLoading(startedAtMillis: Long) {
        val elapsed = System.currentTimeMillis() - startedAtMillis
        val remaining = MIN_START_CHAT_LOADING_MS - elapsed
        if (remaining > 0) {
            delay(remaining)
        }
    }
}
