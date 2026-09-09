package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.CHAT_MESSAGE_MAX_LENGTH

@Composable
internal fun ChatMessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    isSending: Boolean,
    onSendMessage: () -> Unit
) {
    val sendEnabled = value.isNotBlank() && !isSending
    val inputShape = RoundedCornerShape(percent = 50)
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val submitMessage = {
        if (sendEnabled) {
            keyboardController?.hide()
            focusManager.clearFocus()
            onSendMessage()
        }
    }

    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it.take(CHAT_MESSAGE_MAX_LENGTH)) },
        modifier = Modifier
            .fillMaxWidth()
            .height(SdpR_48)
            .background(ColorD9FFFFFF, inputShape)
            .border(SdpR_1, ColorE8C3AC, inputShape)
            .padding(horizontal = SdpR_12),
        enabled = !isSending,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(onSend = { submitMessage() }),
        textStyle = TextStyle(
            color = Color1A0A2E,
            fontFamily = ManropeRegular,
            fontWeight = FontWeight.Normal,
            fontSize = SdpR_13.nonScaledSp
        ),
        cursorBrush = SolidColor(ColorDC60FF),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = SdpR_10),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.chat_input_hint),
                            color = Color6B5E80,
                            fontFamily = ManropeRegular,
                            fontWeight = FontWeight.Normal,
                            fontSize = SdpR_14.nonScaledSp
                        )
                    }
                    innerTextField()
                }

                val sendButtonBrush = if (sendEnabled) SendButtonEnabledBrush else SendButtonDisabledBrush

                Box(
                    modifier = Modifier
                        .size(SdpR_34)
                        .background(
                            brush = sendButtonBrush,
                            shape = CircleShape
                        )
                        .clickable(
                            enabled = sendEnabled,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = submitMessage
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSending) {
                        ImageLoadingLottie(size = SdpR_14)
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.ArrowUpward,
                            contentDescription = stringResource(R.string.chat_send),
                            modifier = Modifier.size(SdpR_16),
                            tint = ColorFFFFFF
                        )
                    }
                }
            }
        }
    )
}

private val SendButtonEnabledBrush = Brush.linearGradient(
    colors = listOf(
        ColorDC60FF,
        ColorFF41BC,
        ColorFF8040
    ),
    start = Offset(0f, Float.POSITIVE_INFINITY),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

private val SendButtonDisabledBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFF1A8EB),
        Color(0xFFFA98D9),
        Color(0xFFFCAEA4)
    ),
    start = Offset(0f, Float.POSITIVE_INFINITY),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

@Preview(
    name = "Empty",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430
)
@Composable
private fun ChatMessageInputEmptyPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color08030F)
            .padding(SdpR_16)
    ) {
        ChatMessageInput(
            value = "",
            onValueChange = {},
            isSending = false,
            onSendMessage = {}
        )
    }
}

@Preview(
    name = "With message",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430
)
@Composable
private fun ChatMessageInputWithMessagePreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color08030F)
            .padding(SdpR_16)
    ) {
        ChatMessageInput(
            value = "Tell me more",
            onValueChange = {},
            isSending = false,
            onSendMessage = {}
        )
    }
}
