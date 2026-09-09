package com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color0AFFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color0FD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color17FFFFFF
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD65A98
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_1
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_10
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_18
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_2
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_20
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_40
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_5
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
fun CoinPackageItem(
    displayName: String,
    price: String,
    badge: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val borderColor = if (isSelected) ColorD65A98 else Color17FFFFFF
    val borderWidth = if (isSelected) SdpR_2 else SdpR_1
    val bgColor = if (isSelected) Color0FD65A98 else Color0AFFFFFF


    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SdpR_16))
            .background(bgColor)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(SdpR_20)
            )
            .clickable { onClick() }
            .padding(vertical = SdpR_14, horizontal = SdpR_10),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.height(SdpR_20),
                contentAlignment = Alignment.Center
            ) {
                if (!badge.isNullOrBlank()) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = ColorD65A98,
                                shape = RoundedCornerShape(SdpR_6)
                            )
                            .padding(horizontal = SdpR_8, vertical = SdpR_4)
                    ) {
                        Text(
                            text = badge,
                            fontFamily = OutfitExtraBold,
                            fontSize = SdpR_10.nonScaledSp,
                            color = ColorFDFDFD
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(SdpR_4))

            Image(
                painter = painterResource(id = R.drawable.img_coin),
                contentDescription = null,
                modifier = Modifier.size(SdpR_40)
            )

            Spacer(modifier = Modifier.height(SdpR_5))

            Text(
                text = displayName,
                fontFamily = OutfitExtraBold,
                fontSize = SdpR_18.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = ColorFDFDFD
            )

            Spacer(modifier = Modifier.height(SdpR_10))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color0AFFFFFF,
                        shape = RoundedCornerShape(SdpR_12)
                    )
                    .border(
                        width = SdpR_1,
                        color = Color17FFFFFF,
                        shape = RoundedCornerShape(SdpR_12)
                    )
                    .padding(vertical = SdpR_6),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = price,
                    fontFamily = OutfitBold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = ColorE8C3AC
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 200
)
@Composable
private fun CoinPackageItemPreview() {
    CoinPackageItem(
        displayName = "2400 Coins",
        price = "526.000 đ",
        badge = "+400 (17%)",
        onClick = {}
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 200
)
@Composable
private fun CoinPackageItemNoBadgePreview() {
    CoinPackageItem(
        displayName = "100 Coins",
        price = "52.000 đ",
        badge = null,
        onClick = {}
    )
}
