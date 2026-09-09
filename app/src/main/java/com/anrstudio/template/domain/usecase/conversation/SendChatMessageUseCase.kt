package com.pegas.aura.aigirlfriend.soul.domain.usecase.conversation

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.SendChatMessageInput
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.SendChatResult
import com.pegas.aura.aigirlfriend.soul.domain.repository.ConversationRepository
import javax.inject.Inject

class SendChatMessageUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(input: SendChatMessageInput): AppResult<SendChatResult> =
        conversationRepository.sendChatMessage(input)
}
