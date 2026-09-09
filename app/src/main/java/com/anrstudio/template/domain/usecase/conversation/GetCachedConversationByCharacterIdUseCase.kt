package com.pegas.aura.aigirlfriend.soul.domain.usecase.conversation

import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationSummary
import com.pegas.aura.aigirlfriend.soul.domain.repository.ConversationRepository
import javax.inject.Inject

class GetCachedConversationByCharacterIdUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(characterId: String): ConversationSummary? =
        conversationRepository.getCachedConversationByCharacterId(characterId)
}
