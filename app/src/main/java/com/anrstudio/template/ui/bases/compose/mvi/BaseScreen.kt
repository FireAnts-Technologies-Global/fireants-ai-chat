package com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicError
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.appSplashBackground
import com.pegas.aura.aigirlfriend.soul.ui.component.dialog.ErrorRetryDialog
import com.pegas.aura.aigirlfriend.soul.ui.component.dialog.LoadingDialog

@Composable
fun <S : BaseUiState, I : Any, E : Any> BaseScreen(
    viewModel: BaseComposeViewModel<S, I, E>,
    screenName: String,
    fromScreen: String? = null,
    @StringRes errorTitleRes: Int? = null,
    modifier: Modifier = Modifier,
    showBackground: Boolean = true,
    showLoadingDialog: Boolean = false,
    loadingDialogText: String? = null,
    shouldShowLoadingDialog: (S) -> Boolean = { it.isLoading },
    onRetryError: (() -> Unit)? = null,
    onEffect: (E) -> Unit = {},
    onError: (@Composable (PublicError, () -> Unit) -> Unit)? = null,
    content: @Composable (state: S, onIntent: (I) -> Unit) -> Unit
) {
    TrackScreen(screenName = screenName, fromScreen = fromScreen)

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { onEffect(it) }
    }

    val boxModifier = if (showBackground) {
        modifier
            .fillMaxSize()
            .appSplashBackground()
    } else {
        modifier.fillMaxSize()
    }

    Box(
        modifier = boxModifier
    ) {
        content(state) { intent -> viewModel.handleIntent(intent) }

        if (showLoadingDialog && shouldShowLoadingDialog(state)) {
            LoadingDialog(
                loadingText = loadingDialogText ?: stringResource(R.string.loading_label)
            )
        }

        state.error?.let { error ->
            val dismissError = { viewModel.dismissError() }
            if (error.messageKey == com.pegas.aura.aigirlfriend.soul.domain.model.common.PublicMessageKey.INSUFFICIENT_COINS) {
                com.pegas.aura.aigirlfriend.soul.ui.component.bottomsheet.CoinsBottomSheet(
                    onDismiss = dismissError
                )
            } else {
                when {
                    onError != null -> onError(error, dismissError)
                    errorTitleRes != null -> {
                        ErrorRetryDialog(
                            title = context.getString(errorTitleRes),
                            error = error,
                            onDismiss = dismissError,
                            onRetry = {
                                dismissError()
                                onRetryError?.invoke()
                            }
                        )
                    }
                }
            }
        }
    }
}
