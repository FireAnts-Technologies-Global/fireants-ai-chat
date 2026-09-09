package com.pegas.aura.aigirlfriend.soul.domain.usecase.conversation

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.QuickPromptPage
import com.pegas.aura.aigirlfriend.soul.domain.repository.QuickPromptRepository
import javax.inject.Inject

class GetQuickPromptsUseCase @Inject constructor(
    private val quickPromptRepository: QuickPromptRepository
) {
    suspend operator fun invoke(query: PaginationQuery): AppResult<QuickPromptPage> {
        return quickPromptRepository.getQuickPrompts(query)
    }
}
