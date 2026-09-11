package com.pegas.yuki.virtual.chat.ui.component.screen.creat.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppText
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppTextHorizontalGradient
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color663A1C
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color9F6038
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorC98555
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE0A56F
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorF3BD95
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFD7B8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_104
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@Composable
fun GeneralSetupStep(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    selectedStyle: String,
    onStyleSelected: (String) -> Unit,
    prompt: String,
    onPromptChanged: (String) -> Unit,
    onInspireMe: () -> Unit = {},
    genderOptions: List<String>,
    styleOptions: List<CreateImageOption>
) {
    val girlsLabel = stringResource(R.string.create_gender_girls)
    val guysLabel = stringResource(R.string.create_gender_guys)
    val transLabel = stringResource(R.string.create_gender_trans)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_16)
    ) {
        item {
            SectionTitle(stringResource(R.string.create_companion_gender))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                val isFemale = selectedGender == girlsLabel
                val isMale = selectedGender == guysLabel
                val isTrans = selectedGender == transLabel

                IdentityOptionCard(
                    title = girlsLabel,
                    iconRes = if (isFemale) R.drawable.ic_gender_female_selected else R.drawable.ic_gender_female,
                    selected = isFemale,
                    modifier = Modifier.weight(1f),
                    onClick = { onGenderSelected(girlsLabel) }
                )
                IdentityOptionCard(
                    title = guysLabel,
                    iconRes = if (isMale) R.drawable.ic_gender_male_selected else R.drawable.ic_gender_male,
                    selected = isMale,
                    modifier = Modifier.weight(1f),
                    onClick = { onGenderSelected(guysLabel) }
                )
                IdentityOptionCard(
                    title = transLabel,
                    iconRes = if (isTrans) R.drawable.ic_gender_nonbinary_selecterd else R.drawable.ic_gender_nonbinary,
                    selected = isTrans,
                    modifier = Modifier.weight(1f),
                    onClick = { onGenderSelected(transLabel) }
                )
            }
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
            SectionTitle(
                text = stringResource(R.string.create_describe_to_create),
                action = {
                    AppText(
                        text = stringResource(R.string.create_inspire_me),
                        fontFamily = ManropeRegular,
                        fontSize = SdpR_14.nonScaledSp,
                        lineHeight = SdpR_14.nonScaledSp,
                        gradient = AppTextHorizontalGradient,
                        modifier = Modifier
                            .clickable(onClick = onInspireMe)
                            .padding(vertical = SdpR_2, horizontal = SdpR_4)
                    )

                }
            )
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
        verticalArrangement = Arrangement.spacedBy(SdpR_20)
    ) {
        item {
            SectionTitle(stringResource(R.string.create_companion_ethnicity))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(SdpR_10)) {
                items(ethnicityOptions) { option ->
                    SelectableImageCard(
                        option = option,
                        selected = option.title == selectedEthnicity,
                        modifier = Modifier.width(SdpR_104),
                        imageAspectRatio = 0.95f,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                skinTones.forEachIndexed { index, color ->
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
        verticalArrangement = Arrangement.spacedBy(SdpR_12)
    ) {
        item { SectionTitle(stringResource(R.string.create_hair_color)) }
        items(hairOptions.chunked(2)) { rowOptions ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SdpR_12)
            ) {
                rowOptions.forEach { option ->
                    SelectableImageCard(
                        option = option,
                        selected = option.title == selectedHair,
                        modifier = Modifier.weight(1f),
                        imageAspectRatio = 1.35f,
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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_16)
    ) {
        item {
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
        }

        item {
            SectionTitle(stringResource(R.string.create_personality_social_background))
        }

        items(personalityItems.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SdpR_12)
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
