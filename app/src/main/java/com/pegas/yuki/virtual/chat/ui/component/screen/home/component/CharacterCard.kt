package com.pegas.yuki.virtual.chat.ui.component.screen.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.character.Character
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterCategorySummary
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color08030F
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color322D41
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD65A98
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD9D9D9
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_15
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_9
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.custom.LoadingAsyncImage

@Composable
internal fun CharacterCard(
    character: Character,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(SdpR_20),
        colors = CardDefaults.cardColors(containerColor = Color08030F),
        border = BorderStroke(SdpR_1, Color322D41),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.76f)
        ) {
            LoadingAsyncImage(
                imageUrl = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .matchParentSize()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.42f to Color.Transparent,
                                0.72f to Color08030F.copy(alpha = 0.45f),
                                1f to Color08030F.copy(alpha = 0.96f)
                            )
                        )
                    )
            )

            if (character.isHot) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = SdpR_12, end = SdpR_12)
                        .background(
                            color = ColorD65A98,
                            shape = RoundedCornerShape(SdpR_9)
                        )
                        .padding(horizontal = SdpR_9, vertical = SdpR_1)
                ) {
                    Text(
                        text = stringResource(id = R.string.character_detail_hot),
                        fontFamily = OutfitBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = SdpR_10.nonScaledSp,
                        color = ColorFDFDFD
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(SdpR_10)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_15.nonScaledSp,
                    color = ColorFDFDFD,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (character.description.isNotBlank()) {
                    Text(
                        text = character.description,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = ManropeRegular,
                        fontWeight = FontWeight.Normal,
                        fontSize = SdpR_12.nonScaledSp,
                        color = ColorD9D9D9,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Character card",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 220
)
@Composable
private fun CharacterCardPreview() {
    Box(
        modifier = Modifier
            .background(Color08030F)
            .padding(SdpR_16)
    ) {
        CharacterCard(
            character = previewCharacter(),
            onClick = {}
        )
    }
}

private fun previewCharacter() = Character(
    id = "character_preview",
    name = "Sofia, 24",
    slug = "sakura",
    image = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400",
    description = "Sofia is an artist who finds inspiration in nature and the stars.",
    task = "AI Girlfriend",
    categoryId = "companion",
    category = CharacterCategorySummary(
        id = "companion",
        name = "Companion",
        slug = "companion",
        sort = 1
    ),
    sort = 1,
    isHot = true,
    likes = 1200,
    ratingStars = 4.8,
    ratingCount = 340,
    createdAt = "2026-07-29T00:00:00Z",
    updatedAt = "2026-07-29T00:00:00Z"
)
