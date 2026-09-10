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
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

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
            color = Color(0xFF332E3F),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { onRestorePurchasesClick() }
                .padding(SdpR_12)
        )

        Text(
            text = stringResource(id = R.string.store_terms_policies),
            fontFamily = ManropeBold,
            fontSize = SdpR_13.nonScaledSp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E1B24)
        )

        Spacer(modifier = Modifier.height(SdpR_6))

        Text(
            text = stringResource(id = R.string.store_transactions_info),
            fontFamily = ManropeRegular,
            fontSize = SdpR_11.nonScaledSp,
            color = Color(0xFF9E97AA),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SdpR_6))

        Text(
            text = stringResource(id = R.string.store_terms_privacy),
            fontFamily = ManropeRegular,
            fontSize = SdpR_11.nonScaledSp,
            color = Color(0xFF9E97AA),
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
