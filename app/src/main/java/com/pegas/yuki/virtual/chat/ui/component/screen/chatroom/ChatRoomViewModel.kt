package com.pegas.yuki.virtual.chat.ui.component.screen.chatroom

import android.content.Context
import android.os.SystemClock
import androidx.lifecycle.SavedStateHandle
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.data.pref.AppSharedPref
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationMessage
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationMessageRole
import com.pegas.yuki.virtual.chat.domain.model.conversation.SendChatMessageInput
import com.pegas.yuki.virtual.chat.domain.model.conversation.UpdateConversationInput
import com.pegas.yuki.virtual.chat.domain.model.report.CreateReportInput
import com.pegas.yuki.virtual.chat.domain.model.report.ReportClientMeta
import com.pegas.yuki.virtual.chat.domain.usecase.auth.GetAuthUserFlowUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.character.GetCharacterBackgroundsUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.character.GetCharacterProgressUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.coins.GetCoinBalanceUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.DeleteConversationUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.GetCachedConversationByIdUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.GetConversationMessagesUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.GetConversationUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.GetQuickPromptsUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.SendChatMessageUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.UpdateConversationUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.report.SubmitReportUseCase
import com.pegas.yuki.virtual.chat.ui.analytics.ChatActionTracker
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseComposeViewModel
import com.pegas.yuki.virtual.chat.ui.bases.navigation.AppRoutes
import com.pegas.yuki.virtual.chat.ui.billing.BackgroundPurchaseCoordinator
import com.pegas.yuki.virtual.chat.ui.billing.BackgroundPurchaseResult
import com.pegas.yuki.virtual.chat.ui.component.rate.RatePromptPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
    private val getConversationUseCase: GetConversationUseCase,
    private val getConversationMessagesUseCase: GetConversationMessagesUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase,
    private val deleteConversationUseCase: DeleteConversationUseCase,
    private val getAuthUserFlowUseCase: GetAuthUserFlowUseCase,
    private val getCharacterProgressUseCase: GetCharacterProgressUseCase,
    private val getCachedConversationByIdUseCase: GetCachedConversationByIdUseCase,
    private val getQuickPromptsUseCase: GetQuickPromptsUseCase,
    private val getCharacterBackgroundsUseCase: GetCharacterBackgroundsUseCase,
    private val getCoinBalanceUseCase: GetCoinBalanceUseCase,
    private val updateConversationUseCase: UpdateConversationUseCase,
    private val submitReportUseCase: SubmitReportUseCase,
    private val appSharedPref: AppSharedPref,
    private val backgroundPurchaseCoordinator: BackgroundPurchaseCoordinator,
    private val chatActionTracker: ChatActionTracker
) : BaseComposeViewModel<ChatRoomUiState, ChatRoomIntent, ChatRoomEffect>(ChatRoomUiState()) {

    private val conversationId: String =
        savedStateHandle.get<String>(AppRoutes.CONVERSATION_ID_ARG).orEmpty()

    init {
        launchIO {
            getAuthUserFlowUseCase().collect { user ->
                updateState { copy(coinBalance = user?.coinBalance ?: 0) }
            }
        }
    }

    override fun handleIntent(intent: ChatRoomIntent) {
        when (intent) {
            ChatRoomIntent.Initialize -> {
                if (currentState.isLoading) return
                initializeScreen()
                loadCoinBalance()
            }

            is ChatRoomIntent.MessageChanged -> updateState {
                copy(inputMessage = intent.value.take(CHAT_MESSAGE_MAX_LENGTH))
            }

            ChatRoomIntent.SendMessage -> sendMessage()

            is ChatRoomIntent.SendSuggestedMessage -> sendMessage(intent.message)

            is ChatRoomIntent.SendQuickPrompt -> sendQuickPromptMessage(
                intent.promptId,
                intent.promptContent
            )

            is ChatRoomIntent.AssistantAnimationCompleted -> {
                if (currentState.animatingAssistantMessageId == intent.messageId) {
                    updateState { copy(animatingAssistantMessageId = null) }
                }
            }

            ChatRoomIntent.DeleteConversation -> deleteConversation()

            ChatRoomIntent.Retry -> loadMessages(forceReload = true)

            ChatRoomIntent.OpenCustomBackground -> {
                if (currentState.backgrounds.isEmpty()) {
                    loadBackgrounds()
                }
            }

            is ChatRoomIntent.SelectBackground -> selectBackground(intent.backgroundId)

            is ChatRoomIntent.PurchaseBackground -> purchaseBackground(intent.backgroundId)

            ChatRoomIntent.RateSubmitted -> {
                appSharedPref.isRate = true
            }

            is ChatRoomIntent.Report -> submitReport(intent.reason)
        }
    }

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private fun initializeScreen() {
        updateState { copy(isLoading = true, error = null) }
        launchIO {
            val cached = getCachedConversationByIdUseCase(conversationId)
            if (cached != null) {
                var level = 1
                var relationshipTitle = context.getString(R.string.chat_relationship_stranger)

                if (cached.characterId.isNotBlank()) {
                    loadBackgrounds(cached.characterId)
                    when (val progressResult = getCharacterProgressUseCase(cached.characterId)) {
                        is AppResult.Success -> {
                            level = progressResult.data.level
                            relationshipTitle = progressResult.data.relationshipTitle
                        }

                        else -> Unit
                    }
                }

                updateState {
                    copy(
                        conversationId = conversationId,
                        characterId = cached.characterId,
                        title = cached.character?.name
                            ?: context.getString(
                                R.string.chat_title_format,
                                conversationId.take(8)
                            ),
                        subtitle = context.getString(
                            R.string.chat_subtitle_format,
                            relationshipTitle,
                            level
                        ),
                        assistantName = cached.character?.name.orEmpty(),
                        assistantAvatarUrl = cached.character?.image,
                        currentBackgroundId = cached.backgroundId,
                        currentBackgroundUrl = cached.background?.imageUrl
                    )
                }
            } else {
                updateState {
                    copy(
                        conversationId = conversationId,
                        title = context.getString(
                            R.string.chat_title_format,
                            conversationId.take(8)
                        ),
                        subtitle = context.getString(R.string.chat_status_online)
                    )
                }
            }

            launchIO {
                val promptsResult = getQuickPromptsUseCase(PaginationQuery(page = 1, limit = 20))
                if (promptsResult is AppResult.Success) {
                    updateState { copy(quickPrompts = promptsResult.data.items) }
                }
            }

            loadMessages(forceReload = true)
        }
    }

    private fun loadCoinBalance() {
        launchIO {
            when (val result = getCoinBalanceUseCase()) {
                is AppResult.Success -> {
                    updateState {
                        copy(
                            coinBalance = result.data.balance,
                        )
                    }
                }

                is AppResult.Failure -> Unit
            }
        }
    }

    private fun loadMessages(forceReload: Boolean = false) {
        if (conversationId.isBlank()) return
        if (!forceReload && currentState.messages.isNotEmpty()) return

        launchIO {
            updateState { copy(isLoading = true, error = null) }

            when (val conversationResult = getConversationUseCase(conversationId)) {
                is AppResult.Failure -> Unit
                is AppResult.Success -> {
                    val conversation = conversationResult.data
                    val characterId = conversation.characterId

                    var level = 1
                    var relationshipTitle = context.getString(R.string.chat_relationship_stranger)

                    if (characterId.isNotBlank()) {
                        if (currentState.backgrounds.isEmpty()) {
                            loadBackgrounds(characterId)
                        }
                        when (val progressResult = getCharacterProgressUseCase(characterId)) {
                            is AppResult.Success -> {
                                level = progressResult.data.level
                                relationshipTitle = progressResult.data.relationshipTitle
                            }

                            else -> Unit
                        }
                    }

                    updateState {
                        copy(
                            conversationId = conversation.id,
                            characterId = characterId,
                            title = conversation.character?.name
                                ?: context.getString(
                                    R.string.chat_title_format,
                                    conversation.id.take(8)
                                ),
                            subtitle = context.getString(
                                R.string.chat_subtitle_format,
                                relationshipTitle,
                                level
                            ),
                            assistantName = conversation.character?.name.orEmpty(),
                            assistantAvatarUrl = conversation.character?.image,
                            currentBackgroundId = conversation.backgroundId,
                            currentBackgroundUrl = conversation.background?.imageUrl
                        )
                    }
                }
            }

            when (val result = getConversationMessagesUseCase(conversationId)) {
                is AppResult.Failure -> {
                    updateState { copy(isLoading = false, error = result.error) }
                }

                is AppResult.Success -> {
                    updateState {
                        copy(
                            isLoading = false,
                            conversationId = conversationId,
                            messages = result.data,
                            error = null
                        )
                    }
                }
            }
        }
    }

    private fun sendMessage(suggestedMessage: String? = null) {
        val content = (suggestedMessage ?: currentState.inputMessage)
            .take(CHAT_MESSAGE_MAX_LENGTH)
            .trim()

        if (conversationId.isBlank() || content.isBlank() || currentState.isSending) return

        val optimisticMessage = createOptimisticUserMessage(content)
        val requestStartedAt = SystemClock.elapsedRealtime()

        updateState {
            copy(
                inputMessage = "",
                isSending = true,
                isAssistantTyping = true,
                messages = messages + optimisticMessage,
                error = null
            )
        }

        launchIO {
            when (
                val result = sendChatMessageUseCase(
                    SendChatMessageInput(
                        conversationId = conversationId,
                        content = content
                    )
                )
            ) {
                is AppResult.Failure -> {
                    updateState {
                        copy(
                            isSending = false,
                            isAssistantTyping = false,
                            inputMessage = content,
                            messages = messages.filterNot { it.id == optimisticMessage.id },
                            error = result.error
                        )
                    }
                }

                is AppResult.Success -> {
                    val elapsed = SystemClock.elapsedRealtime() - requestStartedAt
                    delay((MINIMUM_TYPING_DURATION_MS - elapsed).coerceAtLeast(0L))

                    updateState {
                        copy(
                            isSending = false,
                            isAssistantTyping = false,
                            animatingAssistantMessageId = result.data.assistantMessage.id,
                            messages = messages
                                .filterNot { it.id == optimisticMessage.id } +
                                    result.data.userMessage +
                                    result.data.assistantMessage,
                            error = null
                        )
                    }
                    maybeShowRateDialog()
                }
            }
        }
    }

    private fun sendQuickPromptMessage(promptId: String, promptContent: String) {
        if (conversationId.isBlank() || currentState.isSending) return

        val prompt = currentState.quickPrompts.find { it.id == promptId }
        prompt?.let {
            chatActionTracker.trackIntent(
                screen = SCREEN_NAME,
                characterId = currentState.characterId,
                prompt = it,
                coinBalance = currentState.coinBalance
            )
        }
        val optimisticMessage = createOptimisticUserMessage(promptContent)
        val requestStartedAt = SystemClock.elapsedRealtime()

        updateState {
            copy(
                isSending = true,
                isAssistantTyping = true,
                messages = messages + optimisticMessage,
                error = null
            )
        }

        launchIO {
            when (
                val result = sendChatMessageUseCase(
                    SendChatMessageInput(
                        conversationId = conversationId,
                        content = null,
                        quickPromptId = promptId
                    )
                )
            ) {
                is AppResult.Failure -> {
                    chatActionTracker.trackFailure(
                        screen = SCREEN_NAME,
                        characterId = currentState.characterId,
                        prompt = prompt,
                        promptId = promptId,
                        coinBalance = currentState.coinBalance,
                        error = result.error
                    )
                    updateState {
                        copy(
                            isSending = false,
                            isAssistantTyping = false,
                            messages = messages.filterNot { it.id == optimisticMessage.id },
                            error = result.error
                        )
                    }
                }

                is AppResult.Success -> {
                    chatActionTracker.trackSuccess(
                        screen = SCREEN_NAME,
                        characterId = currentState.characterId,
                        prompt = prompt,
                        promptId = promptId,
                        coinBalance = currentState.coinBalance
                    )
                    val elapsed = SystemClock.elapsedRealtime() - requestStartedAt
                    delay((MINIMUM_TYPING_DURATION_MS - elapsed).coerceAtLeast(0L))

                    updateState {
                        copy(
                            isSending = false,
                            isAssistantTyping = false,
                            animatingAssistantMessageId = result.data.assistantMessage.id,
                            messages = messages.filterNot { it.id == optimisticMessage.id } +
                                    result.data.userMessage +
                                    result.data.assistantMessage,
                            error = null
                        )
                    }
                    maybeShowRateDialog()
                }
            }
        }
    }

    private fun maybeShowRateDialog() {
        appSharedPref.successfulChatMessageCount = appSharedPref.successfulChatMessageCount + 1

        if (!RatePromptPolicy.canShowAfterChatSuccess(appSharedPref)) return

        RatePromptPolicy.markShownInSession(appSharedPref)
        launchIO {
            sendEffect(ChatRoomEffect.ShowRateDialog)
        }
    }

    private fun deleteConversation() {
        if (conversationId.isBlank() || currentState.isLoading) return

        launchIO {
            updateState { copy(isLoading = true, error = null) }

            when (val result = deleteConversationUseCase(conversationId)) {
                is AppResult.Failure -> {
                    updateState { copy(isLoading = false, error = result.error) }
                }

                is AppResult.Success -> {
                    if (result.data) {
                        updateState { copy(isLoading = false, error = null) }
                        sendEffect(ChatRoomEffect.ConversationDeleted)
                    } else {
                        updateState {
                            copy(
                                isLoading = false,
                                error = com.pegas.yuki.virtual.chat.domain.model.common.PublicError(
                                    com.pegas.yuki.virtual.chat.domain.model.common.PublicMessageKey.GENERIC_ERROR
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadBackgrounds(characterId: String? = currentState.characterId) {
        val charId = characterId ?: currentState.characterId ?: return
        if (charId.isBlank()) return
        launchIO {
            when (val result = getCharacterBackgroundsUseCase(charId)) {
                is AppResult.Success -> {
                    updateState { copy(backgrounds = result.data.backgrounds) }
                }

                else -> Unit
            }
        }
    }

    private fun selectBackground(bgId: String) {
        if (conversationId.isBlank()) return
        launchIO {
            val bgUrl = currentState.backgrounds.find { it.id == bgId }?.imageUrl
            updateState {
                copy(
                    currentBackgroundId = bgId,
                    currentBackgroundUrl = bgUrl ?: currentBackgroundUrl
                )
            }
            updateConversationUseCase(UpdateConversationInput(conversationId, backgroundId = bgId))
        }
    }

    private fun purchaseBackground(bgId: String) {
        val charId = currentState.characterId ?: return
        if (currentState.purchasingBackgroundId != null) return
        val background = currentState.backgrounds.find { it.id == bgId }
        if (background == null || !background.isLocked) return

        launchIO {
            updateState { copy(purchasingBackgroundId = bgId) }
            when (
                val result = backgroundPurchaseCoordinator.purchase(
                    screen = SCREEN_NAME,
                    characterId = charId,
                    background = background,
                    coinBalance = currentState.coinBalance
                )
            ) {
                is BackgroundPurchaseResult.Success -> {
                    val updatedBg = result.result.background
                    val updatedList = if (updatedBg != null) {
                        currentState.backgrounds.map { bg ->
                            if (bg.id == updatedBg.id) updatedBg else bg
                        }
                    } else {
                        currentState.backgrounds
                    }
                    updateState {
                        copy(
                            purchasingBackgroundId = null,
                            backgrounds = updatedList
                        )
                    }
                    sendEffect(ChatRoomEffect.ShowMessage(R.string.character_detail_purchase_success))
                    selectBackground(bgId)
                }

                is BackgroundPurchaseResult.Failure -> {
                    sendEffect(ChatRoomEffect.ShowToast(result.error))
                    updateState {
                        copy(
                            purchasingBackgroundId = null,
                            error = result.error
                        )
                    }
                }
            }
        }
    }

    private fun submitReport(reasonDescription: String) {
        if (conversationId.isBlank() || currentState.isLoading) return

        launchIO {
            updateState { copy(isLoading = true, error = null) }
            val packageInfo = try {
                context.packageManager.getPackageInfo(context.packageName, 0)
            } catch (e: Exception) {
                null
            }
            val appVersion = packageInfo?.versionName ?: "1.0.0"

            val input = CreateReportInput(
                reason = "other",
                description = reasonDescription,
                clientMeta = ReportClientMeta(
                    appVersion = appVersion,
                    platform = "android",
                    screen = "chat"
                ),
                conversationId = conversationId
            )

            when (val result = submitReportUseCase(input)) {
                is AppResult.Failure -> {
                    updateState { copy(isLoading = false, error = result.error) }
                }

                is AppResult.Success -> {
                    updateState { copy(isLoading = false, error = null) }
                    sendEffect(ChatRoomEffect.ReportSuccess)
                }
            }
        }
    }

    private fun createOptimisticUserMessage(content: String) = ConversationMessage(
        id = "local_${UUID.randomUUID()}",
        conversationId = conversationId,
        role = ConversationMessageRole.USER,
        content = content,
        tokenCount = 0,
        promptTokens = 0,
        completionTokens = 0,
        provider = null,
        model = null,
        estimatedCostUsd = 0.0,
        createdAt = UTC_DATE_FORMAT.format(Date())
    )

    private companion object {
        const val SCREEN_NAME = "ChatRoomScreen"
        const val MINIMUM_TYPING_DURATION_MS = 3_000L

        val UTC_DATE_FORMAT = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.US
        ).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}
