package com.pegas.yuki.virtual.chat.domain.usecase.conversation

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationSummary
import com.pegas.yuki.virtual.chat.domain.model.conversation.UpdateConversationInput
import com.pegas.yuki.virtual.chat.domain.repository.ConversationRepository
import javax.inject.Inject

class UpdateConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(input: UpdateConversationInput): AppResult<ConversationSummary> =
        conversationRepository.updateConversation(input)
}
