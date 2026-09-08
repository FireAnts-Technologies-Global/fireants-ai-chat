package com.anrstudio.template.domain.usecase.conversation

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PaginationQuery
import com.anrstudio.template.domain.model.conversation.QuickPromptPage
import com.anrstudio.template.domain.repository.QuickPromptRepository
import javax.inject.Inject

class GetQuickPromptsUseCase @Inject constructor(
    private val quickPromptRepository: QuickPromptRepository
) {
    suspend operator fun invoke(query: PaginationQuery): AppResult<QuickPromptPage> {
        return quickPromptRepository.getQuickPrompts(query)
    }
}
