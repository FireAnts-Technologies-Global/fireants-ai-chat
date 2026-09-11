package com.pegas.yuki.virtual.chat.ui.bases.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalNavController = compositionLocalOf<NavController> {
    error("No LocalNavController provided")
}
