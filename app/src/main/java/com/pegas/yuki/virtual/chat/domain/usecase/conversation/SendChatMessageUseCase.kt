package com.pegas.yuki.virtual.chat.domain.usecase.conversation

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.conversation.SendChatMessageInput
import com.pegas.yuki.virtual.chat.domain.model.conversation.SendChatResult
import com.pegas.yuki.virtual.chat.domain.repository.ConversationRepository
import javax.inject.Inject

class SendChatMessageUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(input: SendChatMessageInput): AppResult<SendChatResult> =
        conversationRepository.sendChatMessage(input)
}
