package com.pegas.yuki.virtual.chat.ui.bases.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pegas.yuki.virtual.chat.ui.ads.navigateWithHomeInterstitial
import com.pegas.yuki.virtual.chat.ui.component.screen.chatlist.ChatListScreen
import com.pegas.yuki.virtual.chat.ui.component.screen.home.HomeScreen
import com.pegas.yuki.virtual.chat.ui.component.screen.mission.MissionScreen
import com.pegas.yuki.virtual.chat.ui.component.screen.reward.RewardScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = AppRoutes.MAIN_HOME,
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(AppRoutes.MAIN_HOME) {
            HomeScreen(
                fromScreen = navController.previousRoute(),
                onOpenCharacterDetail = { slug ->
                    rootNavController.navigateWithHomeInterstitial(
                        context,
                        AppRoutes.characterDetail(slug)
                    )
                },
                onCreateAssistant = {
                    rootNavController.navigateWithHomeInterstitial(
                        context,
                        AppRoutes.CREATE_CHARACTER
                    )
                },
                onSeeAll = {
                    rootNavController.navigateWithHomeInterstitial(
                        context,
                        AppRoutes.HISTORIES
                    )
                }
            )
        }

        composable(AppRoutes.MAIN_REWARD) {
            RewardScreen(
                fromScreen = navController.previousRoute(),
                onNavigateToStore = {
                    rootNavController.navigate(AppRoutes.MAIN_STORE)
                }
            )
        }

        composable(AppRoutes.MAIN_CHAT) {
            ChatListScreen(
                fromScreen = navController.previousRoute(),
                onOpenConversation = { conversationId ->
                    rootNavController.navigateWithHomeInterstitial(
                        context,
                        AppRoutes.chatRoom(conversationId)
                    )
                },
                onCreateAssistant = {
                    rootNavController.navigateWithHomeInterstitial(
                        context,
                        AppRoutes.CREATE_CHARACTER
                    )
                }
            )
        }

        composable(AppRoutes.MAIN_MISSION) {
            MissionScreen(
                fromScreen = navController.previousRoute(),
            )
        }
    }
}

private fun NavHostController.previousRoute(): String? {
    return previousBackStackEntry
        ?.destination
        ?.route
}
