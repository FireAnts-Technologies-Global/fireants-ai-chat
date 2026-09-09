package com.pegas.aura.aigirlfriend.soul.ui.bases.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalNavController = compositionLocalOf<NavController> {
    error("No LocalNavController provided")
}
