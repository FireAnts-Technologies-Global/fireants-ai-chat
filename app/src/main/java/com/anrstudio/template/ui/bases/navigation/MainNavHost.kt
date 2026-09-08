package com.anrstudio.template.ui.bases.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.anrstudio.template.ads.AdsManager
import com.anrstudio.template.ui.bases.ext.findActivity
import com.anrstudio.template.ui.component.chatlist.ChatListScreen
import com.anrstudio.template.ui.component.home.HomeScreen
import com.anrstudio.template.ui.component.mission.MissionScreen
import com.anrstudio.template.ui.component.reward.RewardScreen

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
                    val activity = context.findActivity()
                    if (activity != null) {
                        AdsManager.loadInterAndShowInterHome(activity) {
                            rootNavController.navigate(
                                AppRoutes.characterDetail(slug),
                            )
                        }
                    } else {
                        rootNavController.navigate(
                            AppRoutes.characterDetail(slug),
                        )
                    }
                },
                onCreateAssistant = {
                    val activity = context.findActivity()
                    if (activity != null) {
                        AdsManager.loadInterAndShowInterHome(activity) {
                            rootNavController.navigate(AppRoutes.CREATE_CHARACTER)
                        }
                    } else {
                        rootNavController.navigate(AppRoutes.CREATE_CHARACTER)
                    }
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
                    val activity = context.findActivity()
                    if (activity != null) {
                        AdsManager.loadInterAndShowInterHome(activity) {
                            rootNavController.navigate(
                                AppRoutes.chatRoom(conversationId),
                            )
                        }
                    } else {
                        rootNavController.navigate(
                            AppRoutes.chatRoom(conversationId),
                        )
                    }
                },
                onCreateAssistant = {
                    val activity = context.findActivity()
                    if (activity != null) {
                        AdsManager.loadInterAndShowInterHome(activity) {
                            rootNavController.navigate(AppRoutes.CREATE_CHARACTER)
                        }
                    } else {
                        rootNavController.navigate(AppRoutes.CREATE_CHARACTER)
                    }
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