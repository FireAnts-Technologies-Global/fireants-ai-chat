package com.pegas.yuki.virtual.chat.ui.component.screen.chatroom.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color000000
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color1A0A2E
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD9FFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_15
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@Composable
internal fun ConversationStarterSuggestions(
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val suggestions = stringArrayResource(R.array.chat_starter_suggestions)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SdpR_10)
    ) {
        Text(
            text = stringResource(R.string.chat_starter_title),
            fontFamily = OutfitRegular,
            fontWeight = FontWeight.SemiBold,
            fontSize = SdpR_15.nonScaledSp,
            color = Color000000
        )

        Text(
            text = stringResource(R.string.chat_starter_subtitle),
            fontFamily = OutfitRegular,
            fontSize = SdpR_12.nonScaledSp,
            color = Color000000
        )

        suggestions.forEach { suggestion ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = enabled,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onSuggestionClick(suggestion)
                    },
                shape = RoundedCornerShape(SdpR_16),
                color = ColorD9FFFFFF,
                border = BorderStroke(
                    width = SdpR_1,
                    color = ColorE8C3AC
                )
            ) {
                Text(
                    text = suggestion,
                    modifier = Modifier.padding(
                        horizontal = SdpR_14,
                        vertical = SdpR_10
                    ),
                    fontFamily = OutfitRegular,
                    fontSize = SdpR_14.nonScaledSp,
                    color = if (enabled) Color1A0A2E else ColorAFA5C3
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430
)
@Composable
private fun ConversationStarterSuggestionsPreview() {
    ConversationStarterSuggestions(
        modifier = Modifier.padding(SdpR_16),
        onSuggestionClick = {}
    )
}
