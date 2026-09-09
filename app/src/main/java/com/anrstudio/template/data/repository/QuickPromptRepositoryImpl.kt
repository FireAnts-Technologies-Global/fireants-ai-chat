package com.pegas.aura.aigirlfriend.soul.data.repository

import com.pegas.aura.aigirlfriend.soul.data.network.model.conversation.toDomain
import com.pegas.aura.aigirlfriend.soul.data.network.service.QuickPromptService
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.QuickPromptPage
import com.pegas.aura.aigirlfriend.soul.domain.repository.QuickPromptRepository
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
