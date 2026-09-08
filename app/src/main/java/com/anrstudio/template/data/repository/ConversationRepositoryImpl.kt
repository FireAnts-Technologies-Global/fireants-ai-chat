package com.anrstudio.template.data.repository

import com.anrstudio.template.data.local.conversation.ConversationLocalDataSource
import com.anrstudio.template.data.network.model.base.envelopeAdapter
import com.anrstudio.template.data.network.model.conversation.ConversationDto
import com.anrstudio.template.data.network.model.conversation.ConversationMessageDto
import com.anrstudio.template.data.network.model.conversation.CreateConversationRequestDto
import com.anrstudio.template.data.network.model.conversation.DeleteConversationDto
import com.anrstudio.template.data.network.model.conversation.SendChatMessageRequestDto
import com.anrstudio.template.data.network.model.conversation.SendChatResponseDto
import com.anrstudio.template.data.network.model.conversation.UpdateConversationRequestDto
import com.anrstudio.template.data.network.model.conversation.toDomain
import com.anrstudio.template.data.network.model.conversation.withCharacterFallback
import com.anrstudio.template.data.network.service.ConversationService
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.RawJsonPayload
import com.anrstudio.template.domain.model.conversation.ConversationMessage
import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.domain.model.conversation.CreateConversationInput
import com.anrstudio.template.domain.model.conversation.SendChatMessageInput
import com.anrstudio.template.domain.model.conversation.SendChatResult
import com.anrstudio.template.domain.model.conversation.UpdateConversationInput
import com.anrstudio.template.domain.repository.AuthRepository
import com.anrstudio.template.domain.repository.ConversationRepository
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepositoryImpl @Inject constructor(
    private val conversationService: ConversationService,
    private val conversationLocalDataSource: ConversationLocalDataSource,
    private val authRepository: AuthRepository
) : ConversationRepository {

    private val moshi = Moshi.Builder().build()

    private val conversationEnvelopeAdapter = moshi.envelopeAdapter<ConversationDto>()
    private val conversationsEnvelopeAdapter = moshi.envelopeAdapter<List<ConversationDto>>()
    private val conversationMessagesEnvelopeAdapter =
        moshi.envelopeAdapter<List<ConversationMessageDto>>()
    private val sendChatEnvelopeAdapter = moshi.envelopeAdapter<SendChatResponseDto>()
    private val deleteConversationEnvelopeAdapter = moshi.envelopeAdapter<DeleteConversationDto>()

    override suspend fun getOrCreateConversation(input: CreateConversationInput): AppResult<ConversationSummary> {
        conversationLocalDataSource.getLatestByCharacterId(input.characterId)?.let {
            return AppResult.Success(it)
        }

        when (val syncResult = syncConversations()) {
            is AppResult.Success -> {
                syncResult.data.firstOrNull { it.characterId == input.characterId }?.let {
                    return AppResult.Success(it)
                }
            }

            is AppResult.Failure -> Unit
        }

        return createConversationTyped(input)
    }

    override suspend fun syncConversations(): AppResult<List<ConversationSummary>> =
        apiResult("syncConversations") {
            val rawJson = conversationService.getConversations().toRawJsonPayload()
            val conversations = rawJson.toConversations().map { it.toDomain() }
            conversationLocalDataSource.replaceAll(conversations)
            conversations
        }

    override suspend fun getCachedConversationByCharacterId(characterId: String): ConversationSummary? =
        conversationLocalDataSource.getLatestByCharacterId(characterId)

    override suspend fun getCachedConversationById(conversationId: String): ConversationSummary? =
        conversationLocalDataSource.getById(conversationId)

    override suspend fun createConversation(input: CreateConversationInput): AppResult<ConversationSummary> =
        apiResult("createConversation") {
            val rawJson = conversationService.createConversation(
                CreateConversationRequestDto(
                    characterId = input.characterId,
                    backgroundId = input.backgroundId,
                    title = input.title
                )
            ).toRawJsonPayload()
            rawJson.toConversation().toDomain()
                .withCharacterFallback(characterId = input.characterId, title = input.title)
                .also { conversationLocalDataSource.upsert(it) }
        }

    override suspend fun getConversations(): AppResult<List<ConversationSummary>> =
        apiResult("getConversations") {
            val conversations = conversationService.getConversations().toRawJsonPayload()
                .toConversations()
                .map { it.toDomain() }
            conversationLocalDataSource.replaceAll(conversations)
            conversationLocalDataSource.getAll()
        }

    override fun getConversationsFlow(): kotlinx.coroutines.flow.Flow<List<ConversationSummary>> =
        conversationLocalDataSource.getAllFlow()

    override suspend fun getConversation(conversationId: String): AppResult<ConversationSummary> =
        apiResult("getConversation") {
            conversationService.getConversation(conversationId).toRawJsonPayload()
                .toConversation()
                .toDomain()
                .also { conversationLocalDataSource.upsert(it) }
        }

    override suspend fun updateConversation(input: UpdateConversationInput): AppResult<ConversationSummary> =
        apiResult("updateConversation") {
            conversationService.updateConversation(
                conversationId = input.conversationId,
                body = UpdateConversationRequestDto(
                    backgroundId = input.backgroundId,
                    title = input.title
                )
            ).toRawJsonPayload().toConversation().toDomain()
                .also { conversationLocalDataSource.upsert(it) }
        }

    override suspend fun getConversationMessages(conversationId: String): AppResult<List<ConversationMessage>> =
        apiResult("getConversationMessagesData") {
            conversationService.getConversationMessages(conversationId)
                .toRawJsonPayload()
                .toConversationMessages()
                .map { it.toDomain() }
                .also { messages ->
                    messages.lastOrNull()?.let { lastMessage ->
                        conversationLocalDataSource.updateLastMessagePreview(
                            conversationId = conversationId,
                            lastMessagePreview = lastMessage.content,
                            lastMessageAt = lastMessage.createdAt
                        )
                    }
                }
        }

    override suspend fun sendChatMessage(input: SendChatMessageInput): AppResult<SendChatResult> =
        apiResult("sendChatMessageData") {
            conversationService.sendChatMessage(
                conversationId = input.conversationId,
                body = SendChatMessageRequestDto(
                    content = input.content,
                    quickPromptId = input.quickPromptId
                )
            ).toRawJsonPayload().toSendChatResult().toDomain()
                .also { result ->
                    conversationLocalDataSource.updateLastMessagePreview(
                        conversationId = result.conversationId,
                        lastMessagePreview = result.assistantMessage.content,
                        lastMessageAt = result.assistantMessage.createdAt
                    )
                    result.coinBalanceAfter?.let {
                        authRepository.updateLocalCoinBalance(it)
                    }
                }
        }

    override suspend fun deleteConversation(conversationId: String): AppResult<Boolean> =
        apiResult("deleteConversation") {
            val deleted = conversationService.deleteConversation(conversationId)
                .toRawJsonPayload()
                .toDeleteConversationResult()
            check(deleted) { "Server did not confirm conversation deletion" }
            conversationLocalDataSource.remove(conversationId)
            true
        }

    private suspend fun createConversationTyped(input: CreateConversationInput): AppResult<ConversationSummary> =
        createConversation(input)

    private fun RawJsonPayload.toConversation(): ConversationDto =
        toConversationOrNull() ?: error("Missing conversation data")

    private fun RawJsonPayload.toConversationOrNull() =
        conversationEnvelopeAdapter.fromJson(json)?.data

    private fun RawJsonPayload.toConversations(): List<ConversationDto> =
        toConversationsOrNull() ?: emptyList()

    private fun RawJsonPayload.toConversationsOrNull() =
        conversationsEnvelopeAdapter.fromJson(json)?.data

    private fun RawJsonPayload.toConversationMessages() =
        conversationMessagesEnvelopeAdapter.fromJson(json)?.data ?: emptyList()

    private fun RawJsonPayload.toSendChatResult() =
        sendChatEnvelopeAdapter.fromJson(json)?.data ?: error("Missing send chat data")

    private fun RawJsonPayload.toDeleteConversationResult() =
        deleteConversationEnvelopeAdapter.fromJson(json)?.data?.deleted == true
}
