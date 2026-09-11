package com.pegas.yuki.virtual.chat.data.local.conversation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {

    @Query(
        """
        SELECT * FROM conversations
        WHERE userId = :userId AND characterId = :characterId
        ORDER BY updatedAt DESC, createdAt DESC
        LIMIT 1
        """
    )
    suspend fun getLatestConversationByCharacterId(
        userId: String,
        characterId: String
    ): ConversationEntity?

    @Query(
        """
        SELECT * FROM conversations
        WHERE userId = :userId
        ORDER BY updatedAt DESC, createdAt DESC
        """
    )
    suspend fun getConversationsByUserId(userId: String): List<ConversationEntity>

    @Query(
        """
        SELECT * FROM conversations
        WHERE userId = :userId
        ORDER BY updatedAt DESC, createdAt DESC
        """
    )
    fun getConversationsFlowByUserId(userId: String): Flow<List<ConversationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(conversation: ConversationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(conversations: List<ConversationEntity>)

    @Query("SELECT * FROM conversations WHERE id = :conversationId LIMIT 1")
    suspend fun getById(conversationId: String): ConversationEntity?

    @Query(
        """
        UPDATE conversations
        SET lastMessagePreview = :lastMessagePreview,
            lastMessageAt = COALESCE(:lastMessageAt, lastMessageAt)
        WHERE id = :conversationId
        """
    )
    suspend fun updateLastMessagePreview(
        conversationId: String,
        lastMessagePreview: String,
        lastMessageAt: String?
    )

    @Query("DELETE FROM conversations WHERE id = :conversationId")
    suspend fun deleteById(conversationId: String)

    @Query("DELETE FROM conversations WHERE userId = :userId")
    suspend fun deleteAllByUserId(userId: String)

    @Transaction
    suspend fun replaceAllForUser(
        userId: String,
        conversations: List<ConversationEntity>
    ) {
        deleteAllByUserId(userId)
        if (conversations.isNotEmpty()) {
            upsertAll(conversations)
        }
    }
}
