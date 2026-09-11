package com.pegas.yuki.virtual.chat.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color000000
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color322D41
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import kotlinx.coroutines.delay

@Composable
fun LoadingDialog(
    loadingText: String = stringResource(R.string.loading_label)
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(horizontal = SdpR_24),
            shape = RoundedCornerShape(SdpR_24)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorFFFFFF)
                    .border(
                        width = 0.dp,
                        color = Color322D41,
                        shape = RoundedCornerShape(SdpR_24)
                    )
                    .padding(horizontal = SdpR_20, vertical = SdpR_16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(SdpR_12)
            ) {
                ImageLoadingLottie(size = SdpR_32)
                AnimatedLoadingText(baseText = loadingText)
            }
        }
    }
}

@Composable
private fun AnimatedLoadingText(baseText: String) {
    var dotCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(baseText) {
        while (true) {
            dotCount = (dotCount % 3) + 1
            delay(500L)
        }
    }

    Text(
        text = "$baseText${".".repeat(dotCount)}",
        color = Color000000,
        fontFamily = OutfitSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = SdpR_12.nonScaledSp,
        textAlign = TextAlign.Center
    )
}
