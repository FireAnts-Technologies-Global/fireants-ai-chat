package com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color08030F
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorF1CBB7
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_27
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_48
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
internal fun CharacterDetailBottomBar(
    isStartingChat: Boolean,
    onStartChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color08030F)
            .navigationBarsPadding()
            .padding(horizontal = SdpR_12, vertical = SdpR_12)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(SdpR_48)
                .clip(RoundedCornerShape(SdpR_27))
                .background(ColorF1CBB7)
                .clickable(enabled = !isStartingChat, onClick = onStartChat),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.character_detail_start_chat),
                fontFamily = OutfitBold,
                fontWeight = FontWeight.Bold,
                fontSize = SdpR_16.nonScaledSp,
                color = Color08030F
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun CharacterDetailBottomBarPreview() {
    CharacterDetailBottomBar(
        isStartingChat = false,
        onStartChat = {}
    )
}
