package com.pegas.aura.aigirlfriend.soul.domain.usecase.conversation

import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationSummary
import com.pegas.aura.aigirlfriend.soul.domain.repository.ConversationRepository
import javax.inject.Inject

class GetCachedConversationByIdUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String): ConversationSummary? =
        conversationRepository.getCachedConversationById(conversationId)
}
