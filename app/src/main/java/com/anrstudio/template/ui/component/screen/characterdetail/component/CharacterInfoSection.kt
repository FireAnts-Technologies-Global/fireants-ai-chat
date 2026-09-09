package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategorySummary
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeSemiBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_15
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CharacterInfoSection(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SdpR_16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val displayName = if (character.age != null) {
                "${character.name}, ${character.age}"
            } else {
                character.name
            }

            Text(
                text = displayName,
                fontFamily = OutfitExtraBold,
                fontWeight = FontWeight.Bold,
                fontSize = SdpR_24.nonScaledSp,
                color = ColorFDFDFD
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color150F25)
                    .border(BorderStroke(SdpR_1, Color322D41), CircleShape)
                    .padding(horizontal = SdpR_12, vertical = SdpR_6),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(
                            R.drawable.ic_favorite
                        ),
                        contentDescription = "Likes",
                        tint = ColorD65A98,
                        modifier = Modifier.size(SdpR_14)
                    )
                    Spacer(modifier = Modifier.width(SdpR_4))
                    Text(
                        text = formatLikes(character.likes),
                        color = ColorFDFDFD,
                        fontFamily = OutfitBold,
                        fontSize = SdpR_13.nonScaledSp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(SdpR_2))

        Row(verticalAlignment = Alignment.CenterVertically) {
            val stars = (character.ratingStars ?: 4.5).toFloat()
            val fullStars = stars.toInt()
            repeat(5) { index ->
                Icon(
                    painter = painterResource(if (index < fullStars) R.drawable.ic_chat_favorite_fill else R.drawable.ic_chat_favorite),
                    contentDescription = null,
                    tint = ColorE8C3AC,
                    modifier = Modifier
                        .size(SdpR_14)
                        .padding(end = SdpR_2)
                )
            }
            Spacer(modifier = Modifier.width(SdpR_4))
            Text(
                text = "(${character.ratingStars ?: 4.9})",
                color = ColorAFA5C3,
                fontFamily = ManropeRegular,
                fontSize = SdpR_12.nonScaledSp
            )
        }
        Spacer(modifier = Modifier.height(SdpR_15))

        if (character.tags.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(SdpR_8),
                verticalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                character.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color150F25)
                            .border(BorderStroke(SdpR_1, ColorE8C3AC), CircleShape)
                            .padding(horizontal = SdpR_12, vertical = SdpR_6)
                    ) {
                        Text(
                            text = tag.replaceFirstChar { it.uppercase() },
                            color = ColorE8C3AC,
                            fontFamily = ManropeSemiBold,
                            fontSize = SdpR_12.nonScaledSp
                        )
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

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun CharacterInfoSectionPreview() {
    CharacterInfoSection(
        character = previewCharacter()
    )
}

private fun previewCharacter() = Character(
    id = "1",
    name = "Luna",
    slug = "luna",
    image = null,
    description = "Dreamer",
    task = "Soulmate",
    tags = listOf("Empathetic", "Cozy", "Dreamer", "Sweet & Caring"),
    age = 21,
    gender = "FEMALE",
    categoryId = "cat1",
    category = CharacterCategorySummary("cat1", "Anime", "anime", 1),
    sort = 1,
    isHot = true,
    likes = 14200,
    ratingStars = 4.9,
    ratingCount = 120,
    createdAt = "",
    updatedAt = ""
)
