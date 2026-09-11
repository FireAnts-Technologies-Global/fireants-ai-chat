package com.pegas.yuki.virtual.chat.domain.usecase.mycharacter

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.domain.model.common.PublicMessageKey
import com.pegas.yuki.virtual.chat.domain.model.conversation.CreateConversationInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.CreateMyCharacterInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.pegas.yuki.virtual.chat.domain.usecase.conversation.CreateConversationUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

data class CreateCharacterAndChatInput(
    val name: String,
    val personality: String,
    val gender: String,
    val age: Int,
    val tags: List<String>,
    val appearancePrompt: String
)

sealed interface CreateCharacterAndChatResult {
    data class Success(val conversationId: String) : CreateCharacterAndChatResult
    data class Failure(val error: PublicError) : CreateCharacterAndChatResult
    data class ImageGenerationFailed(val message: String?) : CreateCharacterAndChatResult
}

class CreateCharacterAndChatUseCase @Inject constructor(
    private val createMyCharacterUseCase: CreateMyCharacterUseCase,
    private val generateMyCharacterImageUseCase: GenerateMyCharacterImageUseCase,
    private val getGeneratedMyCharacterImageStatusUseCase: GetGeneratedMyCharacterImageStatusUseCase,
    private val publishMyCharacterUseCase: PublishMyCharacterUseCase,
    private val createConversationUseCase: CreateConversationUseCase
) {
    suspend operator fun invoke(input: CreateCharacterAndChatInput): CreateCharacterAndChatResult {
        val createResult = createMyCharacterUseCase(
            CreateMyCharacterInput(
                name = input.name,
                personality = input.personality,
                gender = input.gender,
                age = input.age,
                tags = input.tags
            )
        )
        if (createResult is AppResult.Failure) {
            return CreateCharacterAndChatResult.Failure(createResult.error)
        }

        val character = (createResult as AppResult.Success).data
        val imageResult = generateMyCharacterImageUseCase(
            GenerateMyCharacterImageInput(
                userCharacterId = character.id,
                prompt = input.appearancePrompt
            )
        )
        if (imageResult is AppResult.Failure) {
            return CreateCharacterAndChatResult.Failure(imageResult.error)
        }

        val imageTask = (imageResult as AppResult.Success).data
        if (imageTask.taskId.isBlank()) {
            return CreateCharacterAndChatResult.Failure(PublicError(PublicMessageKey.GENERIC_ERROR))
        }

        val imageStatus = pollGeneratedImage(character.id, imageTask.taskId)
        if (imageStatus is AppResult.Failure) {
            return CreateCharacterAndChatResult.Failure(imageStatus.error)
        }

        val completedImage = (imageStatus as AppResult.Success).data
        if (!completedImage.isSuccess) {
            return CreateCharacterAndChatResult.ImageGenerationFailed(completedImage.message)
        }

        val publishResult = publishMyCharacterUseCase(character.id)
        if (publishResult is AppResult.Failure) {
            return CreateCharacterAndChatResult.Failure(publishResult.error)
        }

        val conversationResult = createConversationUseCase(
            CreateConversationInput(characterId = character.id)
        )
        if (conversationResult is AppResult.Failure) {
            return CreateCharacterAndChatResult.Failure(conversationResult.error)
        }

        return CreateCharacterAndChatResult.Success(
            (conversationResult as AppResult.Success).data.id
        )
    }

    private suspend fun pollGeneratedImage(
        userCharacterId: String,
        imageTaskId: String
    ) = repeat(IMAGE_POLL_ATTEMPTS) { attempt ->
        if (attempt > 0) delay(IMAGE_POLL_INTERVAL_MS)
        val result = getGeneratedMyCharacterImageStatusUseCase(userCharacterId, imageTaskId)
        if (result !is AppResult.Success || !result.data.isPending) return result
    }.let {
        getGeneratedMyCharacterImageStatusUseCase(userCharacterId, imageTaskId)
    }

    private companion object {
        const val IMAGE_POLL_ATTEMPTS = 20
        const val IMAGE_POLL_INTERVAL_MS = 3_000L
    }
}
