package com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat

import androidx.annotation.StringRes
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseUiState

enum class CreateGender {
    GIRLS,
    GUYS,
    TRANS
}

enum class CreateStyle {
    REALISTIC,
    ANIME
}

enum class CreateEthnicity {
    EAST_ASIAN,
    SOUTH_ASIAN,
    BLACK,
    WHITE,
    ASIAN,
    LATINA
}

enum class CreateHairColor {
    BLONDE,
    BLACK,
    COPPER,
    BLUE,
    PURPLE,
    RED
}

data class CreateUiState(
    override val isLoading: Boolean = false,
    override val error: PublicError? = null,
    val hasInitialized: Boolean = false,
    val currentStep: Int = 1,
    val selectedGender: CreateGender = CreateGender.GIRLS,
    val selectedStyle: CreateStyle = CreateStyle.REALISTIC,
    val prompt: String = "",
    val selectedEthnicity: CreateEthnicity = CreateEthnicity.EAST_ASIAN,
    val selectedAge: String = "20",
    val selectedSkinTone: Int = 0,
    val selectedHair: CreateHairColor = CreateHairColor.BLONDE,
    val companionName: String = "",
    val selectedPersonality: String = "Spiritual",
    val selectedOccupation: String = "Billionaire CEO",
    val selectedHobbies: List<String> = listOf("Music"),
    val selectedRelationship: String = "Stranger",
    val imageGenCoinCost: Int = 0
) : BaseUiState

sealed interface CreateIntent {
    data class Initialize(
        val defaultPrompt: String,
        val defaultName: String
    ) : CreateIntent

    data object NextStep : CreateIntent
    data object PreviousStep : CreateIntent
    data class GenderSelected(val gender: CreateGender) : CreateIntent
    data class StyleSelected(val style: CreateStyle) : CreateIntent
    data class PromptChanged(val value: String) : CreateIntent
    data class EthnicitySelected(val ethnicity: CreateEthnicity) : CreateIntent
    data class AgeSelected(val value: String) : CreateIntent
    data class SkinToneSelected(val index: Int) : CreateIntent
    data class HairSelected(val hairColor: CreateHairColor) : CreateIntent
    data class NameChanged(val value: String) : CreateIntent

    data class PersonalitySelected(val value: String) : CreateIntent
    data class OccupationSelected(val value: String) : CreateIntent
    data class HobbiesSelected(val values: List<String>) : CreateIntent
    data class RelationshipSelected(val value: String) : CreateIntent

}

sealed interface CreateEffect {
    data class NavigateToChat(val conversationId: String) : CreateEffect
    data class ShowToast(@StringRes val messageRes: Int) : CreateEffect
    data class ShowToastString(val message: String) : CreateEffect
}
