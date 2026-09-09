package com.pegas.aura.aigirlfriend.soul.ui.component.screen.subscription.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

@Composable
fun SubscriptionFeatureItem(iconRes: Int, textRes: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SdpR_6)
    ) {
        Box(
            modifier = Modifier
                .size(SdpR_28)
                .background(Color271531, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = ColorD65A98,
                modifier = Modifier.size(SdpR_14)
            )
        }
        Spacer(modifier = Modifier.width(SdpR_8))
        Text(
            text = stringResource(id = textRes),
            color = ColorFDFDFD,
            fontFamily = ManropeMedium,
            fontSize = SdpR_13.nonScaledSp
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun SubscriptionFeatureItemPreview() {
    SubscriptionFeatureItem(
        iconRes = R.drawable.ic_check,
        textRes = R.string.sub_feature_outfits
    )
}
