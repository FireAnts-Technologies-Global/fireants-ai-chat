package com.anrstudio.template.ui.bases.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anrstudio.template.ui.component.main.MainScreen
import com.pegas.aura.aigirlfriend.soul.ui.bases.navigation.LocalNavController

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
) {
    val rootNavController = rememberNavController()
    val context = LocalContext.current

    CompositionLocalProvider(LocalNavController provides rootNavController) {
        NavHost(
            navController = rootNavController,
            startDestination = AppRoutes.MAIN,
            modifier = modifier
        ) {
            composable(AppRoutes.MAIN) {
                MainScreen(
                    rootNavController = rootNavController,
                )
            }

//            composable(AppRoutes.CREATE_CHARACTER) {
//                CreateScreen(
//                    fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
//                    onBack = {
//                        val activity = context.findActivity()
//                        if (activity != null) {
//                            AdsManager.loadAndShowInterBack(activity) {
//                                if (rootNavController.previousBackStackEntry != null) {
//                                    rootNavController.popBackStack()
//                                }
//                            }
//                        } else {
//                            if (rootNavController.previousBackStackEntry != null) {
//                                rootNavController.popBackStack()
//                            }
//                        }
//                    },
//                    onOpenChat = { conversationId ->
//                        rootNavController.navigate(AppRoutes.chatRoom(conversationId)) {
//                            popUpTo(AppRoutes.MAIN) { inclusive = false }
//                        }
//                    },
//                )
//            }
//
//            composable(AppRoutes.MAIN_STORE) {
//                StoreScreen(
//                    fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
//                    onNavigateBack = {
//                        if (rootNavController.previousBackStackEntry != null) {
//                            rootNavController.popBackStack()
//                        }
//                    }
//                )
//            }
//
//            composable(AppRoutes.SUBSCRIPTION) {
//                SubscriptionScreen(
//                    onNavigateUp = {
//                        if (rootNavController.previousBackStackEntry != null) {
//                            rootNavController.popBackStack()
//                        }
//                    }
//                )
//            }
//
//            composable(
//                route = AppRoutes.CHARACTER_DETAIL_ROUTE,
//                arguments = listOf(
//                    navArgument(AppRoutes.CHARACTER_SLUG_ARG) { type = NavType.StringType }
//                )
//            ) {
//                CharacterDetailScreen(
//                    fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
//                    onBack = {
//                        val activity = context.findActivity()
//                        if (activity != null) {
//                            AdsManager.loadAndShowInterBack(activity) {
//                                if (rootNavController.previousBackStackEntry != null) {
//                                    rootNavController.popBackStack()
//                                }
//                            }
//                        } else {
//                            if (rootNavController.previousBackStackEntry != null) {
//                                rootNavController.popBackStack()
//                            }
//                        }
//                    },
//                    onOpenChat = { conversationId ->
//                        rootNavController.navigate(AppRoutes.chatRoom(conversationId))
//                    }
//                )
//            }
//
//            composable(
//                route = AppRoutes.CHAT_ROOM_ROUTE,
//                arguments = listOf(
//                    navArgument(AppRoutes.CONVERSATION_ID_ARG) { type = NavType.StringType }
//                )
//            ) {
//                ChatRoomScreen(
//                    fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
//                    onBack = {
//                        val activity = context.findActivity()
//                        if (activity != null) {
//                            AdsManager.loadAndShowInterBack(activity) {
//                                if (rootNavController.previousBackStackEntry != null) {
//                                    rootNavController.popBackStack()
//                                }
//                            }
//                        } else {
//                            if (rootNavController.previousBackStackEntry != null) {
//                                rootNavController.popBackStack()
//                            }
//                        }
//                    },
//                )
//            }
        }
    }
}
