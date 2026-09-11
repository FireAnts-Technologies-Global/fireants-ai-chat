package com.pegas.yuki.virtual.chat.ui.component.screen.chatroom.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color08030F
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_13
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_15
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_50
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@Composable
fun ChatActionsButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val contentColor = ColorFDFDFD.copy(alpha = if (enabled) 1f else 0.45f)

    Row(
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(SdpR_50)
            )
            .border(
                width = SdpR_1,
                color = Color.White.copy(alpha = 0.14f),
                shape = RoundedCornerShape(SdpR_50)
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(
                horizontal = SdpR_15,
                vertical = SdpR_4
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SdpR_6)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_hand),
            contentDescription = null,
            modifier = Modifier.size(SdpR_16),
            tint = contentColor
        )

        Text(
            fontFamily = OutfitRegular,
            fontWeight = FontWeight.Normal,
            text = stringResource(id = R.string.actions),
            color = contentColor,
            fontSize = SdpR_13.nonScaledSp
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430,
    heightDp = 180
)
@Composable
private fun ChatInputWithActionsPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color08030F)
            .padding(SdpR_16)
    ) {
        ChatActionsButton(
            onClick = {}
        )
    }
}
