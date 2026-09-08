package com.anrstudio.template.di

import android.content.Context
import androidx.room.Room
import com.anrstudio.template.data.auth.AuthInfoProvider
import com.anrstudio.template.data.auth.AuthInfoProviderImpl
import com.anrstudio.template.data.local.AppDatabase
import com.anrstudio.template.data.local.conversation.ConversationDao
import com.anrstudio.template.data.pref.AppSharedPref
import com.anrstudio.template.data.pref.AppSharedPreferencesApp
import com.anrstudio.template.data.repository.AuthRepositoryImpl
import com.anrstudio.template.data.repository.BillingRepositoryImpl
import com.anrstudio.template.data.repository.CharacterRepositoryImpl
import com.anrstudio.template.data.repository.CoinsRepositoryImpl
import com.anrstudio.template.data.repository.ConversationRepositoryImpl
import com.anrstudio.template.data.repository.MyCharacterRepositoryImpl
import com.anrstudio.template.data.repository.QuickPromptRepositoryImpl
import com.anrstudio.template.data.repository.ReportRepositoryImpl
import com.anrstudio.template.data.revenuecat.RevenueCatRepositoryImpl
import com.anrstudio.template.domain.repository.AuthRepository
import com.anrstudio.template.domain.repository.BillingRepository
import com.anrstudio.template.domain.repository.CharacterRepository
import com.anrstudio.template.domain.repository.CoinsRepository
import com.anrstudio.template.domain.repository.ConversationRepository
import com.anrstudio.template.domain.repository.MyCharacterRepository
import com.anrstudio.template.domain.repository.QuickPromptRepository
import com.anrstudio.template.domain.repository.ReportRepository
import com.anrstudio.template.domain.repository.RevenueCatRepository
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
        impl: QuickPromptRepositoryImpl
    ): QuickPromptRepository = impl

    @Singleton
    @Provides
    fun provideReportRepository(
        impl: ReportRepositoryImpl
    ): ReportRepository = impl
}
