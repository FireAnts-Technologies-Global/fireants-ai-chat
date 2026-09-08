package com.anrstudio.template.domain.usecase.conversation

import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.domain.repository.ConversationRepository
import javax.inject.Inject

class GetCachedConversationByCharacterIdUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(characterId: String): ConversationSummary? =
        conversationRepository.getCachedConversationByCharacterId(characterId)
}
