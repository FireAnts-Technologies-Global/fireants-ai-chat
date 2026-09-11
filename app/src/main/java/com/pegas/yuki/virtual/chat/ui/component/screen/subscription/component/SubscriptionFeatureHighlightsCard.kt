package com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppText
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeMedium
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_1
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_10
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_13
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_42
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_44
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

private val IconCircleBg = Color(0xFFF8ECFF)
private val IconTint = Color(0xFFE8287A)
private val CardBorderColor = Color(0xFFEDE7F6)
private val FeatureTextColor = Color(0xFF4C2878)

@Composable
fun SubscriptionFeatureHighlightsCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = SdpR_8,
                shape = RoundedCornerShape(SdpR_24),
                clip = false
            )
            .clip(RoundedCornerShape(SdpR_24))
            .background(Color.White.copy(alpha = 0.95f))
            .padding(
                vertical = SdpR_16,
                horizontal = SdpR_8
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            FeatureHighlightItem(
                iconRes = R.drawable.ic_sub_unlimited,
                textRes = R.string.sub_feature_unlimited_messages,
                modifier = Modifier.weight(1f)
            )

            FeatureHighlightItem(
                iconRes = R.drawable.ic_sub_exclusive,
                textRes = R.string.sub_feature_exclusive_companions,
                modifier = Modifier.weight(1f)
            )

            FeatureHighlightItem(
                iconRes = R.drawable.ic_sub_create,
                textRes = R.string.sub_feature_create_characters,
                modifier = Modifier.weight(1f)
            )

            FeatureHighlightItem(
                iconRes = R.drawable.ic_sub_background,
                textRes = R.string.sub_feature_chat_backgrounds,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FeatureHighlightItem(
    iconRes: Int,
    textRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(SdpR_42)
                .background(IconCircleBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(SdpR_24)
            )
        }

        Spacer(modifier = Modifier.height(SdpR_8))

        Text(
            text = stringResource(id = textRes),
            color = FeatureTextColor,
            fontFamily = ManropeRegular,
            fontSize = SdpR_10.nonScaledSp,
            lineHeight = SdpR_13.nonScaledSp,
            textAlign = TextAlign.Center
        )
    }
}
