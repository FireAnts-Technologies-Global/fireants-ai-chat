package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategorySummary
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color08030F
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_100
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_30
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_36
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_90
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage

@Composable
internal fun CharacterHeaderCover(
    character: Character,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = SdpR_36)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.1f)
        ) {
            LoadingAsyncImage(
                imageUrl = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color08030F.copy(alpha = 0.5f),
                                Color08030F
                            ),
                            startY = 100f
                        )
                    )
            )

            if (character.isHot) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = SdpR_16, bottom = SdpR_8)
                        .clip(RoundedCornerShape(SdpR_100))
                        .background(ColorD65A98)
                        .padding(horizontal = SdpR_12, vertical = SdpR_4),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.character_detail_hot),
                        color = ColorFDFDFD,
                        fontFamily = OutfitBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = SdpR_12.nonScaledSp
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = SdpR_16, y = SdpR_30)
                .size(SdpR_90)
                .clip(CircleShape)
                .border(SdpR_2, ColorE8C3AC, CircleShape)
                .background(Color08030F)
        ) {
            LoadingAsyncImage(
                imageUrl = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun CharacterHeaderCoverPreview() {
    CharacterHeaderCover(
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
    tags = listOf("Empathetic", "Cozy", "Dreamer"),
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
