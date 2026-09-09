package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.QuickPromptPage

interface QuickPromptRepository {
    suspend fun getQuickPrompts(query: PaginationQuery): AppResult<QuickPromptPage>
}
