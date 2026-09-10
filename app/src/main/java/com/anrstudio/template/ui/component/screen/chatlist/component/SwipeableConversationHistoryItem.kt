package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatlist.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationSummary
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import kotlinx.coroutines.launch

@Composable
internal fun SwipeableConversationHistoryItem(
    conversation: ConversationSummary,
    enabled: Boolean,
    deleteDialogVisible: Boolean,
    hasUnread: Boolean = false,
    onDeleteRequest: () -> Unit,
    onClick: () -> Unit
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    var itemHeightPx by remember(conversation.id) { mutableIntStateOf(0) }
    var itemWidthPx by remember(conversation.id) { mutableIntStateOf(0) }
    val itemHeightDp = with(density) { itemHeightPx.toDp() }

    val buttonWidthDp = SdpR_68
    val buttonGapDp = SdpR_8
    val totalRevealDp = buttonWidthDp + buttonGapDp
    val totalRevealPx = with(density) { totalRevealDp.toPx() }

    var isDragging by remember(conversation.id) { mutableStateOf(false) }
    var dragOffsetX by remember(conversation.id) { mutableFloatStateOf(0f) }
    val animatedOffsetX = remember(conversation.id) { Animatable(0f) }
    val currentOffsetX = if (isDragging) dragOffsetX else animatedOffsetX.value

    LaunchedEffect(deleteDialogVisible) {
        if (!deleteDialogVisible && animatedOffsetX.value != 0f) {
            animatedOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 180)
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (itemHeightPx > 0 && currentOffsetX < 0f) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(buttonWidthDp)
                    .height(itemHeightDp)
                    .clip(RoundedCornerShape(SdpR_16))
                    .background(ColorFF382E)
                    .clickable { onDeleteRequest() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.chat_delete_confirm),
                    tint = Color.White,
                    modifier = Modifier.size(SdpR_24)
                )
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
                                    finalOffset <= -itemWidthPx * 0.45f
                            val passedRevealThreshold = finalOffset <= -totalRevealPx * 0.4f

                            coroutineScope.launch {
                                isDragging = false
                                animatedOffsetX.snapTo(finalOffset)
                                if (passedDeleteThreshold) {
                                    animatedOffsetX.animateTo(
                                        targetValue = -itemWidthPx.toFloat(),
                                        animationSpec = tween(durationMillis = 180)
                                    )
                                    onDeleteRequest()
                                } else if (passedRevealThreshold) {
                                    animatedOffsetX.animateTo(
                                        targetValue = -totalRevealPx,
                                        animationSpec = tween(durationMillis = 180)
                                    )
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
                                isDragging = false
                                animatedOffsetX.snapTo(finalOffset)
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
                hasUnread = hasUnread,
                onClick = {
                    if (animatedOffsetX.value != 0f) {
                        coroutineScope.launch {
                            animatedOffsetX.animateTo(0f, tween(180))
                        }
                    } else {
                        onClick()
                    }
                }
            )
        }
    }
}
