package com.anrstudio.template.domain.usecase.conversation

import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.domain.repository.ConversationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConversationsUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    operator fun invoke(): Flow<List<ConversationSummary>> =
        conversationRepository.getConversationsFlow()
}
