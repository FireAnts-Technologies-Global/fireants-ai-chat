package com.anrstudio.template.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anrstudio.template.data.local.conversation.ConversationDao
import com.anrstudio.template.data.local.conversation.ConversationEntity

@Database(
    entities = [ConversationEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
}
