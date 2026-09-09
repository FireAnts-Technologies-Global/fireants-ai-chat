package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ads.AdRemoteConfig
import com.pegas.aura.aigirlfriend.soul.ads.banner_chat
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationMessage
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationMessageRole
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.BannerAdView
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.CommonTopBar
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.CommonTopBarStyle
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.bases.ext.showRateDialog
import com.pegas.aura.aigirlfriend.soul.ui.component.custom.LoadingAsyncImage
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component.ChatActionsBottomSheet
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component.ChatActionsButton
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component.ChatBackgroundBottomSheet
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component.ChatMessageInput
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component.ChatMoreBottomSheet
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component.ConversationStarterSuggestions
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component.MessageBubble
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.component.MessageLoadingBubble
import com.pegas.aura.aigirlfriend.soul.ui.model.asString
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChatRoomScreen(
    fromScreen: String? = null,
    onBack: (() -> Unit)? = null,
    viewModel: ChatRoomViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val deleteSuccessMessage = stringResource(
        R.string.chat_delete_success
    )

    val reportSuccessMessage = stringResource(
        R.string.chat_report_success
    )

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(
            ChatRoomIntent.Initialize
        )
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "ChatRoomScreen",
        fromScreen = fromScreen,
        errorTitleRes = R.string.chat_error_title,
        onRetryError = {
            viewModel.handleIntent(
                ChatRoomIntent.Retry
            )
        },
        onEffect = { effect ->
            when (effect) {
                ChatRoomEffect.ConversationDeleted -> {
                    Toast.makeText(
                        context,
                        deleteSuccessMessage,
                        Toast.LENGTH_SHORT
                    ).show()

                    onBack?.invoke()
                }

                ChatRoomEffect.ShowRateDialog -> {
                    val activity = context as? Activity
                    if (activity != null) {
                        showRateDialog(activity, false) {
                            viewModel.handleIntent(ChatRoomIntent.RateSubmitted)
                        }
                    }
                }

                ChatRoomEffect.ReportSuccess -> {
                    Toast.makeText(
                        context,
                        reportSuccessMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ChatRoomEffect.ShowMessage -> {
                    Toast.makeText(
                        context,
                        context.getString(effect.messageRes),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ChatRoomEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        effect.error.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    ) { state, onIntent ->

        ChatRoomContent(
            state = state,
            onBack = onBack,
            onMessageChanged = {
                onIntent(
                    ChatRoomIntent.MessageChanged(it)
                )
            },
            onSendMessage = {
                onIntent(
                    ChatRoomIntent.SendMessage
                )
            },
            onSuggestionClick = {
                onIntent(
                    ChatRoomIntent.SendSuggestedMessage(it)
                )
            },
            onDeleteChat = {
                onIntent(
                    ChatRoomIntent.DeleteConversation
                )
            },
            onReport = { reason ->
                onIntent(
                    ChatRoomIntent.Report(reason)
                )
            },
            onAssistantAnimationCompleted = {
                onIntent(
                    ChatRoomIntent.AssistantAnimationCompleted(it)
                )
            },
            onQuickPromptClick = { quickPromptId, quickPromptContent ->
                onIntent(
                    ChatRoomIntent.SendQuickPrompt(
                        quickPromptId,
                        quickPromptContent
                    )
                )
            },
            onOpenCustomBackground = {
                onIntent(
                    ChatRoomIntent.OpenCustomBackground
                )
            },
            onSelectBackground = {
                onIntent(
                    ChatRoomIntent.SelectBackground(it)
                )
            },
            onPurchaseBackground = {
                onIntent(
                    ChatRoomIntent.PurchaseBackground(it)
                )
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChatRoomContent(
    state: ChatRoomUiState,
    onBack: (() -> Unit)? = null,
    onMessageChanged: (String) -> Unit = {},
    onSendMessage: () -> Unit = {},
    onSuggestionClick: (String) -> Unit = {},
    onQuickPromptClick: (String, String) -> Unit = { _, _ -> },
    onDeleteChat: () -> Unit = {},
    onReport: (String) -> Unit = {},
    onAssistantAnimationCompleted: (String) -> Unit = {},
    onOpenCustomBackground: () -> Unit = {},
    onSelectBackground: (String) -> Unit = {},
    onPurchaseBackground: (String) -> Unit = {}
) {
    val hazeState = remember {
        HazeState()
    }

    val messageListState = rememberLazyListState()

    var activeSheet by rememberSaveable {
        mutableStateOf<ChatRoomBottomSheet?>(null)
    }

    val messageItemCount =
        state.messages.size +
                if (state.isAssistantTyping) {
                    1
                } else {
                    0
                }

    LaunchedEffect(messageItemCount) {
        if (
            messageItemCount > 0 &&
            messageListState.firstVisibleItemIndex <= 2
        ) {
            messageListState.scrollToItem(0)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
        ) {

            if (!state.currentBackgroundUrl.isNullOrBlank()) {
                LoadingAsyncImage(
                    imageUrl = state.currentBackgroundUrl,
                    contentDescription = "Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to ColorB30B0616,
                                0.4f to Color00160C2C,
                                0.75f to ColorD907030D,
                                1f to ColorF207030D
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .imePadding()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        SdpR_12
                    )
                ) {

                    when {
                        state.isLoading -> {

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(
                                        top = TopAppBarDefaults
                                            .TopAppBarExpandedHeight,
                                        start = SdpR_12,
                                        end = SdpR_12
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                ImageLoadingLottie(
                                    size = SdpR_48
                                )
                            }
                        }

                        state.messages.isEmpty() -> {

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(
                                        top = TopAppBarDefaults
                                            .TopAppBarExpandedHeight,
                                        start = SdpR_12,
                                        end = SdpR_12
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                ConversationStarterSuggestions(
                                    onSuggestionClick = onSuggestionClick,
                                    enabled = !state.isSending
                                )
                            }
                        }

                        else -> {
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = SdpR_12
                                    ),
                                state = messageListState,
                                reverseLayout = true,
                                contentPadding = PaddingValues(
                                    top = TopAppBarDefaults
                                        .TopAppBarExpandedHeight,
                                    bottom = SdpR_12
                                ),
                                verticalArrangement =
                                    Arrangement.spacedBy(
                                        space = SdpR_8,
                                        alignment = Alignment.Bottom
                                    )
                            ) {

                                if (state.isAssistantTyping) {
                                    item(
                                        key = "assistant_typing",
                                        contentType =
                                            "assistant_typing"
                                    ) {
                                        MessageLoadingBubble(
                                            assistantName =
                                                state.assistantName,
                                            assistantAvatarUrl =
                                                state.assistantAvatarUrl
                                        )
                                    }
                                }

                                val reversedMessages =
                                    state.messages.asReversed()

                                items(
                                    items = reversedMessages,
                                    key = {
                                        it.id
                                    },
                                    contentType = {
                                        "message"
                                    }
                                ) { message ->

                                    MessageBubble(
                                        message = message,
                                        assistantName =
                                            state.assistantName,
                                        assistantAvatarUrl =
                                            state.assistantAvatarUrl,
                                        animateContent =
                                            message.id ==
                                                    state.animatingAssistantMessageId,
                                        onAnimationCompleted = {
                                            onAssistantAnimationCompleted(
                                                message.id
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SdpR_12),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.spacedBy(
                            SdpR_8
                        )
                ) {

                    ChatActionsButton(
                        enabled = !state.isSending,
                        onClick = {
                            activeSheet =
                                ChatRoomBottomSheet.ACTIONS
                        }
                    )

                    ChatMessageInput(
                        value = state.inputMessage,
                        onValueChange = onMessageChanged,
                        isSending = state.isSending,
                        onSendMessage = onSendMessage
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (WindowInsets.isImeVisible) {
                                Modifier
                                    .height(0.dp)
                                    .clipToBounds()
                            } else {
                                Modifier.wrapContentHeight()
                            }
                        )
                ) {
                    BannerAdView(
                        adUnitId = AdRemoteConfig.banner_chat.id,
                        isEnabled = AdRemoteConfig.banner_chat.isEnable,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        CommonTopBar(
            title = state.title,
            subtitle = state.subtitle,
            style = CommonTopBarStyle.CHAT,
            showActions = true,
            hazeState = hazeState,
            coinCount = state.coinBalance,
            onBack = onBack,
            onMoreClick = {
                activeSheet =
                    ChatRoomBottomSheet.MORE
            },
            modifier = Modifier
                .align(
                    Alignment.TopCenter
                )
        )
    }

    if (
        activeSheet ==
        ChatRoomBottomSheet.MORE
    ) {
        ChatMoreBottomSheet(
            showCustomBackground = !state.currentBackgroundId.isNullOrBlank(),
            onDismiss = {
                activeSheet = null
            },
            onDeleteChatClick = onDeleteChat,
            onReportClick = onReport,
            onCustomBackgroundClick = {
                onOpenCustomBackground()

                activeSheet =
                    ChatRoomBottomSheet.CUSTOM_BACKGROUND
            }
        )
    }

    if (
        activeSheet ==
        ChatRoomBottomSheet.ACTIONS
    ) {
        ChatActionsBottomSheet(
            quickPrompts = state.quickPrompts,
            onDismiss = {
                activeSheet = null
            },
            onActionClick = { quickPromptId,
                              quickPromptContent ->
                onQuickPromptClick(
                    quickPromptId,
                    quickPromptContent
                )

                activeSheet = null
            }
        )
    }

    if (
        activeSheet ==
        ChatRoomBottomSheet.CUSTOM_BACKGROUND
    ) {
        ChatBackgroundBottomSheet(
            backgrounds = state.backgrounds,
            currentBackgroundId =
                state.currentBackgroundId,
            purchasingBackgroundId =
                state.purchasingBackgroundId,
            onDismiss = {
                activeSheet = null
            },
            onBackgroundClick = { bg ->

                if (bg.isLocked) {
                    onPurchaseBackground(
                        bg.id
                    )
                } else {
                    onSelectBackground(
                        bg.id
                    )
                }
            }
        )
    }
}

private enum class ChatRoomBottomSheet {
    MORE,
    ACTIONS,
    CUSTOM_BACKGROUND
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun ChatRoomScreenPreview() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .appSplashBackground()
    ) {

        ChatRoomContent(
            state = ChatRoomUiState(
                conversationId =
                    "conversation_preview",
                title =
                    "Hespera, 24",
                subtitle =
                    "Online",
                assistantName =
                    "Hespera",
                assistantAvatarUrl =
                    null,
                messages = listOf(
                    previewMessage(
                        id = "message_1",
                        role =
                            ConversationMessageRole.ASSISTANT,
                        content =
                            "The ancient books speak of a celestial alignment tonight."
                    ),
                    previewMessage(
                        id = "message_2",
                        role =
                            ConversationMessageRole.USER,
                        content =
                            "Shall we decode the stars together?"
                    ),
                    previewMessage(
                        id = "message_3",
                        role =
                            ConversationMessageRole.ASSISTANT,
                        content =
                            "I would love that. Let us begin with the brightest constellation."
                    )
                ),
                inputMessage =
                    "Tell me more"
            ),
            onBack = {}
        )
    }
}

private fun previewMessage(
    id: String,
    role: ConversationMessageRole,
    content: String
) = ConversationMessage(
    id = id,
    conversationId =
        "conversation_preview",
    role = role,
    content = content,
    tokenCount = 0,
    promptTokens = 0,
    completionTokens = 0,
    provider = null,
    model = null,
    estimatedCostUsd = 0.0,
    createdAt =
        "2026-07-28T10:00:00Z"
)
