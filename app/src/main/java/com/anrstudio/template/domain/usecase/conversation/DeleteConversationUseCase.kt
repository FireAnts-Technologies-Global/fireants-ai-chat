package com.pegas.aura.aigirlfriend.soul.domain.usecase.conversation

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.ConversationRepository
import javax.inject.Inject

class DeleteConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String): AppResult<Boolean> =
        conversationRepository.deleteConversation(conversationId)
}
