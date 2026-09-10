package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.dialog.ActionConfirmationDialog
import com.pegas.aura.aigirlfriend.soul.ui.component.dialog.ReportConfirmationDialog

import androidx.compose.material3.rememberModalBottomSheetState

private enum class ConfirmationAction {
    DELETE_CHAT,
    REPORT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatMoreBottomSheet(
    onDismiss: () -> Unit,
    onFavoriteClick: () -> Unit = {},
    onCustomBackgroundClick: () -> Unit = {},
    onDeleteChatClick: () -> Unit = {},
    onReportClick: (String) -> Unit = {},
    showCustomBackground: Boolean = true
) {
    var confirmationAction by remember { mutableStateOf<ConfirmationAction?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = ColorFFFFFF,
        contentColor = ColorFDFDFD,
        scrimColor = Color.Black.copy(alpha = 0.68f),
        shape = RoundedCornerShape(
            topStart = SdpR_24,
            topEnd = SdpR_24
        ),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = SdpR_8)
                    .width(SdpR_40)
                    .height(SdpR_4)
                    .background(
                        color = ColorFDFDFD.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(SdpR_4)
                    )
            )
        }
    ) {
        ChatMoreSheetContent(
            onDismiss = onDismiss,
            onFavoriteClick = onFavoriteClick,
            onCustomBackgroundClick = onCustomBackgroundClick,
            onDeleteChatClick = { confirmationAction = ConfirmationAction.DELETE_CHAT },
            onReportClick = { confirmationAction = ConfirmationAction.REPORT },
            showCustomBackground = showCustomBackground
        )
    }

    confirmationAction?.let { action ->
        val isDeleteAction = action == ConfirmationAction.DELETE_CHAT

        if (isDeleteAction) {
            ActionConfirmationDialog(
                title = stringResource(R.string.chat_delete_title),
                message = stringResource(R.string.chat_delete_message),
                confirmText = stringResource(R.string.chat_delete_confirm),
                onDismiss = { confirmationAction = null },
                onConfirm = {
                    confirmationAction = null
                    onDismiss()
                    onDeleteChatClick()
                },
                iconContent = {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_circle),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(SdpR_54)
                    )
                }
            )
        } else {
            ReportConfirmationDialog(
                title = stringResource(R.string.chat_report_title),
                message = stringResource(R.string.chat_report_message),
                confirmText = stringResource(R.string.chat_report_confirm),
                hintText = stringResource(R.string.chat_report_hint),
                onDismiss = { confirmationAction = null },
                onConfirm = { reason ->
                    confirmationAction = null
                    onDismiss()
                    onReportClick(reason)
                },
                iconContent = {
                    Icon(
                        painter = painterResource(R.drawable.ic_flag_circle),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(SdpR_54)
                    )
                }
            )
        }
    }
}

@Composable
private fun ChatMoreSheetContent(
    onDismiss: () -> Unit,
    onFavoriteClick: () -> Unit,
    onCustomBackgroundClick: () -> Unit,
    onDeleteChatClick: () -> Unit,
    onReportClick: () -> Unit,
    showCustomBackground: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(
                start = SdpR_20,
                end = SdpR_20,
                bottom = SdpR_16
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SdpR_12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.chat_more_title),
                modifier = Modifier.weight(1f),
                fontFamily = OutfitBold,
                fontWeight = FontWeight.Bold,
                fontSize = SdpR_20.nonScaledSp,
                color = Color1A1A2E
            )

            ActionsSheetCloseButton(onClick = onDismiss)
        }

        if (showCustomBackground) {
            ChatMoreItem(
                title = stringResource(R.string.chat_more_custom_background),
                iconRes = R.drawable.ic_chat_background,
                onClick = onCustomBackgroundClick
            )
            ChatMoreDivider()
        }
        ChatMoreItem(
            title = stringResource(R.string.chat_more_delete),
            iconRes = R.drawable.ic_delete_new,
            isDestructive = true,
            onClick = onDeleteChatClick
        )
        ChatMoreDivider()
        ChatMoreItem(
            title = stringResource(R.string.chat_more_report),
            iconRes = R.drawable.ic_chat_report,
            isDestructive = true,
            onClick = onReportClick
        )
    }
}

@Composable
private fun ChatMoreItem(
    title: String,
    @DrawableRes iconRes: Int,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    val contentColor = if (isDestructive) ColorFF6A6A else Color1A1A2E

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = SdpR_12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(SdpR_32)
                .background(
                    color = contentColor.copy(alpha = 0.09f),
                    shape = RoundedCornerShape(SdpR_8)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(SdpR_18),
                tint = if (isDestructive) ColorFF6A6A else Color000000
            )
        }

        Spacer(modifier = Modifier.width(SdpR_12))

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontFamily = OutfitMedium,
            fontSize = SdpR_16.nonScaledSp,
            color = contentColor
        )

        Icon(
            painter = painterResource(R.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier.size(SdpR_16),
            tint = contentColor.copy(alpha = 0.3f)
        )
    }
}

@Composable
private fun ChatMoreDivider() {
    HorizontalDivider(
        color = ColorFDFDFD.copy(alpha = 0.08f),
        thickness = SdpR_1
    )
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 430
)
@Composable
private fun ChatMoreSheetContentPreview() {
    Box(modifier = Modifier.background(ColorFFFFFF)) {
        ChatMoreSheetContent(
            onDismiss = {},
            onFavoriteClick = {},
            onCustomBackgroundClick = {},
            onDeleteChatClick = {},
            onReportClick = {},
            showCustomBackground = true
        )
    }
}
