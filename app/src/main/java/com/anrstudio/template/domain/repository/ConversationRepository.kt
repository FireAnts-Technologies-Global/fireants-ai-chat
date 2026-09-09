package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationMessage
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationSummary
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.CreateConversationInput
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.SendChatMessageInput
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.SendChatResult
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.UpdateConversationInput
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
