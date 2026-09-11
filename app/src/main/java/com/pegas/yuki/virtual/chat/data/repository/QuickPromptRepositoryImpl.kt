package com.pegas.yuki.virtual.chat.data.repository

import com.pegas.yuki.virtual.chat.data.network.model.conversation.toDomain
import com.pegas.yuki.virtual.chat.data.network.service.QuickPromptService
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery
import com.pegas.yuki.virtual.chat.domain.model.conversation.QuickPromptPage
import com.pegas.yuki.virtual.chat.domain.repository.QuickPromptRepository
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
