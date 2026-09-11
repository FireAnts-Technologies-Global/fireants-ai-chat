package com.pegas.yuki.virtual.chat.di

import android.content.Context
import com.pegas.yuki.virtual.chat.BuildConfig
import com.pegas.yuki.virtual.chat.data.auth.AuthInfoProvider
import com.pegas.yuki.virtual.chat.data.network.auth.AuthInterceptor
import com.pegas.yuki.virtual.chat.data.network.auth.TokenAuthenticator
import com.pegas.yuki.virtual.chat.data.network.interceptor.ErrorFileLoggingInterceptor
import com.pegas.yuki.virtual.chat.data.network.service.AuthService
import com.pegas.yuki.virtual.chat.data.network.service.BillingService
import com.pegas.yuki.virtual.chat.data.network.service.CharacterService
import com.pegas.yuki.virtual.chat.data.network.service.CoinsService
import com.pegas.yuki.virtual.chat.data.network.service.ConversationService
import com.pegas.yuki.virtual.chat.data.network.service.MyCharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        authInfoProvider: AuthInfoProvider
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(authInfoProvider))
            .authenticator(TokenAuthenticator(authInfoProvider, BuildConfig.BASE_URL))

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            builder
                .addInterceptor(ErrorFileLoggingInterceptor(context))
                .addInterceptor(loggingInterceptor)
        }

        return builder.build()
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
    fun provideQuickPromptService(retrofit: Retrofit): com.pegas.yuki.virtual.chat.data.network.service.QuickPromptService =
        retrofit.create(com.pegas.yuki.virtual.chat.data.network.service.QuickPromptService::class.java)

    @Provides
    @Singleton
    fun provideReportService(retrofit: Retrofit): com.pegas.yuki.virtual.chat.data.network.service.ReportService =
        retrofit.create(com.pegas.yuki.virtual.chat.data.network.service.ReportService::class.java)

    @Provides
    @Singleton
    fun providePromoCodeService(retrofit: Retrofit): com.pegas.yuki.virtual.chat.data.network.service.PromoCodeService =
        retrofit.create(com.pegas.yuki.virtual.chat.data.network.service.PromoCodeService::class.java)
}
