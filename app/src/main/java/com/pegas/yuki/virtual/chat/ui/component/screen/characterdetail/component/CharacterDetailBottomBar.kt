package com.pegas.yuki.virtual.chat.ui.component.screen.characterdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppButton
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16

@Composable
internal fun CharacterDetailBottomBar(
    isStartingChat: Boolean,
    onStartChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(ColorFFFFFF)
            .padding(horizontal = SdpR_16, vertical = SdpR_12)
    ) {
        AppButton(
            text = stringResource(R.string.character_detail_start_chat),
            iconRes = R.drawable.ic_chat,
            loading = isStartingChat,
            onClick = onStartChat,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CharacterDetailBottomBarPreview() {
    CharacterDetailBottomBar(
        isStartingChat = false,
        onStartChat = {}
    )
}
