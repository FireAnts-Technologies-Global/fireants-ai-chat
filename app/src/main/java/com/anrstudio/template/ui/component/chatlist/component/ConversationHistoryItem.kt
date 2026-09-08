package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatlist.component

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.anrstudio.template.domain.model.conversation.ConversationCharacterSummary
import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.ui.bases.compose.component.LoadingAsyncImage
import com.anrstudio.template.ui.bases.compose.theme.Color08030F
import com.anrstudio.template.ui.bases.compose.theme.Color6B5E80
import com.anrstudio.template.ui.bases.compose.theme.ColorAFA5C3
import com.anrstudio.template.ui.bases.compose.theme.ColorFDFDFD
import com.anrstudio.template.ui.bases.compose.theme.ManropeMedium
import com.anrstudio.template.ui.bases.compose.theme.ManropeRegular
import com.anrstudio.template.ui.bases.compose.theme.OutfitBold
import com.anrstudio.template.ui.bases.compose.theme.SdpR_1
import com.anrstudio.template.ui.bases.compose.theme.SdpR_10
import com.anrstudio.template.ui.bases.compose.theme.SdpR_11
import com.anrstudio.template.ui.bases.compose.theme.SdpR_12
import com.anrstudio.template.ui.bases.compose.theme.SdpR_13
import com.anrstudio.template.ui.bases.compose.theme.SdpR_16
import com.anrstudio.template.ui.bases.compose.theme.SdpR_2
import com.anrstudio.template.ui.bases.compose.theme.SdpR_4
import com.anrstudio.template.ui.bases.compose.theme.SdpR_6
import com.anrstudio.template.ui.bases.compose.theme.SdpR_64
import com.anrstudio.template.ui.bases.compose.theme.appVerticalGradientBackground
import com.anrstudio.template.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
internal fun ConversationHistoryItem(
    conversation: ConversationSummary,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val relativeTime = remember(
        conversation.lastMessageAt,
        conversation.updatedAt,
        conversation.createdAt
    ) {
        formatRelativeConversationTime(
            conversation.lastMessageAt
                ?: conversation.updatedAt
                    .ifBlank { conversation.createdAt }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.spacedBy(SdpR_12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LoadingAsyncImage(
            imageUrl = conversation.character?.image,
            contentDescription = conversation.character?.name,
            modifier = Modifier
                .size(SdpR_64)
                .clip(MaterialTheme.shapes.large)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(SdpR_1)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SdpR_12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SdpR_6)
                ) {
                    val characterName = conversation.character?.name ?: conversation.id
                    val titleText = if (conversation.character?.age != null) {
                        "$characterName, ${conversation.character.age}"
                    } else {
                        characterName
                    }
                    Text(
                        text = titleText,
                        modifier = Modifier.weight(1f, fill = false),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontFamily = OutfitBold,
                        fontSize = SdpR_16.nonScaledSp,
                        color = ColorFDFDFD,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (conversation.backgroundId.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF8A56EC).copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(SdpR_4)
                                )
                                .padding(horizontal = SdpR_6, vertical = SdpR_2)
                        ) {
                            Text(
                                text = stringResource(R.string.chat_list_my_ai),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFC0A3FF),
                                fontSize = SdpR_10.nonScaledSp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                relativeTime?.let {
                    Text(
                        text = it,
                        fontFamily = ManropeMedium,
                        fontWeight = FontWeight.Normal,
                        fontSize = SdpR_11.nonScaledSp,
                        color = Color6B5E80,
                        maxLines = 1
                    )
                }
            }

            conversation.lastMessagePreview?.takeIf { it.isNotBlank() }?.let { lastMessagePreview ->
                Text(
                    text = lastMessagePreview,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_13.nonScaledSp,
                    color = ColorAFA5C3,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun formatRelativeConversationTime(value: String): String? {
    if (value.isBlank()) return null

    val normalizedValue = value.replace(
        regex = Regex("""(\.\d{3})\d+"""),
        replacement = "$1"
    )
    val formats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" to false,
        "yyyy-MM-dd'T'HH:mm:ssXXX" to false,
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" to true,
        "yyyy-MM-dd'T'HH:mm:ss'Z'" to true,
        "yyyy-MM-dd HH:mm:ss" to false,
        "yyyy-MM-dd HH:mm" to false
    )

    val timestamp = formats.firstNotNullOfOrNull { (pattern, useUtc) ->
        runCatching {
            SimpleDateFormat(pattern, Locale.US).apply {
                isLenient = false
                if (useUtc) timeZone = TimeZone.getTimeZone("UTC")
            }.parse(normalizedValue)?.time
        }.getOrNull()
    } ?: return null

    return DateUtils.getRelativeTimeSpanString(
        timestamp,
        System.currentTimeMillis(),
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F, widthDp = 430)
@Composable
private fun ConversationHistoryItemWithImagePreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color08030F)
            .appVerticalGradientBackground()
            .padding(SdpR_16)
    ) {
        ConversationHistoryItem(
            conversation = ConversationSummary(
                id = "conversation_1",
                userId = "user_1",
                characterId = "character_1",
                title = "Late night talk",
                lastMessagePreview = "Alo 1234",
                lastMessageAt = "2026-07-28 22:15",
                createdAt = "2026-07-28 21:00",
                updatedAt = "2026-07-28 22:15",
                character = ConversationCharacterSummary(
                    id = "character_1",
                    slug = "luna",
                    name = "Luna",
                    image = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400",
                    description = "Warm and playful",
                    task = "Soulmate",
                )
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F, widthDp = 430)
@Composable
private fun ConversationHistoryItemWithoutImagePreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color08030F)
            .appVerticalGradientBackground()
            .padding(SdpR_16)
    ) {
        ConversationHistoryItem(
            conversation = ConversationSummary(
                id = "conversation_2",
                userId = "user_1",
                characterId = "character_2",
                title = "Morning check-in",
                lastMessagePreview = "Alo 123",
                lastMessageAt = "2026-07-28 08:42",
                createdAt = "2026-07-28 08:00",
                updatedAt = "2026-07-28 08:42",
                character = ConversationCharacterSummary(
                    id = "character_2",
                    slug = "mia",
                    name = "Mia",
                    image = null,
                    description = "Gentle and caring",
                    task = "Companion"
                )
            ),
            onClick = {}
        )
    }
}
