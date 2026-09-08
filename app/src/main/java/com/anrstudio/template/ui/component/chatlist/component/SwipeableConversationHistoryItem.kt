package com.anrstudio.template.ui.component.chatlist.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.ui.bases.compose.theme.ColorFDFDFD
import com.anrstudio.template.ui.bases.compose.theme.ColorFF453A
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatlist.component.ConversationHistoryItem
import kotlinx.coroutines.launch

@Composable
internal fun SwipeableConversationHistoryItem(
    conversation: ConversationSummary,
    enabled: Boolean,
    deleteDialogVisible: Boolean,
    onDeleteRequest: () -> Unit,
    onClick: () -> Unit
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    var itemHeightPx by remember(conversation.id) { mutableIntStateOf(0) }
    var itemWidthPx by remember(conversation.id) { mutableIntStateOf(0) }
    val actionWidthPx = itemHeightPx.toFloat()
    val iconRevealThresholdPx = actionWidthPx * 0.2f
    val itemHeightDp = with(density) { itemHeightPx.toDp() }
    var isDragging by remember(conversation.id) { mutableStateOf(false) }
    var dragOffsetX by remember(conversation.id) { mutableFloatStateOf(0f) }
    val animatedOffsetX = remember(conversation.id) { Animatable(0f) }
    val currentOffsetX = if (isDragging) dragOffsetX else animatedOffsetX.value
    val revealedWidthPx = (-currentOffsetX).coerceIn(0f, actionWidthPx)
    val revealFraction = if (actionWidthPx > 0f) {
        (revealedWidthPx / actionWidthPx).coerceIn(0f, 1f)
    } else {
        0f
    }

    LaunchedEffect(deleteDialogVisible) {
        if (!deleteDialogVisible) {
            animatedOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 180)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clipToBounds()
    ) {
        if (itemHeightPx > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(with(density) { revealedWidthPx.toDp() })
                    .height(itemHeightDp)
                    .background(ColorFF453A),
                contentAlignment = Alignment.Center
            ) {
                if (revealedWidthPx >= iconRevealThresholdPx) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = stringResource(R.string.chat_delete_confirm),
                        tint = ColorFDFDFD.copy(alpha = revealFraction)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    itemWidthPx = it.width
                    itemHeightPx = it.height
                }
                .offset { IntOffset(currentOffsetX.toInt(), 0) }
                .pointerInput(enabled, deleteDialogVisible, conversation.id) {
                    if (!enabled || deleteDialogVisible) return@pointerInput
                    detectHorizontalDragGestures(
                        onDragStart = {
                            isDragging = true
                            dragOffsetX = animatedOffsetX.value
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            dragOffsetX = (dragOffsetX + dragAmount)
                                .coerceIn(-itemWidthPx.toFloat(), 0f)
                        },
                        onDragEnd = {
                            val finalOffset = dragOffsetX
                            val passedDeleteThreshold = itemWidthPx > 0 &&
                                    finalOffset <= -itemWidthPx * 0.5f
                            coroutineScope.launch {
                                animatedOffsetX.snapTo(finalOffset)
                                isDragging = false
                                if (passedDeleteThreshold) {
                                    animatedOffsetX.animateTo(
                                        targetValue = -itemWidthPx.toFloat(),
                                        animationSpec = tween(durationMillis = 180)
                                    )
                                    onDeleteRequest()
                                } else {
                                    animatedOffsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = tween(durationMillis = 180)
                                    )
                                }
                            }
                        },
                        onDragCancel = {
                            val finalOffset = dragOffsetX
                            coroutineScope.launch {
                                animatedOffsetX.snapTo(finalOffset)
                                isDragging = false
                                animatedOffsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(durationMillis = 180)
                                )
                            }
                        }
                    )
                }
        ) {
            ConversationHistoryItem(
                conversation = conversation,
                onClick = onClick
            )
        }
    }
}
