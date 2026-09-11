package com.pegas.yuki.virtual.chat.ui.component.screen.creat

import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.usecase.mycharacter.CreateCharacterAndChatInput
import com.pegas.yuki.virtual.chat.domain.usecase.mycharacter.CreateCharacterAndChatResult
import com.pegas.yuki.virtual.chat.domain.usecase.mycharacter.CreateCharacterAndChatUseCase
import com.pegas.yuki.virtual.chat.domain.usecase.mycharacter.GetMyCharacterCreationGuideUseCase
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val getMyCharacterCreationGuideUseCase: GetMyCharacterCreationGuideUseCase,
    private val createCharacterAndChatUseCase: CreateCharacterAndChatUseCase
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
            val createInput = CreateCharacterAndChatInput(
                name = state.companionName,
                personality = state.toPersonalityPrompt(),
                gender = when (state.selectedGender) {
                    CreateGender.GIRLS -> "FEMALE"
                    CreateGender.GUYS -> "MALE"
                    CreateGender.TRANS -> "OTHER"
                },
                age = state.selectedAge.toIntOrNull() ?: 20,
                tags = state.selectedHobbies.filter { it.isNotBlank() && it != "None" }.take(3),
                appearancePrompt = state.toAppearancePrompt()
            )

            when (val result = createCharacterAndChatUseCase(createInput)) {
                is CreateCharacterAndChatResult.Failure -> {
                    updateState { copy(isLoading = false, error = result.error) }
                }

                is CreateCharacterAndChatResult.ImageGenerationFailed -> {
                    updateState { copy(isLoading = false) }
                    val message = result.message
                    if (!message.isNullOrBlank()) {
                        sendEffect(CreateEffect.ShowToastString(message))
                    } else {
                        sendEffect(CreateEffect.ShowToast(R.string.image_generation_failed))
                    }
                }

                is CreateCharacterAndChatResult.Success -> {
                    updateState { copy(isLoading = false) }
                    sendEffect(CreateEffect.NavigateToChat(result.conversationId))
                }
            }
        }
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

    private fun CreateUiState.toAppearancePrompt(): String =
        "Ethnicity: ${selectedEthnicity.name}, Style: ${selectedStyle.name}, Hair: ${selectedHair.name}"

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private companion object {
        const val TOTAL_STEPS = 4
        const val PERSONALITY_MAX_LENGTH = 1_000
    }
}
