package com.anrstudio.template.ui.component.creat

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.conversation.CreateConversationInput
import com.anrstudio.template.domain.model.mycharacter.CreateMyCharacterInput
import com.anrstudio.template.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.anrstudio.template.domain.usecase.conversation.CreateConversationUseCase
import com.anrstudio.template.domain.usecase.mycharacter.CreateMyCharacterUseCase
import com.anrstudio.template.domain.usecase.mycharacter.GenerateMyCharacterImageUseCase
import com.anrstudio.template.domain.usecase.mycharacter.GetGeneratedMyCharacterImageStatusUseCase
import com.anrstudio.template.domain.usecase.mycharacter.GetMyCharacterCreationGuideUseCase
import com.anrstudio.template.domain.usecase.mycharacter.PublishMyCharacterUseCase
import com.anrstudio.template.ui.bases.compose.mvi.BaseComposeViewModel
import com.pegas.aura.aigirlfriend.soul.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val createMyCharacterUseCase: CreateMyCharacterUseCase,
    private val generateMyCharacterImageUseCase: GenerateMyCharacterImageUseCase,
    private val getGeneratedMyCharacterImageStatusUseCase: GetGeneratedMyCharacterImageStatusUseCase,
    private val getMyCharacterCreationGuideUseCase: GetMyCharacterCreationGuideUseCase,
    private val publishMyCharacterUseCase: PublishMyCharacterUseCase,
    private val createConversationUseCase: CreateConversationUseCase
) : BaseComposeViewModel<CreateUiState, CreateIntent, CreateEffect>(CreateUiState()) {

    override fun handleIntent(intent: CreateIntent) {
        when (intent) {
            is CreateIntent.Initialize -> initialize(intent.defaultPrompt, intent.defaultName)
            CreateIntent.NextStep -> nextStep()
            CreateIntent.PreviousStep -> previousStep()
            is CreateIntent.GenderSelected -> updateState { copy(selectedGender = intent.gender) }
            is CreateIntent.StyleSelected -> updateState { copy(selectedStyle = intent.style) }
            is CreateIntent.PromptChanged -> updateState { copy(prompt = intent.value) }
            is CreateIntent.EthnicitySelected -> updateState { copy(selectedEthnicity = intent.ethnicity) }
            is CreateIntent.AgeSelected -> updateState { copy(selectedAge = intent.value) }
            is CreateIntent.SkinToneSelected -> updateState { copy(selectedSkinTone = intent.index) }
            is CreateIntent.HairSelected -> updateState { copy(selectedHair = intent.hairColor) }
            is CreateIntent.NameChanged -> updateState { copy(companionName = intent.value) }
            is CreateIntent.PersonalitySelected -> updateState { copy(selectedPersonality = intent.value) }
            is CreateIntent.OccupationSelected -> updateState { copy(selectedOccupation = intent.value) }
            is CreateIntent.HobbiesSelected -> updateState { copy(selectedHobbies = intent.values) }
            is CreateIntent.RelationshipSelected -> updateState { copy(selectedRelationship = intent.value) }
        }
    }

    private fun initialize(defaultPrompt: String, defaultName: String) {
        if (currentState.hasInitialized) return
        updateState {
            copy(
                hasInitialized = true,
                prompt = defaultPrompt,
                companionName = defaultName
            )
        }
        loadCreationGuide()
    }

    private fun loadCreationGuide() {
        launchIO {
            when (val result = getMyCharacterCreationGuideUseCase()) {
                is AppResult.Success -> {
                    updateState {
                        copy(imageGenCoinCost = result.data.quota.imageGenCoinCost)
                    }
                }

                is AppResult.Failure -> Unit
            }
        }
    }

    private fun nextStep() {
        val state = currentState
        if (state.currentStep == 1) {
            if (state.prompt.isBlank()) {
                launchIO { sendEffect(CreateEffect.ShowToast(R.string.create_enter_description)) }
                return
            }
        } else if (state.currentStep >= TOTAL_STEPS) {
            if (state.companionName.isBlank()) {
                launchIO { sendEffect(CreateEffect.ShowToast(R.string.create_enter_name)) }
                return
            }
            val validPersonality =
                state.selectedPersonality.isNotBlank() && state.selectedPersonality != "None"
            val validHobbies = state.selectedHobbies.filter { it.isNotBlank() && it != "None" }
            if (!validPersonality && validHobbies.isEmpty()) {
                launchIO { sendEffect(CreateEffect.ShowToast(R.string.create_select_personality_hobby)) }
                return
            }
            createCharacterAndChat()
            return
        }
        updateState { copy(currentStep = currentStep + 1) }
    }

    private fun previousStep() {
        if (currentState.currentStep <= 1) return
        updateState { copy(currentStep = currentStep - 1) }
    }

    private fun createCharacterAndChat() {
        launchIO {
            updateState { copy(isLoading = true, error = null) }

            val state = currentState

            val createInput = CreateMyCharacterInput(
                name = state.companionName,
                personality = state.toPersonalityPrompt(),
                gender = when (state.selectedGender) {
                    CreateGender.GIRLS -> "FEMALE"
                    CreateGender.GUYS -> "MALE"
                    CreateGender.TRANS -> "OTHER"
                },
                age = state.selectedAge.toIntOrNull() ?: 20,
                tags = state.selectedHobbies.filter { it.isNotBlank() && it != "None" }.take(3)
            )

            val createResult = createMyCharacterUseCase(createInput)
            if (createResult is AppResult.Failure) {
                updateState { copy(isLoading = false, error = createResult.error) }
                return@launchIO
            }

            val character = (createResult as AppResult.Success).data

            val appearancePrompt =
                "Ethnicity: ${state.selectedEthnicity.name}, Style: ${state.selectedStyle.name}, Hair: ${state.selectedHair.name}"
            val imageInput = GenerateMyCharacterImageInput(
                userCharacterId = character.id,
                prompt = appearancePrompt
            )
            val imageResult = generateMyCharacterImageUseCase(imageInput)
            if (imageResult is AppResult.Failure) {
                updateState { copy(isLoading = false, error = imageResult.error) }
                return@launchIO
            }
            val imageTask = (imageResult as AppResult.Success).data
            if (imageTask.taskId.isBlank()) {
                updateState { copy(isLoading = false) }
                sendEffect(CreateEffect.ShowToast(R.string.image_task_was_not_created))
                return@launchIO
            }
            val imageStatus = pollGeneratedImage(character.id, imageTask.taskId)
            if (imageStatus is AppResult.Failure) {
                updateState { copy(isLoading = false, error = imageStatus.error) }
                return@launchIO
            }
            val completedImage = (imageStatus as AppResult.Success).data
            if (!completedImage.isSuccess) {
                updateState { copy(isLoading = false) }
                val msg = completedImage.message
                if (!msg.isNullOrBlank()) {
                    sendEffect(CreateEffect.ShowToastString(msg))
                } else {
                    sendEffect(CreateEffect.ShowToast(R.string.image_generation_failed))
                }
                return@launchIO
            }

            val publishResult = publishMyCharacterUseCase(character.id)
            if (publishResult is AppResult.Failure) {
                updateState { copy(isLoading = false, error = publishResult.error) }
                return@launchIO
            }

            val conversationInput = CreateConversationInput(
                characterId = character.id
            )
            val conversationResult = createConversationUseCase(conversationInput)
            if (conversationResult is AppResult.Failure) {
                updateState { copy(isLoading = false, error = conversationResult.error) }
                return@launchIO
            }

            val conversation = (conversationResult as AppResult.Success).data

            updateState { copy(isLoading = false) }
            sendEffect(CreateEffect.NavigateToChat(conversation.id))
        }
    }

    private suspend fun pollGeneratedImage(
        userCharacterId: String,
        imageTaskId: String
    ) = repeat(IMAGE_POLL_ATTEMPTS) { attempt ->
        if (attempt > 0) delay(IMAGE_POLL_INTERVAL_MS.milliseconds)
        val result = getGeneratedMyCharacterImageStatusUseCase(userCharacterId, imageTaskId)
        if (result !is AppResult.Success || !result.data.isPending) return result
    }.let {
        getGeneratedMyCharacterImageStatusUseCase(userCharacterId, imageTaskId)
    }

    private fun CreateUiState.toPersonalityPrompt(): String = buildString {
        append(prompt.trim())
        append(' ')
        if (selectedPersonality.isNotBlank() && selectedPersonality != "None") {
            append("Personality: ")
            append(selectedPersonality)
            append(". ")
        }
        if (selectedOccupation.isNotBlank() && selectedOccupation != "None") {
            append("Occupation: ")
            append(selectedOccupation)
            append(". ")
        }
        if (selectedRelationship.isNotBlank() && selectedRelationship != "None") {
            append("Relationship with user: ")
            append(selectedRelationship)
            append(". ")
        }
        val hobbies = selectedHobbies.filter { it.isNotBlank() && it != "None" }
        if (hobbies.isNotEmpty()) {
            append("Hobbies: ")
            append(hobbies.joinToString(", "))
            append(".")
        }
    }.trim().take(PERSONALITY_MAX_LENGTH)

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private companion object {
        const val TOTAL_STEPS = 4
        const val IMAGE_POLL_ATTEMPTS = 20
        const val IMAGE_POLL_INTERVAL_MS = 3_000L
        const val PERSONALITY_MAX_LENGTH = 1_000
    }
}
