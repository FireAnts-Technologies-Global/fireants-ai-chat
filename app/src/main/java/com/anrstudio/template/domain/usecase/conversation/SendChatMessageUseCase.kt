package com.anrstudio.template.domain.usecase.conversation

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.conversation.SendChatMessageInput
import com.anrstudio.template.domain.model.conversation.SendChatResult
import com.anrstudio.template.domain.repository.ConversationRepository
import javax.inject.Inject

class SendChatMessageUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(input: SendChatMessageInput): AppResult<SendChatResult> =
        conversationRepository.sendChatMessage(input)
}
