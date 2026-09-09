package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component

import android.text.format.DateUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationMessage
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationMessageRole
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
internal fun MessageBubble(
    message: ConversationMessage,
    assistantName: String,
    assistantAvatarUrl: String?,
    animateContent: Boolean = false,
    onAnimationCompleted: () -> Unit = {}
) {
    val isUser = message.role == ConversationMessageRole.USER
    val maxBubbleWidth = LocalConfiguration.current.screenWidthDp.dp * 0.82f
    var visibleCharacterCount by remember(message.id, animateContent) {
        mutableIntStateOf(
            if (animateContent) 0 else message.content.length
        )
    }

    LaunchedEffect(message.id, message.content, animateContent) {
        if (!animateContent) {
            visibleCharacterCount = message.content.length
            return@LaunchedEffect
        }

        message.content.indices.forEach { index ->
            visibleCharacterCount = index + 1
            delay(TYPEWRITER_CHARACTER_DELAY_MS)
        }
        onAnimationCompleted()
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = maxBubbleWidth),
            shape = RoundedCornerShape(SdpR_16),
            colors = CardDefaults.cardColors(
                containerColor = ColorD9FFFFFF
            ),
            border = BorderStroke(
                width = SdpR_1,
                color = ColorE8C3AC
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = SdpR_12,
                    vertical = SdpR_10
                ),
                verticalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                if (!isUser) {
                    AssistantMessageHeader(
                        name = assistantName.ifBlank {
                            stringResource(R.string.chat_role_assistant)
                        },
                        avatarUrl = assistantAvatarUrl,
                        createdAt = message.createdAt
                    )
                }

                Text(
                    text = message.content.take(visibleCharacterCount),
                    color = Color1A0A2E,
                    fontFamily = ManropeRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = SdpR_14.nonScaledSp
                )
            }
        }
    }
}

@Composable
internal fun AssistantMessageHeader(
    name: String,
    avatarUrl: String?,
    createdAt: String
) {
    val formattedTime = remember(createdAt) { formatMessageTime(createdAt) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SdpR_8)
    ) {
        LoadingAsyncImage(
            imageUrl = avatarUrl,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(SdpR_24)
                .clip(CircleShape)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            fontFamily = OutfitBold,
            fontWeight = FontWeight.Bold,
            fontSize = SdpR_13.nonScaledSp,
            color = Color9B6E8A
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = formattedTime ?: stringResource(R.string.chat_message_just_now),
            fontFamily = ManropeRegular,
            fontSize = SdpR_12.nonScaledSp,
            color = ColorB0A0C0
        )
    }
}

private fun formatMessageTime(value: String): String? {
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

    val now = System.currentTimeMillis()
    val elapsed = (now - timestamp).coerceAtLeast(0L)

    return when {
        elapsed < DateUtils.MINUTE_IN_MILLIS -> null
        elapsed < DateUtils.DAY_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            timestamp,
            now,
            DateUtils.MINUTE_IN_MILLIS
        ).toString()

        else -> SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault())
            .format(Date(timestamp))
    }
}

@Preview(
    name = "User and assistant messages",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430
)
@Composable
private fun MessageBubblePreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color08030F)
            .padding(SdpR_16),
        verticalArrangement = Arrangement.spacedBy(SdpR_12)
    ) {
        MessageBubble(
            message = previewMessage(
                id = "user_message",
                role = ConversationMessageRole.USER,
                content = "Hey, what are you doing tonight?"
            ),
            assistantName = "Hespera",
            assistantAvatarUrl = null
        )
        MessageBubble(
            message = previewMessage(
                id = "assistant_message",
                role = ConversationMessageRole.ASSISTANT,
                content = "Just waiting to hear from you. How was your day?",
                provider = "OpenAI",
                model = "GPT"
            ),
            assistantName = "Hespera",
            assistantAvatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200"
        )
    }
}

private fun previewMessage(
    id: String,
    role: ConversationMessageRole,
    content: String,
    provider: String? = null,
    model: String? = null
) = ConversationMessage(
    id = id,
    conversationId = "preview_conversation",
    role = role,
    content = content,
    tokenCount = 0,
    promptTokens = 0,
    completionTokens = 0,
    provider = provider,
    model = model,
    estimatedCostUsd = 0.0,
    createdAt = "2026-07-29T09:51:00Z"
)

private const val TYPEWRITER_CHARACTER_DELAY_MS = 24L
