package com.pegas.aura.aigirlfriend.soul.ui.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color000000
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color161127
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color1E192F
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color363144
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color6B5E80
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE5E7EB
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorF3F4F6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFF6A6A
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitSemiBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_15
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_17
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_20
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_42
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_46
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_9
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_90
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
fun ReportConfirmationDialog(
    title: String,
    message: String,
    confirmText: String,
    hintText: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    iconContent: @Composable () -> Unit
) {
    var reportReason by remember { mutableStateOf("") }

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

                Spacer(modifier = Modifier.height(16.dp))

                BasicTextField(
                    value = reportReason,
                    onValueChange = { reportReason = it },
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = ManropeRegular,
                        fontSize = SdpR_13.nonScaledSp,
                        color = Color000000
                    ),
                    cursorBrush = SolidColor(ColorD65A98),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SdpR_90)
                        .clip(RoundedCornerShape(SdpR_12))
                        .background(ColorFDFDFD)
                        .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_12))
                        .padding(SdpR_12),
                    decorationBox = { innerTextField ->
                        Box {
                            if (reportReason.isBlank()) {
                                Text(
                                    text = hintText,
                                    fontFamily = ManropeRegular,
                                    fontSize = SdpR_13.nonScaledSp,
                                    color = Color000000
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(SdpR_25))

                Button(
                    onClick = { onConfirm(reportReason) },
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
