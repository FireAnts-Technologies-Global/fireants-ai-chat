package com.anrstudio.template.ui.bases.compose.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.anrstudio.template.utils.ANRTrackingHelper

@Composable
internal fun TrackScreen(screenName: String, fromScreen: String? = null) {
    LaunchedEffect(screenName) {
        ANRTrackingHelper.addScreenTrackCompose(screenName)
        fromScreen?.let {
            ANRTrackingHelper.fromScreenToScreenCompose(it, screenName)
        }
    }
}
