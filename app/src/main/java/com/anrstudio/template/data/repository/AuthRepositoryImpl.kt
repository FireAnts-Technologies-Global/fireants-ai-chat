package com.anrstudio.template.data.repository

import com.anrstudio.template.data.auth.AuthInfoProvider
import com.anrstudio.template.data.network.model.auth.requireData
import com.anrstudio.template.data.network.model.auth.toDomain
import com.anrstudio.template.data.network.model.auth.toGuestSession
import com.anrstudio.template.data.network.model.auth.toRequestDto
import com.anrstudio.template.data.network.model.auth.toTokenSession
import com.anrstudio.template.data.network.service.AuthService
import com.anrstudio.template.domain.model.auth.AuthUser
import com.anrstudio.template.domain.model.auth.GuestSession
import com.anrstudio.template.domain.model.auth.TokenSession
import com.anrstudio.template.domain.model.auth.UpdateMeInput
import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.common.PublicError
import com.anrstudio.template.domain.model.common.PublicMessageKey
import com.anrstudio.template.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val authInfoProvider: AuthInfoProvider
) : AuthRepository {

    private val _authUser = MutableStateFlow<AuthUser?>(null)
    override val authUser: StateFlow<AuthUser?> = _authUser.asStateFlow()

    private val emptyBody = "{}".toRequestBody("application/json".toMediaType())

    override suspend fun guestLogin(): AppResult<GuestSession> = appResult("guestLogin") {
        val response = authService.guestLogin(emptyBody)
        val data = response.requireData()
        authInfoProvider.persistGuestSession(
            accessToken = data.accessToken.orEmpty(),
            refreshToken = data.refreshToken.orEmpty(),
            resumeGuestToken = data.resumeGuestToken.orEmpty(),
            userId = data.user?.id.orEmpty()
        )
        data.toGuestSession()
    }

    override suspend fun refreshToken(): AppResult<TokenSession> = appResult("refreshToken") {
        val response = authService.refreshToken(emptyBody)
        val data = response.requireData()
        authInfoProvider.persistRefreshedTokens(
            accessToken = data.accessToken.orEmpty(),
            refreshToken = data.refreshToken.orEmpty(),
            resumeGuestToken = data.resumeGuestToken.orEmpty()
        )
        data.toTokenSession()
    }

    override suspend fun me(): AppResult<AuthUser> =
        appResult("me") {
            authService.me().requireData().toDomain().also { user ->
                _authUser.value = user
            }
        }

    override suspend fun updateMe(input: UpdateMeInput): AppResult<AuthUser> =
        appResult("updateMe") {
            authService.updateMe(input.toRequestDto()).requireData().toDomain().also { user ->
                _authUser.value = user
            }
        }

    override suspend fun logout(): AppResult<Unit> = appResult("logout") {
        authService.logout()
        authInfoProvider.clearAuth()
        _authUser.value = null
    }

    override fun updateLocalCoinBalance(balance: Int) {
        val currentUser = _authUser.value ?: return
        if (currentUser.coinBalance != balance) {
            _authUser.value = currentUser.copy(coinBalance = balance)
        }
    }

    private suspend fun <T> appResult(
        operation: String,
        block: suspend () -> T
    ): AppResult<T> = try {
        AppResult.Success(block())
    } catch (throwable: Throwable) {
        Timber.w(throwable, "Auth repository operation failed: %s", operation)
        AppResult.Failure(throwable.toPublicError())
    }

    private fun Throwable.toPublicError(): PublicError = when (this) {
        is IOException -> PublicError(PublicMessageKey.NETWORK_UNAVAILABLE)
        is HttpException -> when (code()) {
            401, 403 -> PublicError(PublicMessageKey.AUTH_SESSION_EXPIRED)
            else -> PublicError(PublicMessageKey.AUTH_UNAVAILABLE)
        }

        else -> PublicError(PublicMessageKey.GENERIC_ERROR)
    }
}
