package com.pegas.aura.aigirlfriend.soul.ui.component.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component.CommonTopBar
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseScreen
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.SdpR_16

@Composable
fun SettingScreen(
    fromScreen: String? = null,
    viewModel: SettingViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.handleIntent(SettingIntent.Initialize)
    }

    BaseScreen(
        viewModel = viewModel,
        screenName = "SettingScreen",
        fromScreen = fromScreen
    ) { state, onItent ->
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CommonTopBar(title = state.title)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(SdpR_16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = state.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
