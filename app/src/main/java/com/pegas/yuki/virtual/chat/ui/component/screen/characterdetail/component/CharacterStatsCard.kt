package com.pegas.yuki.virtual.chat.ui.component.screen.characterdetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.character.Character
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color171044
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color6F6898
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorED4DA4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFB03A
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_11
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_17
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_22
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import java.util.Locale

@Composable
internal fun CharacterStatsCard(
    character: Character,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SdpR_16)
            .shadow(
                elevation = SdpR_8,
                shape = RoundedCornerShape(SdpR_24),
                clip = false
            )
            .clip(RoundedCornerShape(SdpR_24))
            .background(ColorFFFFFF)
            .padding(
                horizontal = SdpR_12,
                vertical = SdpR_14
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chat),
                contentDescription = null,
                tint = ColorED4DA4,
                modifier = Modifier.size(SdpR_22)
            )
            Spacer(modifier = Modifier.width(SdpR_12))
            Column {
                Text(
                    text = formatLikes(character.likes),
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_17.nonScaledSp,
                    color = Color171044
                )
                Text(
                    text = stringResource(R.string.character_detail_chats),
                    fontFamily = ManropeRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = SdpR_11.nonScaledSp,
                    color = Color6F6898
                )
            }
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chat_favorite_fill),
                contentDescription = null,
                tint = ColorFFB03A,
                modifier = Modifier.size(SdpR_22)
            )
            Spacer(modifier = Modifier.width(SdpR_12))
            Column {
                val rating = String.format(Locale.getDefault(), "%.1f", character.ratingStars ?: 4.9)
                Text(
                    text = rating,
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_17.nonScaledSp,
                    color = Color171044
                )
                Text(
                    text = stringResource(R.string.character_detail_rating),
                    fontFamily = ManropeRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = SdpR_11.nonScaledSp,
                    color = Color6F6898
                )
            }
        }

        Row(
            modifier = Modifier.weight(1.1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_popular),
                contentDescription = null,
                modifier = Modifier.size(SdpR_22)
            )
            Spacer(modifier = Modifier.width(SdpR_12))
            Column {
                val rank = character.sort.takeIf { it > 0 } ?: 1
                Text(
                    text = stringResource(R.string.character_detail_rank_format, rank),
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_17.nonScaledSp,
                    color = Color171044
                )
                Text(
                    text = stringResource(R.string.character_detail_most_popular),
                    fontFamily = ManropeRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = SdpR_11.nonScaledSp,
                    color = Color6F6898
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun CharacterStatsCardPreview() {
    CharacterStatsCard(
        character = Character(
            id = "1",
            name = "Luna",
            slug = "luna",
            image = null,
            description = "Dreamer",
            task = "Poet",
            likes = 12400,
            ratingStars = 4.9,
            sort = 1
        )
    )
}
