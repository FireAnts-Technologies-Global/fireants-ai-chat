package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.Character
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterCategorySummary
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage

@Composable
internal fun CharacterHeaderCover(
    character: Character,
    modifier: Modifier = Modifier
) {
    val displayName = if (character.age != null) {
        "${character.name}, ${character.age}"
    } else {
        character.name
    }

    val subtitle = listOfNotNull(
        character.task.takeIf { it.isNotBlank() },
        character.category?.name.takeIf { !it.isNullOrBlank() }
    ).joinToString(" • ")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.92f)
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
                        colorStops = arrayOf(
                            0.0f to Color.Transparent,
                            0.50f to Color.Transparent,
                            0.80f to ColorFFFFFF.copy(alpha = 0.65f),
                            1.0f to ColorFFFFFF
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(horizontal = SdpR_16, vertical = SdpR_8),
            verticalArrangement = Arrangement.spacedBy(SdpR_4)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                Text(
                    text = displayName,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_27.nonScaledSp,
                    color = Color000000
                )

                Box(
                    modifier = Modifier
                        .size(SdpR_20),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check_detail),
                        contentDescription = "Verified",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(SdpR_20)
                    )
                }
            }

            if (subtitle.isNotBlank()) {
                Text(
                    text = subtitle,
                    fontFamily = ManropeRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = SdpR_13.nonScaledSp,
                    color = Color6B5E80
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CharacterHeaderCoverPreview() {
    CharacterHeaderCover(
        character = Character(
            id = "1",
            name = "Luna",
            slug = "luna",
            image = null,
            description = "Dreamer",
            task = "Dreamy soul",
            age = 21,
            category = CharacterCategorySummary("1", "Poet", "poet", 1)
        )
    )
}
