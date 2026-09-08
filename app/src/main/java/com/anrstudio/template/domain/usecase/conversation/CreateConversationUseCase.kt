package com.anrstudio.template.domain.usecase.conversation

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.domain.model.conversation.CreateConversationInput
import com.anrstudio.template.domain.repository.ConversationRepository
import javax.inject.Inject

class CreateConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(input: CreateConversationInput): AppResult<ConversationSummary> =
        conversationRepository.createConversation(input)
}
