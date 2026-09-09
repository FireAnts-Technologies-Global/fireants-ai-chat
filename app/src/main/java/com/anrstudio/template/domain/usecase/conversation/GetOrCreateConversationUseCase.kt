package com.pegas.aura.aigirlfriend.soul.domain.usecase.conversation

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationSummary
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.CreateConversationInput
import com.pegas.aura.aigirlfriend.soul.domain.repository.ConversationRepository
import javax.inject.Inject

class GetOrCreateConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(input: CreateConversationInput): AppResult<ConversationSummary> =
        conversationRepository.getOrCreateConversation(input)
}
