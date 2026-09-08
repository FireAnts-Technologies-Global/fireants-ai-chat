package com.anrstudio.template.data.network.service

import com.anrstudio.template.data.network.model.conversation.CreateConversationRequestDto
import com.anrstudio.template.data.network.model.conversation.SendChatMessageRequestDto
import com.anrstudio.template.data.network.model.conversation.UpdateConversationRequestDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ConversationService {

    @Headers("Content-Type: application/json")
    @POST("conversations")
    suspend fun createConversation(
        @Body body: CreateConversationRequestDto
    ): ResponseBody

    @GET("conversations")
    suspend fun getConversations(): ResponseBody

    @GET("conversations/{conversationId}")
    suspend fun getConversation(
        @Path("conversationId") conversationId: String
    ): ResponseBody

    @Headers("Content-Type: application/json")
    @PATCH("conversations/{conversationId}")
    suspend fun updateConversation(
        @Path("conversationId") conversationId: String,
        @Body body: UpdateConversationRequestDto
    ): ResponseBody

    @GET("conversations/{conversationId}/messages")
    suspend fun getConversationMessages(
        @Path("conversationId") conversationId: String
    ): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("conversations/{conversationId}/chat")
    suspend fun sendChatMessage(
        @Path("conversationId") conversationId: String,
        @Body body: SendChatMessageRequestDto
    ): ResponseBody

    @DELETE("conversations/{conversationId}")
    suspend fun deleteConversation(
        @Path("conversationId") conversationId: String
    ): ResponseBody
}
