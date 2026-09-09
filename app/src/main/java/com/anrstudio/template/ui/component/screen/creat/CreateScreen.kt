package com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component.CREATE_TOTAL_STEPS
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component.CreateContent
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component.CreateImageOption
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component.PersonalityItem
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component.SelectionBottomSheet

@Composable
fun CreateScreen(
    fromScreen: String? = null,
    onBack: () -> Unit,
    onOpenChat: (String) -> Unit,
    viewModel: CreateViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val defaultName = stringResource(R.string.create_default_name)

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(
            CreateIntent.Initialize(
                defaultPrompt = "",
                defaultName = defaultName
            )
        )
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "CreateScreen",
        fromScreen = fromScreen,
        showLoadingDialog = true,
        shouldShowLoadingDialog = { it.isLoading },
        errorTitleRes = R.string.create_error_title,
        onRetryError = { viewModel.handleIntent(CreateIntent.NextStep) },
        onEffect = { effect ->
            when (effect) {
                is CreateEffect.NavigateToChat -> onOpenChat(effect.conversationId)
                is CreateEffect.ShowToast -> Toast.makeText(
                    context,
                    context.getString(effect.messageRes),
                    Toast.LENGTH_SHORT
                ).show()
                is CreateEffect.ShowToastString -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    ) { state, onIntent ->
        CreateRoute(
            state = state,
            onBack = onBack,
            onIntent = onIntent,
        )
    }
}

@Composable
private fun CreateRoute(
    state: CreateUiState,
    onBack: () -> Unit,
    onIntent: (CreateIntent) -> Unit,
) {
    val title = stringResource(R.string.create_screen_title)
    val nextButtonText = stringResource(R.string.next_step)
    val generateButtonText = stringResource(
        R.string.create_generate_cost,
        state.imageGenCoinCost
    )
    val girlsLabel = stringResource(R.string.create_gender_girls)
    val guysLabel = stringResource(R.string.create_gender_guys)
    val transLabel = stringResource(R.string.create_gender_trans)
    val realisticLabel = stringResource(R.string.create_style_realistic)
    val animeLabel = stringResource(R.string.create_style_anime)
    val whiteLabel = stringResource(R.string.create_ethnicity_white)
    val asianLabel = stringResource(R.string.create_ethnicity_asian)
    val blackLabel = stringResource(R.string.create_ethnicity_black)
    val latinaLabel = stringResource(R.string.create_ethnicity_latina)
    val blondeLabel = stringResource(R.string.create_hair_blonde)
    val hairBlackLabel = stringResource(R.string.create_hair_black)
    val copperLabel = stringResource(R.string.create_hair_copper)
    val blueLabel = stringResource(R.string.create_hair_blue)
    val purpleLabel = stringResource(R.string.create_hair_purple)
    val redLabel = stringResource(R.string.create_hair_red)

    val selectedGender = state.selectedGender.toLabel(
        girlsLabel = girlsLabel,
        guysLabel = guysLabel,
        transLabel = transLabel
    )
    val selectedStyle = state.selectedStyle.toLabel(
        realisticLabel = realisticLabel,
        animeLabel = animeLabel
    )
    val selectedEthnicity = state.selectedEthnicity.toLabel(
        whiteLabel = whiteLabel,
        asianLabel = asianLabel,
        blackLabel = blackLabel,
        latinaLabel = latinaLabel
    )
    val selectedHair = state.selectedHair.toLabel(
        blondeLabel = blondeLabel,
        blackLabel = hairBlackLabel,
        copperLabel = copperLabel,
        blueLabel = blueLabel,
        purpleLabel = purpleLabel,
        redLabel = redLabel
    )
    val genderOptions = listOf(girlsLabel, guysLabel, transLabel)
    val styleOptions = listOf(
        CreateImageOption(
            title = realisticLabel,
            imageRes = when (state.selectedGender) {
                CreateGender.GUYS -> R.drawable.img_guys
                CreateGender.TRANS -> R.drawable.img_trans
                CreateGender.GIRLS -> R.drawable.img_giris
            }
        ),
        CreateImageOption(
            title = animeLabel,
            imageRes = R.drawable.img_anime
        )
    )
    val isGuy = state.selectedGender == CreateGender.GUYS
    val ethnicityOptions = listOf(
        CreateImageOption(
            whiteLabel,
            if (isGuy) R.drawable.img_white_guy else R.drawable.img_white_girl
        ),
        CreateImageOption(
            asianLabel,
            if (isGuy) R.drawable.img_asian_guy else R.drawable.img_asian_girl
        ),
        CreateImageOption(
            blackLabel,
            if (isGuy) R.drawable.img_back_guy else R.drawable.img_back_girl
        ),
        CreateImageOption(
            latinaLabel,
            if (isGuy) R.drawable.img_latine_guy else R.drawable.img_latine_girl
        )
    )
    val ageOptions = listOf("20", "30", "40", "50")
    val skinTones = listOf(
        Color(0xFFFFD7B8),
        Color(0xFFF3BD95),
        Color(0xFFE0A56F),
        Color(0xFFC98555),
        Color(0xFF9F6038),
        Color(0xFF663A1C)
    )
    val hairOptions = listOf(
        CreateImageOption(
            blondeLabel,
            if (isGuy) R.drawable.img_blonde_guy else R.drawable.img_blonde_girl
        ),
        CreateImageOption(
            hairBlackLabel,
            if (isGuy) R.drawable.img_black_guy else R.drawable.img_black_girl
        ),
        CreateImageOption(
            copperLabel,
            if (isGuy) R.drawable.img_copper_guy else R.drawable.img_copper_girl
        ),
        CreateImageOption(
            blueLabel,
            if (isGuy) R.drawable.img_blue_guy else R.drawable.img_blue_girl
        ),
        CreateImageOption(
            purpleLabel,
            if (isGuy) R.drawable.img_purple_guy else R.drawable.img_purple_girl
        ),
        CreateImageOption(redLabel, if (isGuy) R.drawable.img_red_guy else R.drawable.img_red_girl)
    )
    var showPersonalitySheet by remember { mutableStateOf(false) }
    var showOccupationSheet by remember { mutableStateOf(false) }
    var showHobbiesSheet by remember { mutableStateOf(false) }
    var showRelationshipSheet by remember { mutableStateOf(false) }

    val personalityOptions = stringArrayResource(R.array.create_personality_options).toList()
    val occupationOptions = stringArrayResource(R.array.create_occupation_options).toList()
    val hobbiesOptions = stringArrayResource(R.array.create_hobbies_options).toList()
    val relationshipOptions = stringArrayResource(R.array.create_relationships_options).toList()

    val optionNone = stringResource(R.string.option_none)

    val personalityItems = listOf(
        PersonalityItem(
            stringResource(R.string.create_personality),
            state.selectedPersonality.takeIf { it.isNotBlank() } ?: optionNone,
            R.drawable.ic_personality,
            onClick = { showPersonalitySheet = true }
        ),
        PersonalityItem(
            stringResource(R.string.create_occupation),
            state.selectedOccupation.takeIf { it.isNotBlank() } ?: optionNone,
            R.drawable.ic_occupation,
            onClick = { showOccupationSheet = true }
        ),
        PersonalityItem(
            stringResource(R.string.create_hobbies),
            if (state.selectedHobbies.isNotEmpty()) state.selectedHobbies.joinToString(", ") else optionNone,
            R.drawable.ic_hobbies,
            onClick = { showHobbiesSheet = true }
        ),
        PersonalityItem(
            stringResource(R.string.create_relationships),
            state.selectedRelationship.takeIf { it.isNotBlank() } ?: optionNone,
            R.drawable.ic_relationships,
            onClick = { showRelationshipSheet = true }
        )
    )

    CreateContent(
        title = title,
        currentStep = state.currentStep,
        stepLabel = createStepLabel(state.currentStep),
        isShowCoin = state.currentStep == CREATE_TOTAL_STEPS,
        nextButtonText = if (state.currentStep == CREATE_TOTAL_STEPS) generateButtonText else nextButtonText,
        selectedGender = selectedGender,
        selectedStyle = selectedStyle,
        prompt = state.prompt,
        selectedEthnicity = selectedEthnicity,
        selectedAge = state.selectedAge,
        selectedSkinTone = state.selectedSkinTone,
        selectedHair = selectedHair,
        companionName = state.companionName,
        genderOptions = genderOptions,
        styleOptions = styleOptions,
        ethnicityOptions = ethnicityOptions,
        ageOptions = ageOptions,
        skinTones = skinTones,
        hairOptions = hairOptions,
        personalityItems = personalityItems,
        onBack = {
            if (state.currentStep > 1) {
                onIntent(CreateIntent.PreviousStep)
            } else {
                onBack()
            }
        },
        onNext = { onIntent(CreateIntent.NextStep) },
        onGenderSelected = {
            onIntent(
                CreateIntent.GenderSelected(
                    when (it) {
                        girlsLabel -> CreateGender.GIRLS
                        guysLabel -> CreateGender.GUYS
                        else -> CreateGender.TRANS
                    }
                )
            )
        },
        onStyleSelected = {
            onIntent(
                CreateIntent.StyleSelected(
                    when (it) {
                        realisticLabel -> CreateStyle.REALISTIC
                        else -> CreateStyle.ANIME
                    }
                )
            )
        },
        onPromptChanged = { onIntent(CreateIntent.PromptChanged(it)) },
        onEthnicitySelected = {
            onIntent(
                CreateIntent.EthnicitySelected(
                    when (it) {
                        whiteLabel -> CreateEthnicity.WHITE
                        asianLabel -> CreateEthnicity.ASIAN
                        blackLabel -> CreateEthnicity.BLACK
                        else -> CreateEthnicity.LATINA
                    }
                )
            )
        },
        onAgeSelected = { onIntent(CreateIntent.AgeSelected(it)) },
        onSkinToneSelected = { onIntent(CreateIntent.SkinToneSelected(it)) },
        onHairSelected = {
            onIntent(
                CreateIntent.HairSelected(
                    when (it) {
                        blondeLabel -> CreateHairColor.BLONDE
                        hairBlackLabel -> CreateHairColor.BLACK
                        copperLabel -> CreateHairColor.COPPER
                        blueLabel -> CreateHairColor.BLUE
                        purpleLabel -> CreateHairColor.PURPLE
                        else -> CreateHairColor.RED
                    }
                )
            )
        },
        onNameChanged = { onIntent(CreateIntent.NameChanged(it)) },
        onRandomize = {
            val randomNames = listOf(
                "Luna",
                "Bella",
                "Chloe",
                "Mia",
                "Zoe",
                "Lily",
                "Ella",
                "Sophia",
                "Aria",
                "Emma",
                "Ava",
                "Stella",
                "Nova"
            )
            onIntent(CreateIntent.NameChanged(randomNames.random()))
            onIntent(CreateIntent.PersonalitySelected(personalityOptions.filter { it != optionNone }
                .random()))
            onIntent(CreateIntent.OccupationSelected(occupationOptions.filter { it != optionNone }
                .random()))
            onIntent(CreateIntent.HobbiesSelected(hobbiesOptions.filter { it != optionNone }
                .shuffled().take((1..3).random())))
            onIntent(CreateIntent.RelationshipSelected(relationshipOptions.filter { it != optionNone }
                .random()))
        }
    )

    if (showPersonalitySheet) {
        SelectionBottomSheet(
            title = stringResource(R.string.create_personality),
            options = personalityOptions,
            selectedOptions = listOfNotNull(state.selectedPersonality.takeIf { it.isNotBlank() }),
            onDismiss = { showPersonalitySheet = false },
            onOptionSelected = { onIntent(CreateIntent.PersonalitySelected(it)) }
        )
    }

    if (showOccupationSheet) {
        SelectionBottomSheet(
            title = stringResource(R.string.create_occupation),
            options = occupationOptions,
            selectedOptions = listOfNotNull(state.selectedOccupation.takeIf { it.isNotBlank() }),
            onDismiss = { showOccupationSheet = false },
            onOptionSelected = { onIntent(CreateIntent.OccupationSelected(it)) }
        )
    }

    if (showHobbiesSheet) {
        SelectionBottomSheet(
            title = stringResource(R.string.create_hobbies),
            options = hobbiesOptions,
            selectedOptions = state.selectedHobbies,
            isMultiSelect = true,
            onDismiss = { showHobbiesSheet = false },
            onOptionSelected = {},
            onMultiOptionsSelected = { onIntent(CreateIntent.HobbiesSelected(it)) }
        )
    }

    if (showRelationshipSheet) {
        SelectionBottomSheet(
            title = stringResource(R.string.create_relationships),
            options = relationshipOptions,
            selectedOptions = listOfNotNull(state.selectedRelationship.takeIf { it.isNotBlank() }),
            onDismiss = { showRelationshipSheet = false },
            onOptionSelected = { onIntent(CreateIntent.RelationshipSelected(it)) }
        )
    }
}

private fun CreateGender.toLabel(
    girlsLabel: String,
    guysLabel: String,
    transLabel: String
): String = when (this) {
    CreateGender.GIRLS -> girlsLabel
    CreateGender.GUYS -> guysLabel
    CreateGender.TRANS -> transLabel
}

private fun CreateStyle.toLabel(
    realisticLabel: String,
    animeLabel: String
): String = when (this) {
    CreateStyle.REALISTIC -> realisticLabel
    CreateStyle.ANIME -> animeLabel
}

private fun CreateEthnicity.toLabel(
    whiteLabel: String,
    asianLabel: String,
    blackLabel: String,
    latinaLabel: String
): String = when (this) {
    CreateEthnicity.WHITE -> whiteLabel
    CreateEthnicity.ASIAN -> asianLabel
    CreateEthnicity.BLACK -> blackLabel
    CreateEthnicity.LATINA -> latinaLabel
}

private fun CreateHairColor.toLabel(
    blondeLabel: String,
    blackLabel: String,
    copperLabel: String,
    blueLabel: String,
    purpleLabel: String,
    redLabel: String
): String = when (this) {
    CreateHairColor.BLONDE -> blondeLabel
    CreateHairColor.BLACK -> blackLabel
    CreateHairColor.COPPER -> copperLabel
    CreateHairColor.BLUE -> blueLabel
    CreateHairColor.PURPLE -> purpleLabel
    CreateHairColor.RED -> redLabel
}

@Composable
private fun createStepLabel(step: Int): String {
    val titleRes = when (step) {
        1 -> R.string.create_step_general_setup
        2 -> R.string.create_step_appearance_details
        3 -> R.string.create_step_hair_customization
        else -> R.string.create_step_personality_identity
    }
    return stringResource(
        R.string.create_step_label_format,
        step,
        CREATE_TOTAL_STEPS,
        stringResource(titleRes)
    )
}
