package com.pegas.yuki.virtual.chat.ui.component.screen.characterdetail.component

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.character.CharacterProgress
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color110640
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color6B5E80
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorDC60FF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE9DDF2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorED4DA4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFF41BC
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFF8040
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_100
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

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
            .padding(horizontal = SdpR_16)
            .shadow(
                elevation = SdpR_2,
                shape = RoundedCornerShape(SdpR_20),
                clip = false
            )
            .clip(RoundedCornerShape(SdpR_20))
            .background(ColorFFFFFF)
            .border(BorderStroke(SdpR_1, ColorE9DDF2), RoundedCornerShape(SdpR_20))
            .padding(SdpR_16)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(SdpR_12)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.character_detail_intimacy_title),
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_16.nonScaledSp,
                    color = Color110640
                )

                val levelText = stringResource(R.string.character_detail_level_format, level)
                val title = progress?.relationshipTitle ?: "Stranger"
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(SdpR_100))
                        .background(ColorE9DDF2.copy(alpha = 0.5f))
                        .border(BorderStroke(SdpR_1, ColorE9DDF2), RoundedCornerShape(SdpR_100))
                        .padding(horizontal = SdpR_10, vertical = SdpR_4)
                ) {
                    Text(
                        text = "$levelText • $title",
                        fontFamily = ManropeBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = SdpR_12.nonScaledSp,
                        color = ColorED4DA4
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SdpR_8)
                    .clip(CircleShape)
                    .background(ColorE9DDF2.copy(alpha = 0.6f))
            ) {
                if (progressFraction > 0f) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressFraction)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        ColorDC60FF,
                                        ColorFF41BC,
                                        ColorFF8040
                                    )
                                )
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
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
