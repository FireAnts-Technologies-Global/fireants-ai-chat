package com.anrstudio.template.ui.component.chatlist


import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PublicError
import com.anrstudio.template.domain.model.common.PublicMessageKey
import com.anrstudio.template.domain.usecase.conversation.DeleteConversationUseCase
import com.anrstudio.template.domain.usecase.conversation.ObserveConversationsUseCase
import com.anrstudio.template.domain.usecase.conversation.SyncConversationsUseCase
import com.anrstudio.template.ui.bases.compose.mvi.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val observeConversationsUseCase: ObserveConversationsUseCase,
    private val syncConversationsUseCase: SyncConversationsUseCase,
    private val deleteConversationUseCase: DeleteConversationUseCase
) : BaseComposeViewModel<ChatListUiState, ChatListIntent, ChatListEffect>(ChatListUiState()) {

    init {
        launchIO {
            observeConversationsUseCase().collect { conversations ->
                updateState {
                    copy(conversations = conversations)
                }
            }
        }
    }

    override fun handleIntent(intent: ChatListIntent) {
        when (intent) {
            ChatListIntent.Initialize -> {
                if (currentState.hasLoadedInitialData || currentState.isLoading) return
                loadConversations()
            }

            is ChatListIntent.OpenConversation -> {
                if (intent.conversationId.isBlank()) return
                sendEffect(ChatListEffect.NavigateToConversation(intent.conversationId))
            }

            is ChatListIntent.DeleteConversation -> {
                if (intent.conversationId.isBlank() || currentState.deletingConversationId != null) return
                deleteConversation(intent.conversationId)
            }

            is ChatListIntent.CreateAssistant -> {
                sendEffect(ChatListEffect.CreateAssistant)
            }

            ChatListIntent.Retry -> loadConversations(forceReload = true)
        }
    }

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private fun loadConversations(forceReload: Boolean = false) {
        if (!forceReload && currentState.hasLoadedInitialData) return

        launchIO {
            updateState { copy(isLoading = true, error = null) }

            when (val result = syncConversationsUseCase()) {
                is AppResult.Failure -> {
                    updateState {
                        copy(
                            isLoading = false,
                            error = result.error,
                            hasLoadedInitialData = false
                        )
                    }
                }

                is AppResult.Success -> {
                    updateState {
                        copy(
                            isLoading = false,
                            deletingConversationId = null,
                            hasLoadedInitialData = true,
                            error = null
                        )
                    }
                }
            }
        }
    }

    private fun deleteConversation(conversationId: String) {
        launchIO {
            updateState { copy(deletingConversationId = conversationId, error = null) }

            when (val result = deleteConversationUseCase(conversationId)) {
                is AppResult.Failure -> {
                    updateState { copy(deletingConversationId = null, error = result.error) }
                }

                is AppResult.Success -> {
                    if (result.data) {
                        updateState {
                            copy(
                                deletingConversationId = null,
                                conversations = conversations.filterNot { it.id == conversationId },
                                error = null
                            )
                        }
                        sendEffect(ChatListEffect.ConversationDeleted)
                    } else {
                        updateState {
                            copy(
                                deletingConversationId = null,
                                error = PublicError(
                                    PublicMessageKey.GENERIC_ERROR
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
