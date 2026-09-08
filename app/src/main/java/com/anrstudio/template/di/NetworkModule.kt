package com.anrstudio.template.di

import android.content.Context
import com.anrstudio.template.data.auth.AuthInfoProvider
import com.anrstudio.template.data.network.auth.AuthInterceptor
import com.anrstudio.template.data.network.auth.TokenAuthenticator
import com.anrstudio.template.data.network.interceptor.ErrorFileLoggingInterceptor
import com.anrstudio.template.data.network.service.AuthService
import com.anrstudio.template.data.network.service.BillingService
import com.anrstudio.template.data.network.service.CharacterService
import com.anrstudio.template.data.network.service.CoinsService
import com.anrstudio.template.data.network.service.ConversationService
import com.anrstudio.template.data.network.service.MyCharacterService
import com.anrstudio.template.data.network.service.QuickPromptService
import com.anrstudio.template.data.network.service.ReportService
import com.pegas.aura.aigirlfriend.soul.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        authInfoProvider: AuthInfoProvider
    ) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(ErrorFileLoggingInterceptor(context))
            .addInterceptor(AuthInterceptor(authInfoProvider))
            .addInterceptor(loggingInterceptor)
            .authenticator(TokenAuthenticator(authInfoProvider, BuildConfig.BASE_URL))
            .build()
    } else {
        OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor(authInfoProvider))
            .authenticator(TokenAuthenticator(authInfoProvider, BuildConfig.BASE_URL))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun provideConversationService(retrofit: Retrofit): ConversationService =
        retrofit.create(ConversationService::class.java)

    @Provides
    @Singleton
    fun provideCoinsService(retrofit: Retrofit): CoinsService =
        retrofit.create(CoinsService::class.java)

    @Provides
    @Singleton
    fun provideBillingService(retrofit: Retrofit): BillingService =
        retrofit.create(BillingService::class.java)

    @Provides
    @Singleton
    fun provideMyCharacterService(retrofit: Retrofit): MyCharacterService =
        retrofit.create(MyCharacterService::class.java)

    @Provides
    @Singleton
    fun provideQuickPromptService(retrofit: Retrofit): QuickPromptService =
        retrofit.create(QuickPromptService::class.java)

    @Provides
    @Singleton
    fun provideReportService(retrofit: Retrofit): ReportService =
        retrofit.create(ReportService::class.java)
}
