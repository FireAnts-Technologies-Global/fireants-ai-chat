package com.pegas.aura.aigirlfriend.soul.ui.component.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color08030F
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.Color8B5CF6
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorD84DB7
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.ManropeRegular
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.OutfitExtraBold
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_13
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_14
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_20
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_22
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_39
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_4
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_8
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.nonScaledSp

@Composable
internal fun CreateAssistantBanner(
    backgroundPainter: Painter?,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(SdpR_24))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        ColorD84DB7.copy(alpha = 0.42f),
                        Color08030F
                    )
                )
            )
    ) {
        backgroundPainter?.let {
            Image(
                painter = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color08030F.copy(alpha = 0.18f))
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = SdpR_20, vertical = SdpR_16)
        ) {
            Text(
                text = stringResource(R.string.home_create_assistant_title),
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = OutfitExtraBold,
                fontWeight = FontWeight.ExtraBold,
                fontSize = SdpR_22.nonScaledSp,
                color = ColorFDFDFD
            )
            Text(
                text = stringResource(R.string.home_create_assistant_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = ManropeRegular,
                fontSize = SdpR_13.nonScaledSp,
                color = ColorFDFDFD
            )
            Spacer(modifier = Modifier.height(SdpR_16))
            Button(
                onClick = onCreateClick,
                modifier = Modifier
                    .height(SdpR_39)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                ColorD84DB7,
                                Color8B5CF6
                            )
                        ),
                        shape = RoundedCornerShape(SdpR_24)
                    ),
                shape = RoundedCornerShape(SdpR_24),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(
                    horizontal = SdpR_16,
                    vertical = SdpR_4
                )
            ) {
                Text(
                    text = stringResource(R.string.home_create_assistant_action),
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_13.nonScaledSp,
                    color = ColorFDFDFD
                )
                Spacer(modifier = Modifier.width(SdpR_8))
                Image(
                    painter = painterResource(R.drawable.ic_magic_wand),
                    contentDescription = null,
                    modifier = Modifier.size(SdpR_14)
                )
            }
        }
    }
}

@Preview(
    name = "Create assistant banner",
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430
)
@Composable
private fun CreateAssistantBannerPreview() {
    Box(
        modifier = Modifier
            .background(Color08030F)
            .padding(SdpR_16)
    ) {
        CreateAssistantBanner(
            backgroundPainter = null,
            onCreateClick = {}
        )
    }
}
