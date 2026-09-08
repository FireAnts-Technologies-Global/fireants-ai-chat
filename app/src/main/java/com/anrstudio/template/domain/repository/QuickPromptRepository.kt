package com.anrstudio.template.domain.repository

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PaginationQuery
import com.anrstudio.template.domain.model.conversation.QuickPromptPage

interface QuickPromptRepository {
    suspend fun getQuickPrompts(query: PaginationQuery): AppResult<QuickPromptPage>
}
