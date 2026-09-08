package com.anrstudio.template.ui.component.chatlist

import com.anrstudio.template.domain.model.common.PublicError
import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.ui.bases.compose.mvi.BaseUiState


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
