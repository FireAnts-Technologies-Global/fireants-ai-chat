package com.anrstudio.template.domain.repository

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.conversation.ConversationMessage
import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.domain.model.conversation.CreateConversationInput
import com.anrstudio.template.domain.model.conversation.SendChatMessageInput
import com.anrstudio.template.domain.model.conversation.SendChatResult
import com.anrstudio.template.domain.model.conversation.UpdateConversationInput
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    suspend fun getOrCreateConversation(input: CreateConversationInput): AppResult<ConversationSummary>

    suspend fun syncConversations(): AppResult<List<ConversationSummary>>

    suspend fun getCachedConversationByCharacterId(characterId: String): ConversationSummary?

    suspend fun getCachedConversationById(conversationId: String): ConversationSummary?

    suspend fun createConversation(input: CreateConversationInput): AppResult<ConversationSummary>

    suspend fun getConversations(): AppResult<List<ConversationSummary>>

    fun getConversationsFlow(): Flow<List<ConversationSummary>>

    suspend fun getConversation(conversationId: String): AppResult<ConversationSummary>

    suspend fun getConversationMessages(conversationId: String): AppResult<List<ConversationMessage>>

    suspend fun updateConversation(input: UpdateConversationInput): AppResult<ConversationSummary>

    suspend fun sendChatMessage(input: SendChatMessageInput): AppResult<SendChatResult>

    suspend fun deleteConversation(conversationId: String): AppResult<Boolean>
}
