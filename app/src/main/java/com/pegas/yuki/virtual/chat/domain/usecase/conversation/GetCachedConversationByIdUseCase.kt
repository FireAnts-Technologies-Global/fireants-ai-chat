package com.pegas.yuki.virtual.chat.domain.usecase.conversation

import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationSummary
import com.pegas.yuki.virtual.chat.domain.repository.ConversationRepository
import javax.inject.Inject

class GetCachedConversationByIdUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String): ConversationSummary? =
        conversationRepository.getCachedConversationById(conversationId)
}
