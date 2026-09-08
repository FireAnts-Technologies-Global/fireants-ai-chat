package com.anrstudio.template.ui.component.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.anrstudio.template.ui.bases.compose.theme.SdpR_24
import com.pegas.aura.aigirlfriend.soul.R

@Composable
internal fun HomeEmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(SdpR_24),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.home_empty_characters),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08030F)
@Composable
private fun HomeEmptyContentPreview() {
    HomeEmptyContent()
}
