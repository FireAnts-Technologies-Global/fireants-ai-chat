package com.pegas.aura.aigirlfriend.soul.di

import android.content.Context
import androidx.room.Room
import com.pegas.aura.aigirlfriend.soul.data.auth.AuthInfoProvider
import com.pegas.aura.aigirlfriend.soul.data.auth.AuthInfoProviderImpl
import com.pegas.aura.aigirlfriend.soul.data.local.AppDatabase
import com.pegas.aura.aigirlfriend.soul.data.local.conversation.ConversationDao
import com.pegas.aura.aigirlfriend.soul.data.pref.AppSharedPref
import com.pegas.aura.aigirlfriend.soul.data.pref.AppSharedPreferencesApp
import com.pegas.aura.aigirlfriend.soul.data.repository.AuthRepositoryImpl
import com.pegas.aura.aigirlfriend.soul.data.repository.BillingRepositoryImpl
import com.pegas.aura.aigirlfriend.soul.data.repository.CharacterRepositoryImpl
import com.pegas.aura.aigirlfriend.soul.data.repository.CoinsRepositoryImpl
import com.pegas.aura.aigirlfriend.soul.data.repository.ConversationRepositoryImpl
import com.pegas.aura.aigirlfriend.soul.data.repository.MyCharacterRepositoryImpl
import com.pegas.aura.aigirlfriend.soul.data.revenuecat.RevenueCatRepositoryImpl
import com.pegas.aura.aigirlfriend.soul.domain.repository.AuthRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.BillingRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.CharacterRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.CoinsRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.ConversationRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.MyCharacterRepository
import com.pegas.aura.aigirlfriend.soul.domain.repository.RevenueCatRepository
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
        impl: com.pegas.aura.aigirlfriend.soul.data.repository.QuickPromptRepositoryImpl
    ): com.pegas.aura.aigirlfriend.soul.domain.repository.QuickPromptRepository = impl

    @Singleton
    @Provides
    fun provideReportRepository(
        impl: com.pegas.aura.aigirlfriend.soul.data.repository.ReportRepositoryImpl
    ): com.pegas.aura.aigirlfriend.soul.domain.repository.ReportRepository = impl

    @Singleton
    @Provides
    fun providePromoCodeRepository(
        impl: com.pegas.aura.aigirlfriend.soul.data.repository.PromoCodeRepositoryImpl
    ): com.pegas.aura.aigirlfriend.soul.domain.repository.PromoCodeRepository = impl
}
