package com.anrstudio.template.domain.usecase.conversation

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.conversation.ConversationMessage
import com.anrstudio.template.domain.repository.ConversationRepository
import javax.inject.Inject

class GetConversationMessagesUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String): AppResult<List<ConversationMessage>> =
        conversationRepository.getConversationMessages(conversationId)
}
