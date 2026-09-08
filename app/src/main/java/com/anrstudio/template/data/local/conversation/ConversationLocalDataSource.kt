package com.anrstudio.template.data.local.conversation

import com.anrstudio.template.data.pref.AppSharedPref
import com.anrstudio.template.domain.model.conversation.ConversationCharacterSummary
import com.anrstudio.template.domain.model.conversation.ConversationSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationLocalDataSource @Inject constructor(
    private val conversationDao: ConversationDao,
    private val appSharedPref: AppSharedPref
) {

    suspend fun getLatestByCharacterId(characterId: String): ConversationSummary? {
        val userId = currentUserId()
        if (userId == null) return null
        return conversationDao.getLatestConversationByCharacterId(userId, characterId)?.toDomain()
    }

    suspend fun getById(conversationId: String): ConversationSummary? {
        return conversationDao.getById(conversationId)?.toDomain()
    }

    suspend fun getAll(): List<ConversationSummary> {
        val userId = currentUserId() ?: return emptyList()
        return conversationDao.getConversationsByUserId(userId).map { it.toDomain() }
    }

    fun getAllFlow(): Flow<List<ConversationSummary>> {
        val userId = currentUserId() ?: return flowOf(emptyList())
        return conversationDao.getConversationsFlowByUserId(userId)
            .map { list -> list.map { it.toDomain() } }
    }

    suspend fun replaceAll(conversations: List<ConversationSummary>) {
        val userId = currentUserId() ?: return
        val existingById = conversationDao.getConversationsByUserId(userId).associateBy { it.id }
        val merged = conversations.map { conversation ->
            val existing = existingById[conversation.id]
            if (conversation.lastMessagePreview == null && existing?.lastMessagePreview != null) {
                conversation.copy(lastMessagePreview = existing.lastMessagePreview)
            } else {
                conversation
            }
        }
        conversationDao.replaceAllForUser(userId, merged.map { it.toEntity() })
    }

    suspend fun upsert(conversation: ConversationSummary) {
        conversationDao.upsert(conversation.toEntity())
    }

    suspend fun remove(conversationId: String) {
        conversationDao.deleteById(conversationId)
    }

    suspend fun updateLastMessagePreview(
        conversationId: String,
        lastMessagePreview: String,
        lastMessageAt: String?
    ) {
        conversationDao.updateLastMessagePreview(
            conversationId = conversationId,
            lastMessagePreview = lastMessagePreview,
            lastMessageAt = lastMessageAt
        )
    }

    private fun currentUserId(): String? = appSharedPref.userId.takeIf { it.isNotBlank() }
}

private fun ConversationEntity.toDomain(): ConversationSummary = ConversationSummary(
    id = id,
    userId = userId,
    characterId = characterId,
    backgroundId = backgroundId,
    title = title,
    lastMessagePreview = lastMessagePreview,
    lastMessageAt = lastMessageAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    character = if (characterId.isBlank()) null else ConversationCharacterSummary(
        id = characterRefId.orEmpty(),
        slug = characterSlug.orEmpty(),
        name = characterName.orEmpty(),
        image = characterImage,
        description = characterDescription.orEmpty(),
        task = characterTask.orEmpty(),
        age = characterAge
    ).takeIf {
        it.slug.isNotBlank() || it.name.isNotBlank() || it.description.isNotBlank() || it.task.isNotBlank() || it.image != null
    }
)

private fun ConversationSummary.toEntity(): ConversationEntity = ConversationEntity(
    id = id,
    userId = userId,
    characterId = characterId,
    backgroundId = backgroundId,
    title = title,
    lastMessagePreview = lastMessagePreview,
    lastMessageAt = lastMessageAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    characterRefId = character?.id,
    characterSlug = character?.slug,
    characterName = character?.name,
    characterImage = character?.image,
    characterDescription = character?.description,
    characterTask = character?.task,
    characterAge = character?.age
)
