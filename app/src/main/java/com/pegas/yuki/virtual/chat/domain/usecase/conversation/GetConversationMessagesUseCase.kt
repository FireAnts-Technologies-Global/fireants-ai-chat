package com.pegas.yuki.virtual.chat.domain.usecase.conversation

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationMessage
import com.pegas.yuki.virtual.chat.domain.repository.ConversationRepository
import javax.inject.Inject

class GetConversationMessagesUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String): AppResult<List<ConversationMessage>> =
        conversationRepository.getConversationMessages(conversationId)
}
