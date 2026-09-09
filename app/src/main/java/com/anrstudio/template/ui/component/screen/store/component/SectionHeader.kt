package com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SdpR_4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = title,
            fontFamily = OutfitBold,
            fontSize = SdpR_16.nonScaledSp,
            fontWeight = FontWeight.Bold,
            color = ColorFDFDFD
        )
        Text(
            text = subtitle,
            fontFamily = ManropeRegular,
            fontSize = SdpR_12.nonScaledSp,
            color = ColorA69EB3
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun SectionHeaderPreview() {
    SectionHeader(
        title = "TOP UP GEMS",
        subtitle = "Unlock storylines & actions"
    )
}
