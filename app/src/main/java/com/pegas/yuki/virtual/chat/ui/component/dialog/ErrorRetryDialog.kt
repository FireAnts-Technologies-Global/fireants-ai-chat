package com.pegas.yuki.virtual.chat.ui.component.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ColorFF6A6A
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_54
import com.pegas.yuki.virtual.chat.ui.model.asString

@Composable
fun ErrorRetryDialog(
    title: String,
    error: PublicError,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
) {
    ActionConfirmationDialog(
        title = title,
        message = error.asString(),
        confirmText = stringResource(R.string.try_again),
        onDismiss = onDismiss,
        onConfirm = onRetry,
        iconContent = {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = ColorFF6A6A,
                modifier = Modifier.size(SdpR_54)
            )
        }
    )
}
