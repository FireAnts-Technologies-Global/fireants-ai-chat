package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategorySummary
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CharacterInfoSection(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_16)
    ) {
        if (character.description.isNotBlank()) {
            Column(verticalArrangement = Arrangement.spacedBy(SdpR_8)) {
                Text(
                    text = stringResource(R.string.character_detail_about, character.name),
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_18.nonScaledSp,
                    color = Color110640
                )

                Text(
                    text = character.description,
                    fontFamily = ManropeRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = SdpR_14.nonScaledSp,
                    lineHeight = SdpR_20.nonScaledSp,
                    color = Color6B5E80
                )
            }
        }

        if (character.tags.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(SdpR_10)) {
                Text(
                    text = stringResource(R.string.character_detail_personality_traits),
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_18.nonScaledSp,
                    color = Color110640
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(SdpR_8),
                    verticalArrangement = Arrangement.spacedBy(SdpR_8)
                ) {
                    character.tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(SdpR_100))
                                .background(ColorE9DDF2.copy(alpha = 0.5f))
                                .border(BorderStroke(SdpR_1, ColorE9DDF2), RoundedCornerShape(SdpR_100))
                                .padding(horizontal = SdpR_14, vertical = SdpR_8)
                        ) {
                            Text(
                                text = tag.replaceFirstChar { it.uppercase() },
                                color = Color110640,
                                fontFamily = ManropeSemiBold,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = SdpR_13.nonScaledSp
                            )
                        }
                    }
                }
            }
        }
    }
}

fun formatLikes(likes: Int): String {
    return if (likes >= 1000) {
        String.format("%.1fk", likes / 1000.0)
    } else {
        likes.toString()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CharacterInfoSectionPreview() {
    CharacterInfoSection(
        character = Character(
            id = "1",
            name = "Luna",
            slug = "luna",
            image = null,
            description = "A dreamy soul who loves stargazing, poetry, and conversations that last past midnight.",
            task = "Soulmate",
            tags = listOf("Empathetic", "Cozy", "Dreamer", "Sweet & Caring"),
            age = 21,
            gender = "FEMALE",
            categoryId = "cat1",
            category = CharacterCategorySummary("cat1", "Anime", "anime", 1),
            sort = 1,
            isHot = true,
            likes = 12400,
            ratingStars = 4.9,
            ratingCount = 120,
            createdAt = "",
            updatedAt = ""
        )
    )
}
