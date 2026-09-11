package com.pegas.yuki.virtual.chat.ui.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color000000
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color322D41
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color6B5E80
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorE5E7EB
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorF3F4F6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFF453A
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFF6A6A
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_15
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_17
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_25
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_46
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_9
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@Composable
fun ActionConfirmationDialog(
    title: String,
    message: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    iconContent: @Composable () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_16),
            shape = RoundedCornerShape(SdpR_24),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorFFFFFF)
                    .border(width = 1.dp, color = Color322D41, shape = RoundedCornerShape(SdpR_24))
                    .padding(horizontal = SdpR_16, vertical = SdpR_16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                iconContent()
                Spacer(modifier = Modifier.height(SdpR_17))

                Text(
                    text = title,
                    color = Color000000,
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_20.nonScaledSp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = message,
                    color = Color6B5E80,
                    fontFamily = ManropeRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = SdpR_14.nonScaledSp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(SdpR_25))

                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SdpR_46),
                    shape = RoundedCornerShape(SdpR_16),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorFF6A6A,
                        contentColor = ColorFDFDFD
                    )
                ) {
                    Text(
                        text = confirmText,
                        fontFamily = OutfitSemiBold,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = SdpR_15.nonScaledSp
                    )
                }

                Spacer(modifier = Modifier.height(SdpR_9))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SdpR_46),
                    shape = RoundedCornerShape(SdpR_16),
                    border = BorderStroke(1.dp, ColorE5E7EB),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorF3F4F6,
                        contentColor = Color000000
                    )
                ) {
                    Text(
                        text = stringResource(R.string.txt_cancel),
                        fontFamily = OutfitSemiBold,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = SdpR_15.nonScaledSp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun DeleteActionConfirmationDialogPreview() {
    Box(modifier = Modifier.width(430.dp)) {
        ActionConfirmationDialog(
            title = "Delete chat?",
            message = "Are you sure you want to delete this chat? This action cannot be undone.",
            confirmText = "Delete",
            onDismiss = {},
            onConfirm = {},
            iconContent = {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = null,
                    tint = ColorFF453A,
                    modifier = Modifier.size(22.dp)
                )
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun ReportActionConfirmationDialogPreview() {
    Box(modifier = Modifier.width(430.dp)) {
        ActionConfirmationDialog(
            title = "Report",
            message = "Are you sure you want to report this user? We will review your report and take appropriate action.",
            confirmText = "Report",
            onDismiss = {},
            onConfirm = {},
            iconContent = {
                Icon(
                    imageVector = Icons.Outlined.Flag,
                    contentDescription = null,
                    tint = ColorFF453A,
                    modifier = Modifier.size(22.dp)
                )
            }
        )
    }
}
