package com.anrstudio.template.ui.component.creat.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.anrstudio.template.ads.AdRemoteConfig
import com.anrstudio.template.ads.banner_all
import com.anrstudio.template.ui.bases.compose.component.BannerAdView
import com.anrstudio.template.ui.bases.compose.theme.ColorE8C3AC
import com.anrstudio.template.ui.bases.compose.theme.OutfitRegular
import com.anrstudio.template.ui.bases.compose.theme.SdpR_10
import com.anrstudio.template.ui.bases.compose.theme.SdpR_13
import com.anrstudio.template.ui.bases.compose.theme.SdpR_16
import com.anrstudio.template.ui.bases.compose.theme.SdpR_24
import com.anrstudio.template.ui.bases.compose.theme.SdpR_28
import com.anrstudio.template.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.CommonTopBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateContent(
    title: String,
    currentStep: Int,
    stepLabel: String,
    isShowCoin: Boolean,
    nextButtonText: String,
    selectedGender: String,
    selectedStyle: String,
    prompt: String,
    selectedEthnicity: String,
    selectedAge: String,
    selectedSkinTone: Int,
    selectedHair: String,
    companionName: String,
    genderOptions: List<String>,
    styleOptions: List<CreateImageOption>,
    ethnicityOptions: List<CreateImageOption>,
    ageOptions: List<String>,
    skinTones: List<Color>,
    hairOptions: List<CreateImageOption>,
    personalityItems: List<PersonalityItem>,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onGenderSelected: (String) -> Unit,
    onStyleSelected: (String) -> Unit,
    onPromptChanged: (String) -> Unit,
    onEthnicitySelected: (String) -> Unit,
    onAgeSelected: (String) -> Unit,
    onSkinToneSelected: (Int) -> Unit,
    onHairSelected: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onRandomize: () -> Unit = {},
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets.ime,
        topBar = {
            CommonTopBar(
                title = title,
                showActions = false,
                onBack = onBack
            )
        },
        bottomBar = {
            BannerAdView(
                adUnitId = AdRemoteConfig.banner_all.id,
                isEnabled = AdRemoteConfig.banner_all.isEnable,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .imePadding()
                .padding(horizontal = SdpR_16)
        ) {
            CreateStepProgressView(
                currentStep = currentStep,
                totalSteps = CREATE_TOTAL_STEPS,
                height = SdpR_28,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SdpR_24)
            )

            Text(
                text = stepLabel,
                fontFamily = OutfitRegular,
                fontSize = SdpR_13.nonScaledSp,
                color = ColorE8C3AC
            )

            Spacer(modifier = Modifier.height(SdpR_10))

            Box(modifier = Modifier.weight(1f)) {
                when (currentStep) {
                    1 -> GeneralSetupStep(
                        selectedGender = selectedGender,
                        onGenderSelected = onGenderSelected,
                        selectedStyle = selectedStyle,
                        onStyleSelected = onStyleSelected,
                        prompt = prompt,
                        onPromptChanged = onPromptChanged,
                        genderOptions = genderOptions,
                        styleOptions = styleOptions
                    )

                    2 -> AppearanceDetailsStep(
                        selectedEthnicity = selectedEthnicity,
                        onEthnicitySelected = onEthnicitySelected,
                        selectedAge = selectedAge,
                        onAgeSelected = onAgeSelected,
                        selectedSkinTone = selectedSkinTone,
                        onSkinToneSelected = onSkinToneSelected,
                        ethnicityOptions = ethnicityOptions,
                        ageOptions = ageOptions,
                        skinTones = skinTones
                    )

                    3 -> HairCustomizationStep(
                        selectedHair = selectedHair,
                        onHairSelected = onHairSelected,
                        hairOptions = hairOptions
                    )

                    4 -> PersonalityIdentityStep(
                        companionName = companionName,
                        onNameChanged = onNameChanged,
                        personalityItems = personalityItems,
                        onRandomize = onRandomize
                    )
                }
            }
            CreateBottomAction(
                isShowCoin = isShowCoin,
                text = nextButtonText,
                onClick = onNext
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 390, heightDp = 844)
@Composable
private fun CreateContentPreview() {
    CreateContent(
        title = "Create Character",
        currentStep = 2,
        stepLabel = "STEP 1 OF 4: GENERAL SETUP",
        isShowCoin = false,
        nextButtonText = "Next Step",
        selectedGender = "Girls",
        selectedStyle = "Realistic",
        prompt = "A sweet, comforting companion who loves midnight star-gazing, poetry, and brewing warm tea",
        selectedEthnicity = "White",
        selectedAge = "20",
        selectedSkinTone = 0,
        selectedHair = "Blonde",
        companionName = "Alex Thorne",
        genderOptions = listOf("Girls", "Guys", "Trans"),
        styleOptions = listOf(
            CreateImageOption("Realistic", R.drawable.img_empty),
            CreateImageOption("Anime", R.drawable.img_empty)
        ),
        ethnicityOptions = listOf(
            CreateImageOption("White", R.drawable.img_empty),
            CreateImageOption("Asian", R.drawable.img_empty)
        ),
        ageOptions = listOf("20", "30", "40", "50"),
        skinTones = listOf(
            Color(0xFFFFD7B8),
            Color(0xFFF3BD95),
            Color(0xFFE0A56F),
            Color(0xFFC98555),
            Color(0xFF9F6038),
            Color(0xFF663A1C)
        ),
        hairOptions = listOf(
            CreateImageOption("Blonde", R.drawable.img_empty),
            CreateImageOption("Black", R.drawable.img_empty)
        ),
        personalityItems = listOf(
            PersonalityItem(
                "Personality",
                "Spiritual",
                R.drawable.img_empty
            ),
            PersonalityItem(
                "Occupation",
                "Billionaire CEO",
                R.drawable.img_empty
            ),
            PersonalityItem(
                "Hobbies",
                "Music",
                R.drawable.img_empty
            ),
            PersonalityItem(
                "Relationships",
                "Stranger",
                R.drawable.img_empty
            )
        ),
        onBack = {},
        onNext = {},
        onGenderSelected = {},
        onStyleSelected = {},
        onPromptChanged = {},
        onEthnicitySelected = {},
        onAgeSelected = {},
        onSkinToneSelected = {},
        onHairSelected = {},
        onNameChanged = {},
    )
}
