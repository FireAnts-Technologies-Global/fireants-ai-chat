package com.pegas.yuki.virtual.chat.domain.usecase.conversation

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery
import com.pegas.yuki.virtual.chat.domain.model.conversation.QuickPromptPage
import com.pegas.yuki.virtual.chat.domain.repository.QuickPromptRepository
import javax.inject.Inject

class GetQuickPromptsUseCase @Inject constructor(
    private val quickPromptRepository: QuickPromptRepository
) {
    suspend operator fun invoke(query: PaginationQuery): AppResult<QuickPromptPage> {
        return quickPromptRepository.getQuickPrompts(query)
    }
}
