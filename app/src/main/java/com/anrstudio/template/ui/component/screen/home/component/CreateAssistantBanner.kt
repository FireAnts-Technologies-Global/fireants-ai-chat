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
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*

@Composable
internal fun CreateAssistantBanner(
    backgroundPainter: Painter?,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(SdpR_250)
            .clip(RoundedCornerShape(SdpR_24))

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
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = SdpR_20, vertical = SdpR_16)
        ) {
            Text(
                text = stringResource(R.string.home_create_assistant_title),
                fontFamily = LoraBold,
                fontWeight = FontWeight.ExtraBold,
                fontSize = SdpR_24.nonScaledSp,
                lineHeight = SdpR_26.nonScaledSp,
                color = Color21164F
            )
            Text(
                text = stringResource(R.string.home_create_assistant_subtitle),
                fontFamily = ManropeRegular,
                fontWeight = FontWeight.Normal,
                fontSize = SdpR_14.nonScaledSp,
                lineHeight = SdpR_14.nonScaledSp,
                color = Color21164F
            )
            Spacer(modifier = Modifier.height(SdpR_16))
            Button(
                onClick = onCreateClick,
                modifier = Modifier
                    .height(SdpR_35)
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
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_14.nonScaledSp,
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
    widthDp = 430
)
@Composable
private fun CreateAssistantBannerPreview() {
    Box(
        modifier = Modifier
            .padding(SdpR_16)
    ) {
        CreateAssistantBanner(
            backgroundPainter = null,
            onCreateClick = {}
        )
    }
}
