package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.character.CharacterProgress
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color271E38
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color6B5E80
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
internal fun IntimacyProgressCard(
    progress: CharacterProgress?,
    modifier: Modifier = Modifier
) {
    val level = progress?.level ?: 1
    val xp = progress?.xp ?: 0
    val xpToNext = progress?.xpToNextLevel ?: 10
    val xpPerChat = progress?.xpPerChat ?: 100
    val progressFraction = (xp.toFloat() / (xp + xpToNext).coerceAtLeast(1)).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SdpR_12)
            .clip(RoundedCornerShape(SdpR_16))
            .background(Color150F25)
            .border(BorderStroke(SdpR_1, Color322D41), RoundedCornerShape(SdpR_16))
            .padding(SdpR_12)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(SdpR_12)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.character_detail_intimacy_title),
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = ColorFDFDFD
                )

                val levelText = stringResource(R.string.character_detail_level_format, level)
                val title = progress?.relationshipTitle ?: "Stranger"
                val suffixText = " • $title"
                Text(
                    text = "$levelText$suffixText",
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = ColorE8C3AC
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SdpR_8)
                    .clip(CircleShape)
                    .background(Color271E38)
            ) {
                if (progressFraction > 0f) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressFraction)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(
                                ColorE8C3AC
                            )
                    )
                }
            }

            Text(
                text = stringResource(R.string.character_detail_intimacy_hint, xpPerChat),
                fontFamily = ManropeRegular,
                fontSize = SdpR_12.nonScaledSp,
                color = Color6B5E80
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun IntimacyProgressCardPreview() {
    IntimacyProgressCard(
        progress = CharacterProgress(
            characterId = "1",
            xp = 40,
            level = 12,
            maxLevel = 50,
            xpPerChat = 100,
            xpPerLevel = 100,
            xpToNextLevel = 60,
            leveledUp = false
        )
    )
}
