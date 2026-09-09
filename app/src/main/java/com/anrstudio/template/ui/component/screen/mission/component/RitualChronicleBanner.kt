package com.pegas.aura.aigirlfriend.soul.ui.component.screen.mission.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color150F25
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color322D41
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color6B5E80
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorAFA5C3
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFFB03A
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitSemiBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_10
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_20
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
fun RitualChronicleBanner(
    completedQuests: Int,
    gemsObtained: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SdpR_20))
            .background(Color150F25)
            .border(SdpR_1, Color322D41, RoundedCornerShape(SdpR_20))
    ) {
        Image(
            painter = painterResource(R.drawable.img_banner_mission),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier.padding(SdpR_10)
        ) {
            Text(
                text = stringResource(R.string.mission_ritual_chronicle),
                fontFamily = OutfitExtraBold,
                fontSize = SdpR_20.nonScaledSp,
                color = ColorE8C3AC
            )

            Spacer(modifier = Modifier.height(SdpR_4))

            Text(
                text = stringResource(R.string.mission_ritual_chronicle_desc),
                fontFamily = ManropeRegular,
                fontSize = SdpR_12.nonScaledSp,
                color = ColorAFA5C3,
                lineHeight = SdpR_13.nonScaledSp
            )

            Spacer(modifier = Modifier.height(SdpR_10))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.mission_total_quests),
                        fontFamily = OutfitSemiBold,
                        fontSize = SdpR_11.nonScaledSp,
                        color = Color6B5E80
                    )

                    Spacer(modifier = Modifier.height(SdpR_2))

                    Text(
                        text = stringResource(
                            R.string.mission_completed_format,
                            completedQuests
                        ),
                        fontFamily = OutfitExtraBold,
                        fontSize = SdpR_16.nonScaledSp,
                        color = ColorFFFFFF
                    )
                }

                Spacer(modifier = Modifier.width(SdpR_20))

                Column {
                    Text(
                        text = stringResource(R.string.mission_coin_obtained),
                        fontFamily = OutfitSemiBold,
                        fontSize = SdpR_11.nonScaledSp,
                        color = Color6B5E80
                    )

                    Spacer(modifier = Modifier.height(SdpR_2))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "+$gemsObtained",
                            fontFamily = OutfitExtraBold,
                            fontSize = SdpR_16.nonScaledSp,
                            color = ColorFFB03A
                        )

                        Spacer(modifier = Modifier.width(SdpR_6))

                        Image(
                            painter = painterResource(R.drawable.img_coin),
                            contentDescription = null,
                            modifier = Modifier.size(SdpR_16
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun RitualChronicleBannerPreview() {
    RitualChronicleBanner(
        completedQuests = 24,
        gemsObtained = 385
    )
}
