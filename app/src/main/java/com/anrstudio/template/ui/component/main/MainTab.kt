package com.anrstudio.template.ui.component.main

import androidx.annotation.StringRes
import com.anrstudio.template.ui.bases.navigation.AppRoutes
import com.pegas.aura.aigirlfriend.soul.R

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
