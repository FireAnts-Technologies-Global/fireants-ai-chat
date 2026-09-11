package com.pegas.yuki.virtual.chat.ui.bases.compose.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.pegas.yuki.virtual.chat.utils.FireAntsTrackingHelper

@Composable
internal fun TrackScreen(screenName: String, fromScreen: String? = null) {
    LaunchedEffect(screenName) {
        FireAntsTrackingHelper.addScreenTrackCompose(screenName)
        fromScreen?.let {
            FireAntsTrackingHelper.fromScreenToScreenCompose(it, screenName)
        }
    }
}
