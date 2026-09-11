package com.pegas.yuki.virtual.chat.ui.component.screen.setting

import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() :
    BaseComposeViewModel<SettingUiState, SettingIntent, SettingEffect>(SettingUiState()) {

    override fun handleIntent(intent: SettingIntent) {
        when (intent) {
            SettingIntent.Initialize -> Unit
        }
    }
}
