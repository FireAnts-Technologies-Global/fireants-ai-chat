package com.anrstudio.template.ui.component.chatlist

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
import com.anrstudio.template.domain.model.conversation.ConversationCharacterSummary
import com.anrstudio.template.domain.model.conversation.ConversationSummary
import com.anrstudio.template.ui.bases.compose.component.ImageLoadingLottie
import com.anrstudio.template.ui.bases.compose.mvi.BaseScreen
import com.anrstudio.template.ui.bases.compose.theme.Color08030F
import com.anrstudio.template.ui.bases.compose.theme.Color090514
import com.anrstudio.template.ui.bases.compose.theme.ColorAFA5C3
import com.anrstudio.template.ui.bases.compose.theme.ColorE8C3AC
import com.anrstudio.template.ui.bases.compose.theme.ColorFDFDFD
import com.anrstudio.template.ui.bases.compose.theme.OutfitBold
import com.anrstudio.template.ui.bases.compose.theme.OutfitRegular
import com.anrstudio.template.ui.bases.compose.theme.SdpR_12
import com.anrstudio.template.ui.bases.compose.theme.SdpR_13
import com.anrstudio.template.ui.bases.compose.theme.SdpR_16
import com.anrstudio.template.ui.bases.compose.theme.SdpR_18
import com.anrstudio.template.ui.bases.compose.theme.SdpR_280
import com.anrstudio.template.ui.bases.compose.theme.SdpR_48
import com.anrstudio.template.ui.bases.compose.theme.SdpR_56
import com.anrstudio.template.ui.bases.compose.theme.SdpR_8
import com.anrstudio.template.ui.bases.compose.theme.appVerticalGradientBackground
import com.anrstudio.template.ui.bases.compose.theme.nonScaledSp
import com.anrstudio.template.ui.component.chatlist.component.SwipeableConversationHistoryItem
import com.anrstudio.template.ui.component.dialog.ActionConfirmationDialog
import com.pegas.aura.aigirlfriend.soul.R

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
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(SdpR_16),
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
                        ImageLoadingLottie(size = SdpR_56)
                    }
                }

                state.conversations.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_empty),
                            contentDescription = null,
                            modifier = Modifier.size(SdpR_280),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(SdpR_16))

                        Text(
                            text = stringResource(R.string.you_don_have_any_AI_assistant_yet),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutfitBold,
                            color = ColorFDFDFD,
                            textAlign = TextAlign.Center,
                            fontSize = SdpR_18.nonScaledSp,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(R.string.explore_custom_assistants_or_create_a_unique_soulmate_specifically_tailored_to_you_),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = ColorAFA5C3,
                            fontWeight = FontWeight.Normal,
                            fontFamily = OutfitRegular,
                            fontSize = SdpR_13.nonScaledSp,
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                onCreateAssistant()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(SdpR_48),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorE8C3AC
                            )
                        ) {
                            Text(
                                color = Color090514,
                                fontWeight = FontWeight.Bold,
                                fontFamily = OutfitBold,
                                fontSize = SdpR_16.nonScaledSp,
                                text = stringResource(R.string.create_now)
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        state = listState,
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
@Preview(showBackground = true, backgroundColor = 0xFF08030F, widthDp = 430, heightDp = 932)
@Composable
private fun ChatListScreenEmptyPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appVerticalGradientBackground()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color08030F
        ) {
            ChatListContent(state = ChatListUiState())
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F, widthDp = 430, heightDp = 932)
@Composable
private fun ChatListScreenWithDataPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appVerticalGradientBackground()
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
