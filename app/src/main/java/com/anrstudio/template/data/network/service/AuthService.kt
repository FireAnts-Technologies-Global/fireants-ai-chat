package com.pegas.aura.aigirlfriend.soul.data.network.service

import com.pegas.aura.aigirlfriend.soul.data.network.model.auth.AuthSessionEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.auth.AuthUserEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.auth.LogoutEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.auth.UpdateMeRequestDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {

    @Headers("Content-Type: application/json")
    @POST("auth/guest")
    suspend fun guestLogin(
        @Body body: RequestBody
    ): AuthSessionEnvelopeDto

    @Headers("Content-Type: application/json")
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body body: RequestBody
    ): AuthSessionEnvelopeDto

    @GET("auth/me")
    suspend fun me(): AuthUserEnvelopeDto

    @Headers("Content-Type: application/json")
    @PATCH("auth/me")
    suspend fun updateMe(
        @Body body: UpdateMeRequestDto
    ): AuthUserEnvelopeDto

    @POST("auth/logout")
    suspend fun logout(): LogoutEnvelopeDto
}
