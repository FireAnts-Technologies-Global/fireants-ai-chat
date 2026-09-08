package com.anrstudio.template.ui.bases.compose.mvi

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
import com.anrstudio.template.domain.model.common.PublicError
import com.anrstudio.template.domain.model.common.PublicMessageKey
import com.anrstudio.template.ui.bases.compose.theme.appVerticalGradientBackground
import com.anrstudio.template.ui.component.bottomsheet.CoinsBottomSheet
import com.anrstudio.template.ui.component.dialog.ErrorRetryDialog
import com.anrstudio.template.ui.component.dialog.LoadingDialog
import com.pegas.aura.aigirlfriend.soul.R

@Composable
fun <S : BaseUiState, I : Any, E : Any> BaseScreen(
    viewModel: BaseComposeViewModel<S, I, E>,
    screenName: String,
    fromScreen: String? = null,
    @StringRes errorTitleRes: Int? = null,
    modifier: Modifier = Modifier,
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .appVerticalGradientBackground()
    ) {
        content(state) { intent -> viewModel.handleIntent(intent) }

        if (showLoadingDialog && shouldShowLoadingDialog(state)) {
            LoadingDialog(
                loadingText = loadingDialogText ?: stringResource(R.string.loading_label)
            )
        }

        state.error?.let { error ->
            val dismissError = { viewModel.dismissError() }
            if (error.messageKey == PublicMessageKey.INSUFFICIENT_COINS) {
                CoinsBottomSheet(
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
