package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom

import androidx.annotation.StringRes
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterBackground
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationMessage
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseUiState

internal const val CHAT_MESSAGE_MAX_LENGTH = 500

data class ChatRoomUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val conversationId: String = "",
    val title: String = "",
    val subtitle: String = "",
    val assistantName: String = "",
    val assistantAvatarUrl: String? = null,
    val messages: List<ConversationMessage> = emptyList(),
    val inputMessage: String = "",
    val isSending: Boolean = false,
    val isAssistantTyping: Boolean = false,
    val animatingAssistantMessageId: String? = null,
    val coinBalance: Int = 0,
    val quickPrompts: List<com.pegas.aura.aigirlfriend.soul.domain.model.conversation.QuickPrompt> = emptyList(),
    val characterId: String? = null,
    val backgrounds: List<CharacterBackground> = emptyList(),
    val currentBackgroundId: String? = null,
    val currentBackgroundUrl: String? = null,
    val purchasingBackgroundId: String? = null
) : BaseUiState

sealed interface ChatRoomIntent {
    data object Initialize : ChatRoomIntent

    data class MessageChanged(val value: String) : ChatRoomIntent

    data object SendMessage : ChatRoomIntent

    data class SendSuggestedMessage(val message: String) : ChatRoomIntent

    data class SendQuickPrompt(val promptId: String, val promptContent: String) : ChatRoomIntent

    data class AssistantAnimationCompleted(val messageId: String) : ChatRoomIntent

    data object DeleteConversation : ChatRoomIntent

    data object Retry : ChatRoomIntent

    data object OpenCustomBackground : ChatRoomIntent

    data class SelectBackground(val backgroundId: String) : ChatRoomIntent

    data class PurchaseBackground(val backgroundId: String) : ChatRoomIntent

    data object RateSubmitted : ChatRoomIntent

    data class Report(val reason: String) : ChatRoomIntent
}

sealed interface ChatRoomEffect {
    data object ConversationDeleted : ChatRoomEffect
    data object ShowRateDialog : ChatRoomEffect
    data object ReportSuccess : ChatRoomEffect
    data class ShowMessage(@StringRes val messageRes: Int) : ChatRoomEffect
    data class ShowToast(val error: PublicError) : ChatRoomEffect
}
