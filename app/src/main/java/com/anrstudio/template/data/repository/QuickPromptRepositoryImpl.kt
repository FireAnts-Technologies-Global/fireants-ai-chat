package com.anrstudio.template.data.repository

import com.anrstudio.template.data.network.model.conversation.toDomain
import com.anrstudio.template.data.network.service.QuickPromptService
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PaginationQuery
import com.anrstudio.template.domain.model.conversation.QuickPromptPage
import com.anrstudio.template.domain.repository.QuickPromptRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuickPromptRepositoryImpl @Inject constructor(
    private val quickPromptService: QuickPromptService
) : QuickPromptRepository {

    override suspend fun getQuickPrompts(query: PaginationQuery): AppResult<QuickPromptPage> =
        apiResult("getQuickPrompts") {
            quickPromptService.getQuickPrompts(query.page, query.limit)
                .data?.toDomain() ?: error("Missing quick prompts data")
        }
}
