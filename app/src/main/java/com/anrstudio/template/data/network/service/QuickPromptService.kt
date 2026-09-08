package com.anrstudio.template.data.network.service

import com.anrstudio.template.data.network.model.conversation.QuickPromptsEnvelopeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface QuickPromptService {
    @GET("quick-prompts")
    suspend fun getQuickPrompts(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): QuickPromptsEnvelopeDto
}
