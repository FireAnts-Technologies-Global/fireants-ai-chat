package com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.app.AppConstants
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorA197B9
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorE8C3AC
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_11
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_12
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
fun StoreFooter(
    onRestorePurchasesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.store_restore_purchases),
            fontFamily = ManropeRegular,
            fontSize = SdpR_13.nonScaledSp,
            color = ColorE8C3AC,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { onRestorePurchasesClick() }
                .padding(SdpR_12)
        )


        Text(
            text = stringResource(id = R.string.store_terms_policies),
            fontFamily = OutfitBold,
            fontSize = SdpR_13.nonScaledSp,
            fontWeight = FontWeight.Bold,
            color = ColorFDFDFD
        )

        Spacer(modifier = Modifier.height(SdpR_6))

        Text(
            text = stringResource(id = R.string.store_transactions_info),
            fontFamily = ManropeRegular,
            fontSize = SdpR_11.nonScaledSp,
            color = ColorA197B9,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SdpR_6))

        Text(
            text = stringResource(id = R.string.store_terms_privacy),
            fontFamily = ManropeRegular,
            fontSize = SdpR_11.nonScaledSp,
            color = Color(0xFFA69EB3),
            modifier = Modifier
                .clickable {
                    try {
                        uriHandler.openUri(AppConstants.LINK_PRIVACY_POLICY)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                .padding(SdpR_6)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun StoreFooterPreview() {
    StoreFooter(
        onRestorePurchasesClick = {}
    )
}
