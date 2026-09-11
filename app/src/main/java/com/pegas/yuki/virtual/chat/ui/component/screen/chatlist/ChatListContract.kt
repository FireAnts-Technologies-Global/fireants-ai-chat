package com.pegas.yuki.virtual.chat.ui.component.screen.chatlist

import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationSummary
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseUiState

data class ChatListUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val deletingConversationId: String? = null,
    val conversations: List<ConversationSummary> = emptyList(),
    val hasLoadedInitialData: Boolean = false
) : BaseUiState

sealed interface ChatListIntent {
    data object Initialize : ChatListIntent

    data class OpenConversation(val conversationId: String) : ChatListIntent

    data class DeleteConversation(val conversationId: String) : ChatListIntent
    object CreateAssistant : ChatListIntent

    data object Retry : ChatListIntent
}

sealed interface ChatListEffect {
    data class NavigateToConversation(val conversationId: String) : ChatListEffect

    data object ConversationDeleted : ChatListEffect
    data object CreateAssistant : ChatListEffect
}
