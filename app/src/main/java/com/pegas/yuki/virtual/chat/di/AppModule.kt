package com.pegas.yuki.virtual.chat.di

import android.content.Context
import androidx.room.Room
import com.pegas.yuki.virtual.chat.data.auth.AuthInfoProvider
import com.pegas.yuki.virtual.chat.data.auth.AuthInfoProviderImpl
import com.pegas.yuki.virtual.chat.data.local.AppDatabase
import com.pegas.yuki.virtual.chat.data.local.conversation.ConversationDao
import com.pegas.yuki.virtual.chat.data.pref.AppSharedPref
import com.pegas.yuki.virtual.chat.data.pref.AppSharedPreferencesApp
import com.pegas.yuki.virtual.chat.data.repository.AuthRepositoryImpl
import com.pegas.yuki.virtual.chat.data.repository.BillingRepositoryImpl
import com.pegas.yuki.virtual.chat.data.repository.CharacterRepositoryImpl
import com.pegas.yuki.virtual.chat.data.repository.CoinsRepositoryImpl
import com.pegas.yuki.virtual.chat.data.repository.ConversationRepositoryImpl
import com.pegas.yuki.virtual.chat.data.repository.MyCharacterRepositoryImpl
import com.pegas.yuki.virtual.chat.data.revenuecat.RevenueCatRepositoryImpl
import com.pegas.yuki.virtual.chat.domain.repository.AuthRepository
import com.pegas.yuki.virtual.chat.domain.repository.BillingRepository
import com.pegas.yuki.virtual.chat.domain.repository.CharacterRepository
import com.pegas.yuki.virtual.chat.domain.repository.CoinsRepository
import com.pegas.yuki.virtual.chat.domain.repository.ConversationRepository
import com.pegas.yuki.virtual.chat.domain.repository.MyCharacterRepository
import com.pegas.yuki.virtual.chat.domain.repository.RevenueCatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideSharedPref(
        @ApplicationContext context: Context
    ): AppSharedPref = AppSharedPreferencesApp(context)

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "fireants_app.db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideConversationDao(database: AppDatabase): ConversationDao =
        database.conversationDao()

    @Singleton
    @Provides
    fun provideAuthInfoProvider(
        impl: AuthInfoProviderImpl
    ): AuthInfoProvider = impl

    @Singleton
    @Provides
    fun provideAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository = impl

    @Singleton
    @Provides
    fun provideCharacterRepository(
        impl: CharacterRepositoryImpl
    ): CharacterRepository = impl

    @Singleton
    @Provides
    fun provideConversationRepository(
        impl: ConversationRepositoryImpl
    ): ConversationRepository = impl

    @Singleton
    @Provides
    fun provideCoinsRepository(
        impl: CoinsRepositoryImpl
    ): CoinsRepository = impl

    @Singleton
    @Provides
    fun provideBillingRepository(
        impl: BillingRepositoryImpl
    ): BillingRepository = impl

    @Singleton
    @Provides
    fun provideMyCharacterRepository(
        impl: MyCharacterRepositoryImpl
    ): MyCharacterRepository = impl

    @Singleton
    @Provides
    fun provideRevenueCatRepository(
        impl: RevenueCatRepositoryImpl
    ): RevenueCatRepository = impl

    @Singleton
    @Provides
    fun provideQuickPromptRepository(
        impl: com.pegas.yuki.virtual.chat.data.repository.QuickPromptRepositoryImpl
    ): com.pegas.yuki.virtual.chat.domain.repository.QuickPromptRepository = impl

    @Singleton
    @Provides
    fun provideReportRepository(
        impl: com.pegas.yuki.virtual.chat.data.repository.ReportRepositoryImpl
    ): com.pegas.yuki.virtual.chat.domain.repository.ReportRepository = impl

    @Singleton
    @Provides
    fun providePromoCodeRepository(
        impl: com.pegas.yuki.virtual.chat.data.repository.PromoCodeRepositoryImpl
    ): com.pegas.yuki.virtual.chat.domain.repository.PromoCodeRepository = impl
}
