package com.pegas.yuki.virtual.chat.ui.component.screen.chatroom.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color433440
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color990C051A
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorD9FFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8

@Composable
internal fun MessageLoadingBubble(
    assistantName: String,
    assistantAvatarUrl: String?
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_message)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            modifier = Modifier.widthIn(
                max = LocalConfiguration.current.screenWidthDp.dp * 0.82f
            ),
            shape = RoundedCornerShape(SdpR_16),
            colors = CardDefaults.cardColors(containerColor = ColorD9FFFFFF),
            border = BorderStroke(SdpR_1, ColorE8C3AC)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = SdpR_12,
                    vertical = SdpR_10
                ),
                verticalArrangement = Arrangement.spacedBy(SdpR_8)
            ) {
                AssistantMessageHeader(
                    name = assistantName.ifBlank {
                        stringResource(R.string.chat_role_assistant)
                    },
                    avatarUrl = assistantAvatarUrl,
                    createdAt = ""
                )
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(SdpR_24)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F, widthDp = 430)
@Composable
private fun MessageLoadingBubblePreview() {
    MessageLoadingBubble(
        assistantName = "Sakura",
        assistantAvatarUrl = null
    )
}
