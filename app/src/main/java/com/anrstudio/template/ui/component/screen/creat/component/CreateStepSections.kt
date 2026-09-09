package com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

@Composable
fun GeneralSetupStep(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    selectedStyle: String,
    onStyleSelected: (String) -> Unit,
    prompt: String,
    onPromptChanged: (String) -> Unit,
    genderOptions: List<String>,
    styleOptions: List<CreateImageOption>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_12)
    ) {
        item {
            SectionTitle(stringResource(R.string.create_companion_gender))
            SegmentedOptions(
                options = genderOptions,
                selected = selectedGender,
                onSelected = onGenderSelected
            )
        }

        item {
            SectionTitle(stringResource(R.string.create_visual_style))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SdpR_10)
            ) {
                styleOptions.forEach { option ->
                    SelectableImageCard(
                        option = option,
                        selected = option.title == selectedStyle,
                        modifier = Modifier.weight(1f),
                        onClick = { onStyleSelected(option.title) }
                    )
                }
            }
        }

        item {
            SectionTitle(stringResource(R.string.create_describe_to_create))
            PromptInput(
                value = prompt,
                onValueChange = onPromptChanged
            )
        }
    }
}

@Composable
fun AppearanceDetailsStep(
    selectedEthnicity: String,
    onEthnicitySelected: (String) -> Unit,
    selectedAge: String,
    onAgeSelected: (String) -> Unit,
    selectedSkinTone: Int,
    onSkinToneSelected: (Int) -> Unit,
    ethnicityOptions: List<CreateImageOption>,
    ageOptions: List<String>,
    skinTones: List<Color>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_12)
    ) {
        item {
            SectionTitle(stringResource(R.string.create_companion_ethnicity))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(SdpR_10)) {
                items(ethnicityOptions) { option ->
                    SelectableImageCard(
                        option = option,
                        selected = option.title == selectedEthnicity,
                        modifier = Modifier.width(SdpR_80),
                        imageAspectRatio = 0.88f,
                        onClick = { onEthnicitySelected(option.title) }
                    )
                }
            }
        }

        item {
            SectionTitle(stringResource(R.string.create_age))
            SegmentedOptions(
                options = ageOptions,
                selected = selectedAge,
                onSelected = onAgeSelected
            )
        }

        item {
            SectionTitle(stringResource(R.string.create_skin_tone))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(SdpR_152),
                horizontalArrangement = Arrangement.spacedBy(SdpR_10),
                verticalArrangement = Arrangement.spacedBy(SdpR_10),
                userScrollEnabled = false
            ) {
                items(skinTones) { color ->
                    val index = skinTones.indexOf(color)
                    SkinToneSwatch(
                        color = color,
                        selected = selectedSkinTone == index,
                        onClick = { onSkinToneSelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun HairCustomizationStep(
    selectedHair: String,
    onHairSelected: (String) -> Unit,
    hairOptions: List<CreateImageOption>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_10)
    ) {
        item { SectionTitle(stringResource(R.string.create_hair_color)) }
        items(hairOptions.chunked(2)) { rowOptions ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SdpR_10)
            ) {
                rowOptions.forEach { option ->
                    SelectableImageCard(
                        option = option,
                        selected = option.title == selectedHair,
                        modifier = Modifier.weight(1f),
                        imageAspectRatio = 1.42f,
                        onClick = { onHairSelected(option.title) }
                    )
                }
                if (rowOptions.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun PersonalityIdentityStep(
    companionName: String,
    onNameChanged: (String) -> Unit,
    personalityItems: List<PersonalityItem>,
    onRandomize: () -> Unit = {}
) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SectionTitle(stringResource(R.string.create_companion_name))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SdpR_8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleLineInput(
                value = companionName,
                onValueChange = onNameChanged,
                modifier = Modifier.weight(1f)
            )
            RandomNameButton(onClick = onRandomize)
        }

        Spacer(modifier = Modifier.height(SdpR_14))
        SectionTitle(stringResource(R.string.create_personality_social_background))

        personalityItems.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SdpR_8),
                horizontalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                rowItems.forEach { item ->
                    PersonalityCard(
                        item = item,
                        modifier = Modifier.weight(1f),
                        onClick = item.onClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 390, heightDp = 844)
@Composable
private fun GeneralSetupStepPreview() {
    GeneralSetupStep(
        selectedGender = "Girls",
        onGenderSelected = {},
        selectedStyle = "Realistic",
        onStyleSelected = {},
        prompt = "A sweet, comforting companion who loves midnight star-gazing, poetry, and brewing warm tea",
        onPromptChanged = {},
        genderOptions = listOf("Girls", "Guys", "Trans"),
        styleOptions = listOf(
            CreateImageOption("Realistic", R.drawable.img_empty),
            CreateImageOption("Anime", R.drawable.img_empty)
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 390, heightDp = 844)
@Composable
private fun AppearanceDetailsStepPreview() {
    AppearanceDetailsStep(
        selectedEthnicity = "White",
        onEthnicitySelected = {},
        selectedAge = "20",
        onAgeSelected = {},
        selectedSkinTone = 0,
        onSkinToneSelected = {},
        ethnicityOptions = listOf(
            CreateImageOption("White", R.drawable.img_empty),
            CreateImageOption("Asian", R.drawable.img_empty),
            CreateImageOption("Black", R.drawable.img_empty)
        ),
        ageOptions = listOf("20", "30", "40", "50"),
        skinTones = listOf(
            ColorFFD7B8,
            ColorF3BD95,
            ColorE0A56F,
            ColorC98555,
            Color9F6038,
            Color663A1C
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 390, heightDp = 844)
@Composable
private fun HairCustomizationStepPreview() {
    HairCustomizationStep(
        selectedHair = "Blonde",
        onHairSelected = {},
        hairOptions = listOf(
            CreateImageOption("Blonde", R.drawable.img_empty),
            CreateImageOption("Black", R.drawable.img_empty),
            CreateImageOption("Copper", R.drawable.img_empty),
            CreateImageOption("Blue", R.drawable.img_empty)
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF090514, widthDp = 390, heightDp = 844)
@Composable
private fun PersonalityIdentityStepPreview() {
    PersonalityIdentityStep(
        companionName = "Alex Thorne",
        onNameChanged = {},
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
        )
    )
}
