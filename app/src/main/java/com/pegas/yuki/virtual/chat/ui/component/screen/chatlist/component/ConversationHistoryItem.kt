package com.pegas.yuki.virtual.chat.ui.component.screen.chatlist.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationCharacterSummary
import com.pegas.yuki.virtual.chat.domain.model.conversation.ConversationSummary
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color000000
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color6F6794
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color756582
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import androidx.compose.ui.res.stringResource
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_2
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_48
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.appSplashBackground
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.custom.LoadingAsyncImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
internal fun ConversationHistoryItem(
    conversation: ConversationSummary,
    hasUnread: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val formattedTime = remember(
        conversation.lastMessageAt,
        conversation.updatedAt,
        conversation.createdAt
    ) {
        formatConversationTime(
            conversation.lastMessageAt
                ?: conversation.updatedAt
                .ifBlank { conversation.createdAt }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = SdpR_4,
                shape = RoundedCornerShape(SdpR_16),
                spotColor = Color(0x14000000),
                ambientColor = Color(0x0A000000)
            )
            .clip(RoundedCornerShape(SdpR_16))
            .background(ColorFFFFFF)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = SdpR_14, vertical = SdpR_12),
        horizontalArrangement = Arrangement.spacedBy(SdpR_12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LoadingAsyncImage(
            imageUrl = conversation.character?.image,
            contentDescription = conversation.character?.name,
            modifier = Modifier
                .size(SdpR_48)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(SdpR_4)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SdpR_6)
                ) {
                    val characterName = conversation.character?.name ?: conversation.title ?: conversation.id
                    Text(
                        text = characterName,
                        modifier = Modifier.weight(1f, fill = false),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ManropeBold,
                        fontSize = SdpR_16.nonScaledSp,
                        color = Color000000,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (conversation.backgroundId.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0x1FB440F2),
                                    shape = RoundedCornerShape(SdpR_4)
                                )
                                .padding(horizontal = SdpR_6, vertical = SdpR_2)
                        ) {
                            Text(
                                text = stringResource(R.string.chat_list_my_ai),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFB440F2),
                                fontSize = SdpR_10.nonScaledSp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SdpR_6)
                ) {
                    formattedTime?.let {
                        Text(
                            text = it,
                            fontFamily = ManropeRegular,
                            fontWeight = FontWeight.Normal,
                            fontSize = SdpR_10.nonScaledSp,
                            color = Color756582,
                            maxLines = 1
                        )
                    }

                    if (hasUnread) {
                        Box(
                            modifier = Modifier
                                .size(SdpR_6)
                                .background(color = Color(0xFF6C528E), shape = CircleShape)
                        )
                    }
                }
            }

            conversation.lastMessagePreview?.takeIf { it.isNotBlank() }?.let { lastMessagePreview ->
                Text(
                    text = lastMessagePreview,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    fontFamily = ManropeRegular,
                    fontSize = SdpR_14.nonScaledSp,
                    color = Color6F6794,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun formatConversationTime(value: String): String? {
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

    return if (DateUtils.isToday(timestamp)) {
        SimpleDateFormat("h:mm a", Locale.US).format(Date(timestamp))
    } else {
        SimpleDateFormat("MMM d", Locale.US).format(Date(timestamp))
    }
}

@Preview(showBackground = true, widthDp = 430)
@Composable
private fun ConversationHistoryItemWithImagePreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .appSplashBackground()
            .padding(SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_10)
    ) {
        ConversationHistoryItem(
            conversation = ConversationSummary(
                id = "conversation_1",
                userId = "user_1",
                characterId = "character_1",
                title = "Late night talk",
                lastMessagePreview = "Thanks for tonight's talk...",
                lastMessageAt = "2026-07-28 21:32",
                createdAt = "2026-07-28 21:00",
                updatedAt = "2026-07-28 21:32",
                character = ConversationCharacterSummary(
                    id = "character_1",
                    slug = "luna",
                    name = "Luna",
                    image = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400",
                    description = "Warm and playful",
                    task = "Soulmate",
                )
            ),
            hasUnread = true,
            onClick = {}
        )
        ConversationHistoryItem(
            conversation = ConversationSummary(
                id = "conversation_2",
                userId = "user_1",
                characterId = "character_2",
                title = "Morning check-in",
                lastMessagePreview = "I really enjoyed our conversation.",
                lastMessageAt = "2026-07-28 22:15",
                createdAt = "2026-07-28 08:00",
                updatedAt = "2026-07-28 22:15",
                character = ConversationCharacterSummary(
                    id = "character_2",
                    slug = "kai",
                    name = "Kai",
                    image = null,
                    description = "Gentle and caring",
                    task = "Companion"
                )
            ),
            hasUnread = false,
            onClick = {}
        )
    }
}
