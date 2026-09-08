package com.anrstudio.template.ui.component.reward.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.anrstudio.template.ui.bases.compose.theme.Color150F25
import com.anrstudio.template.ui.bases.compose.theme.Color161127
import com.anrstudio.template.ui.bases.compose.theme.Color1AD4A24C
import com.anrstudio.template.ui.bases.compose.theme.Color322D41
import com.anrstudio.template.ui.bases.compose.theme.Color33D4A24C
import com.anrstudio.template.ui.bases.compose.theme.ColorAFA5C3
import com.anrstudio.template.ui.bases.compose.theme.ColorD4A24C
import com.anrstudio.template.ui.bases.compose.theme.ColorFFFFFF
import com.anrstudio.template.ui.bases.compose.theme.OutfitBold
import com.anrstudio.template.ui.bases.compose.theme.OutfitExtraBold
import com.anrstudio.template.ui.bases.compose.theme.OutfitRegular
import com.anrstudio.template.ui.bases.compose.theme.OutfitSemiBold
import com.anrstudio.template.ui.bases.compose.theme.SdpR_1
import com.anrstudio.template.ui.bases.compose.theme.SdpR_11
import com.anrstudio.template.ui.bases.compose.theme.SdpR_13
import com.anrstudio.template.ui.bases.compose.theme.SdpR_15
import com.anrstudio.template.ui.bases.compose.theme.SdpR_16
import com.anrstudio.template.ui.bases.compose.theme.SdpR_18
import com.anrstudio.template.ui.bases.compose.theme.SdpR_20
import com.anrstudio.template.ui.bases.compose.theme.SdpR_21
import com.anrstudio.template.ui.bases.compose.theme.SdpR_24
import com.anrstudio.template.ui.bases.compose.theme.SdpR_32
import com.anrstudio.template.ui.bases.compose.theme.SdpR_38
import com.anrstudio.template.ui.bases.compose.theme.SdpR_64
import com.anrstudio.template.ui.bases.compose.theme.SdpR_72
import com.anrstudio.template.ui.bases.compose.theme.SdpR_8
import com.anrstudio.template.ui.bases.compose.theme.SdpR_99
import com.anrstudio.template.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.R

@Composable
fun CheckInRewardDialog(
    rewardAmount: Int,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SdpR_24),
            shape = RoundedCornerShape(SdpR_24)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color161127)
                    .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_24))
                    .padding(horizontal = SdpR_24, vertical = SdpR_24),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(SdpR_72),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(SdpR_64)
                            .clip(CircleShape)
                            .background(Color150F25)
                            .border(SdpR_1, Color322D41, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            fontSize = SdpR_32.nonScaledSp,
                            color = ColorD4A24C
                        )
                    }
                }

                Spacer(modifier = Modifier.height(SdpR_16))

                Text(
                    text = stringResource(R.string.reward_claimed_title),
                    color = ColorFFFFFF,
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_21.nonScaledSp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(SdpR_8))

                Text(
                    text = stringResource(R.string.reward_claimed_desc),
                    color = ColorAFA5C3,
                    fontFamily = OutfitRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = SdpR_11.nonScaledSp,
                    textAlign = TextAlign.Center,
                    lineHeight = SdpR_18.nonScaledSp
                )

                Spacer(modifier = Modifier.height(SdpR_18))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(SdpR_99))
                        .background(Color1AD4A24C)
                        .border(SdpR_1, Color33D4A24C, RoundedCornerShape(SdpR_99))
                        .padding(horizontal = SdpR_20, vertical = SdpR_8),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.reward_claimed_coin_format, rewardAmount),
                        fontFamily = OutfitSemiBold,
                        fontSize = SdpR_15.nonScaledSp,
                        color = ColorD4A24C
                    )
                }

                Spacer(modifier = Modifier.height(SdpR_18))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SdpR_38),
                    shape = RoundedCornerShape(SdpR_99),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorD4A24C,
                        contentColor = Color161127
                    )
                ) {
                    Text(
                        text = stringResource(R.string.continue_),
                        fontFamily = OutfitExtraBold,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = SdpR_13.nonScaledSp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun CheckInRewardDialogPreview() {
    Box(modifier = Modifier.width(430.dp)) {
        CheckInRewardDialog(
            rewardAmount = 20,
            onDismiss = {}
        )
    }
}
