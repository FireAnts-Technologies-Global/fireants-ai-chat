package com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.CoinAction
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.CommonTopBar

@Composable
fun StoreTopBar(
    coinCount: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CommonTopBar(
        title = stringResource(id = R.string.store_title),
        modifier = modifier,
        onBack = onBackClick,
        customActions = {
            CoinAction(
                coinCount = coinCount,
                onAddClick = null,
                showAddButton = false
            )
        }
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF08030F,
    widthDp = 430
)
@Composable
private fun StoreTopBarPreview() {
    Box(modifier = Modifier.fillMaxWidth()) {
        StoreTopBar(
            coinCount = 480,
            onBackClick = {}
        )
    }
}
