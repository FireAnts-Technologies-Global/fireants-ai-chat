package com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatlist

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationCharacterSummary
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.ConversationSummary
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.ImageLoadingLottie
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.dialog.ActionConfirmationDialog
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatlist.component.SwipeableConversationHistoryItem

@Composable
fun ChatListScreen(
    fromScreen: String? = null,
    onOpenConversation: (String) -> Unit = {},
    onCreateAssistant: () -> Unit = {},
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val deleteSuccessMessage = stringResource(R.string.chat_delete_success)

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(ChatListIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "ChatListScreen",
        fromScreen = fromScreen,
        showBackground = false,
        errorTitleRes = R.string.chat_error_title,
        onRetryError = {
            viewModel.handleIntent(ChatListIntent.Retry)
        },
        onEffect = { effect ->
            when (effect) {
                is ChatListEffect.NavigateToConversation -> onOpenConversation(effect.conversationId)
                ChatListEffect.ConversationDeleted -> {
                    Toast.makeText(
                        context,
                        deleteSuccessMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ChatListEffect.CreateAssistant -> onCreateAssistant()
            }
        }
    ) { state, onIntent ->
        ChatListContent(
            state = state,
            onRetry = {
                onIntent(ChatListIntent.Retry)
            },
            onConfirmDeleteConversation = { conversationId ->
                onIntent(
                    ChatListIntent.DeleteConversation(conversationId),
                )
            },
            onOpenConversation = { conversationId ->
                onIntent(
                    ChatListIntent.OpenConversation(conversationId),
                )
            },
            onCreateAssistant = {
                onIntent(ChatListIntent.CreateAssistant)
            },
        )
    }
}

@Composable
private fun ChatListContent(
    state: ChatListUiState,
    onRetry: () -> Unit = {},
    onConfirmDeleteConversation: (String) -> Unit = {},
    onCreateAssistant: () -> Unit = {},
    onOpenConversation: (String) -> Unit = {},
) {
    var conversationPendingDelete by remember { mutableStateOf<ConversationSummary?>(null) }
    val listState = rememberLazyListState()

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = SdpR_16, end = SdpR_16, top = SdpR_16),
            verticalArrangement = Arrangement.spacedBy(SdpR_12)
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageLoadingLottie(size = SdpR_32)
                    }
                }

                state.conversations.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(bottom = SdpR_86),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_empty),
                            contentDescription = null,
                            modifier = Modifier.size(SdpR_130),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(SdpR_16))

                        Text(
                            text = stringResource(R.string.chat_empty_title),
                            fontWeight = FontWeight.Bold,
                            fontFamily = ManropeBold,
                            color = Color000000,
                            textAlign = TextAlign.Center,
                            fontSize = SdpR_22.nonScaledSp,
                        )

                        Spacer(modifier = Modifier.height(SdpR_8))

                        Text(
                            text = stringResource(R.string.chat_empty_subtitle),
                            textAlign = TextAlign.Center,
                            color = Color756582,
                            fontWeight = FontWeight.Normal,
                            fontFamily = ManropeRegular,
                            fontSize = SdpR_14.nonScaledSp,
                            lineHeight = SdpR_18.nonScaledSp,
                            modifier = Modifier.padding(horizontal = SdpR_24)
                        )

                        Spacer(modifier = Modifier.height(SdpR_24))

                        Row(
                            modifier = Modifier
                                .shadow(
                                    elevation = SdpR_12,
                                    shape = RoundedCornerShape(percent = 50),
                                    spotColor = Color(0x26000000),
                                    ambientColor = Color(0x14000000)
                                )
                                .clip(RoundedCornerShape(percent = 50))
                                .background(ColorFFFFFF)
                                .clickable { onCreateAssistant() }
                                .padding(horizontal = SdpR_24, vertical = SdpR_1),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_sparkle),
                                contentDescription = null,
                                tint = Color(0xFFB440F2),
                                modifier = Modifier.size(SdpR_48)
                            )
                            Spacer(modifier = Modifier.width(SdpR_8))
                            Text(
                                text = stringResource(R.string.chat_empty_start_chat),
                                fontFamily = ManropeSemiBold,
                                fontSize = SdpR_15.nonScaledSp,
                                color = Color(0xFFB440F2)
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        state = listState,
                        contentPadding = PaddingValues(bottom = SdpR_100),
                        verticalArrangement = Arrangement.spacedBy(SdpR_8)
                    ) {
                        items(
                            items = state.conversations,
                            key = { it.id },
                            contentType = { "conversation" }
                        ) { conversation ->
                            SwipeableConversationHistoryItem(
                                conversation = conversation,
                                enabled = state.deletingConversationId == null,
                                deleteDialogVisible = conversationPendingDelete?.id == conversation.id,
                                onDeleteRequest = { conversationPendingDelete = conversation },
                                onClick = { onOpenConversation(conversation.id) }
                            )
                        }
                    }
                }
            }
        }

        conversationPendingDelete?.let { conversation ->
            ActionConfirmationDialog(
                title = stringResource(R.string.chat_delete_title),
                message = stringResource(R.string.chat_delete_message),
                confirmText = stringResource(R.string.chat_delete_confirm),
                onDismiss = { conversationPendingDelete = null },
                onConfirm = {
                    onConfirmDeleteConversation(conversation.id)
                    conversationPendingDelete = null
                },
                iconContent = {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_circle),
                        contentDescription = stringResource(R.string.chat_delete_confirm),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(SdpR_48)
                    )
                }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
private fun ChatListScreenEmptyPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appSplashBackground()
    ) {
        ChatListContent(state = ChatListUiState())
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F, widthDp = 430, heightDp = 932)
@Composable
private fun ChatListScreenWithDataPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appSplashBackground()
    ) {
        ChatListContent(
            state = ChatListUiState(
                conversations = listOf(
                    ConversationSummary(
                        id = "conversation_1",
                        userId = "user_1",
                        characterId = "character_1",
                        title = "Late night talk",
                        lastMessageAt = "2026-07-28 22:15",
                        createdAt = "2026-07-28 21:00",
                        updatedAt = "2026-07-28 22:15",
                        character = ConversationCharacterSummary(
                            id = "character_1",
                            slug = "luna",
                            name = "Luna",
                            image = null,
                            description = "Warm and playful",
                            task = "Soulmate"
                        )
                    ),
                    ConversationSummary(
                        id = "conversation_2",
                        userId = "user_1",
                        characterId = "character_2",
                        title = "Morning check-in",
                        lastMessageAt = "2026-07-28 08:42",
                        createdAt = "2026-07-28 08:00",
                        updatedAt = "2026-07-28 08:42",
                        character = ConversationCharacterSummary(
                            id = "character_2",
                            slug = "mia",
                            name = "Mia",
                            image = null,
                            description = "Gentle and caring",
                            task = "Companion"
                        )
                    ),
                    ConversationSummary(
                        id = "conversation_3",
                        userId = "user_1",
                        characterId = "character_3",
                        title = null,
                        lastMessageAt = "2026-07-27 19:05",
                        createdAt = "2026-07-27 18:30",
                        updatedAt = "2026-07-27 19:05",
                        character = ConversationCharacterSummary(
                            id = "character_3",
                            slug = "ava",
                            name = "Ava",
                            image = null,
                            description = "Smart and curious",
                            task = "Advisor"
                        )
                    )
                )
            )
        )
    }
}
