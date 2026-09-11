package com.pegas.yuki.virtual.chat.ui.component.screen.chatroom.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.conversation.QuickPrompt
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color000000
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.Color08030F
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFDFDFD
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFFFFFF
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitSemiBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_18
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_32
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_40
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_56
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatActionsBottomSheet(
    quickPrompts: List<QuickPrompt>,
    onDismiss: () -> Unit,
    onActionClick: (String, String) -> Unit = { _, _ -> }
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = ColorFFFFFF,
        contentColor = ColorFDFDFD,
        scrimColor = Color.Black.copy(alpha = 0.68f),
        shape = RoundedCornerShape(
            topStart = SdpR_24,
            topEnd = SdpR_24
        ),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = SdpR_8)
                    .width(SdpR_40)
                    .height(SdpR_4)
                    .background(
                        color = ColorFDFDFD.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(SdpR_4)
                    )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    start = SdpR_20,
                    end = SdpR_20,
                    bottom = SdpR_16
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SdpR_12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.actions),
                    modifier = Modifier.weight(1f),
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_18.nonScaledSp,
                    color = Color000000
                )

                ActionsSheetCloseButton(
                    onClick = onDismiss
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false,
                verticalArrangement = Arrangement.spacedBy(SdpR_16),
                horizontalArrangement = Arrangement.spacedBy(SdpR_6)
            ) {
                items(quickPrompts) { item ->
                    ChatActionGridItem(
                        item = item,
                        onClick = { content ->
                            onActionClick(item.id, content)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatActionGridItem(
    item: QuickPrompt,
    onClick: (String) -> Unit
) {
    val title = item.title.ifEmpty { "Action" }

    val fallbackIconRes = when (title.lowercase()) {
        "care" -> R.drawable.img_action_care
        "hug" -> R.drawable.img_action_hug
        "kiss" -> R.drawable.img_action_kiss
        "slap" -> R.drawable.img_action_slap
        "suck" -> R.drawable.img_action_suck
        "touch" -> R.drawable.img_action_touch
        "electrify" -> R.drawable.img_action_electrify
        "gift" -> R.drawable.img_action_gift
        else -> R.drawable.img_action_care
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFDF8FF),
                shape = RoundedCornerShape(SdpR_32)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onClick(item.content)
                }
            )
            .padding(
                horizontal = SdpR_12,
                vertical = SdpR_16
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = item.icon,
            contentDescription = title,
            modifier = Modifier.size(SdpR_56),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(fallbackIconRes),
            error = painterResource(fallbackIconRes),
            fallback = painterResource(fallbackIconRes)
        )

        Spacer(
            modifier = Modifier.height(SdpR_8)
        )

        Text(
            text = title,
            fontFamily = OutfitSemiBold,
            fontSize = SdpR_14.nonScaledSp,
            color = Color000000,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (item.coinCost > 0) {
            Spacer(
                modifier = Modifier.height(SdpR_4)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.img_coin),
                    contentDescription = null,
                    modifier = Modifier.size(SdpR_12)
                )

                Spacer(
                    modifier = Modifier.width(SdpR_4)
                )

                Text(
                    text = item.coinCost.toString(),
                    fontFamily = OutfitSemiBold,
                    fontSize = SdpR_14.nonScaledSp,
                    color = Color000000,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun ChatActionsBottomSheetPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color08030F)
            .padding(SdpR_32)
    ) {
        ChatActionsBottomSheet(
            quickPrompts = listOf(
                QuickPrompt(
                    id = "1",
                    icon = "",
                    coinCost = 15,
                    title = "Care",
                    content = "*cares*",
                    sortOrder = 1
                ),
                QuickPrompt(
                    id = "2",
                    icon = "",
                    coinCost = 20,
                    title = "Hug",
                    content = "*hugs*",
                    sortOrder = 2
                ),
                QuickPrompt(
                    id = "3",
                    icon = "",
                    coinCost = 30,
                    title = "Electrify",
                    content = "*electrifies*",
                    sortOrder = 3
                )
            ),
            onDismiss = {}
        )
    }
}