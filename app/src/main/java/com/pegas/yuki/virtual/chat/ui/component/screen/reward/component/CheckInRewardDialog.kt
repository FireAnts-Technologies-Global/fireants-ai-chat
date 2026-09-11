package com.pegas.yuki.virtual.chat.ui.component.screen.reward.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppButton
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_100
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_18
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_28
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_48
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_86
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@Composable
fun CheckInRewardDialog(
    rewardAmount: Int,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        CheckInRewardDialogContent(
            rewardAmount = rewardAmount,
            onDismiss = onDismiss
        )
    }
}

@Composable
fun CheckInRewardDialogContent(
    rewardAmount: Int,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SdpR_24),
        shape = RoundedCornerShape(SdpR_28),
        colors = CardDefaults.cardColors(containerColor = ColorFFFFFF),
        elevation = CardDefaults.cardElevation(defaultElevation = SdpR_8)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorFFFFFF)
                .padding(horizontal = SdpR_24, vertical = SdpR_28),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(SdpR_86),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(SdpR_100),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_reward_trophy),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(SdpR_100)
                    )


                }
            }

            Spacer(modifier = Modifier.height(SdpR_20))

            Text(
                text = stringResource(R.string.reward_claimed_title),
                color = Color(0xFF161022),
                fontFamily = ManropeBold,
                fontWeight = FontWeight.Bold,
                fontSize = SdpR_24.nonScaledSp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(SdpR_8))

            Text(
                text = stringResource(R.string.reward_claimed_desc),
                color = Color(0xFF7A6F8B),
                fontFamily = ManropeRegular,
                fontWeight = FontWeight.Normal,
                fontSize = SdpR_14.nonScaledSp,
                textAlign = TextAlign.Center,
                lineHeight = SdpR_18.nonScaledSp,
                modifier = Modifier.padding(horizontal = SdpR_8)
            )

            Spacer(modifier = Modifier.height(SdpR_20))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 50))
                    .background(Color(0xFFFFF9EC))
                    .border(SdpR_1, Color(0xFFFFE7C2), RoundedCornerShape(percent = 50))
                    .padding(horizontal = SdpR_20, vertical = SdpR_10),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_coin),
                        contentDescription = null,
                        modifier = Modifier.size(SdpR_20)
                    )

                    Spacer(modifier = Modifier.width(SdpR_6))

                    Text(
                        text = stringResource(R.string.reward_claimed_coin_format, rewardAmount),
                        fontFamily = ManropeSemiBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = SdpR_18.nonScaledSp,
                        color = Color(0xFFE58B00)
                    )
                }
            }

            Spacer(modifier = Modifier.height(SdpR_24))

            AppButton(
                onClick = onDismiss,
                text = stringResource(R.string.continue_),
                textColor = ColorFFFFFF,
                shape = RoundedCornerShape(percent = 50),
                minHeight = SdpR_48,
                textStyle = TextStyle(
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_16.nonScaledSp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x80000000, widthDp = 390)
@Composable
private fun CheckInRewardDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        CheckInRewardDialogContent(
            rewardAmount = 20,
            onDismiss = {}
        )
    }
}
