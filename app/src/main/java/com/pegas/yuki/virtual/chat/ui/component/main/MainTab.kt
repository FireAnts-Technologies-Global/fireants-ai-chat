package com.pegas.yuki.virtual.chat.ui.component.main

import androidx.annotation.StringRes
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.navigation.AppRoutes

sealed class MainTab(
    val route: String,
    @param:StringRes val label: Int
) {
    data object Discover : MainTab(AppRoutes.MAIN_HOME, R.string.main_tab_discover)
    data object Reward : MainTab(AppRoutes.MAIN_REWARD, R.string.main_tab_reward)
    data object Add : MainTab(AppRoutes.CREATE_CHARACTER, R.string.main_tab_add)
    data object Chats : MainTab(AppRoutes.MAIN_CHAT, R.string.main_tab_chats)
    data object Mission : MainTab(AppRoutes.MAIN_MISSION, R.string.main_tab_mission)
}

val mainTabs = listOf(
    MainTab.Discover,
    MainTab.Reward,
    MainTab.Add,
    MainTab.Chats,
    MainTab.Mission
)
