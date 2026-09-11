package com.pegas.yuki.virtual.chat.domain.repository

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery
import com.pegas.yuki.virtual.chat.domain.model.conversation.QuickPromptPage

interface QuickPromptRepository {
    suspend fun getQuickPrompts(query: PaginationQuery): AppResult<QuickPromptPage>
}
