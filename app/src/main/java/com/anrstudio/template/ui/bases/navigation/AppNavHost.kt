package com.pegas.aura.aigirlfriend.soul.ui.bases.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pegas.aura.aigirlfriend.soul.ui.ads.navigateWithHomeInterstitial
import com.pegas.aura.aigirlfriend.soul.ui.ads.popBackStackWithBackInterstitial
import com.pegas.aura.aigirlfriend.soul.ui.component.main.MainScreen
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.characterdetail.CharacterDetailScreen
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.chatroom.ChatRoomScreen
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.creat.CreateScreen
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.histories.HistoriesScreen
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.store.StoreScreen
import com.pegas.aura.aigirlfriend.soul.ui.component.screen.subscription.SubscriptionScreen

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

        composable(AppRoutes.CREATE_CHARACTER) {
            CreateScreen(
                fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
                onBack = {
                    rootNavController.popBackStackWithBackInterstitial(context)
                },
                onOpenChat = { conversationId ->
                    rootNavController.navigate(AppRoutes.chatRoom(conversationId)) {
                        popUpTo(AppRoutes.MAIN) { inclusive = false }
                    }
                },
            )
        }

        composable(AppRoutes.MAIN_STORE) {
            StoreScreen(
                fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
                onNavigateBack = {
                    if (rootNavController.previousBackStackEntry != null) {
                        rootNavController.popBackStack()
                    }
                }
            )
        }

            composable(AppRoutes.SUBSCRIPTION) {
                SubscriptionScreen(
                    onNavigateUp = {
                        if (rootNavController.previousBackStackEntry != null) {
                            rootNavController.popBackStack()
                        }
                    }
                )
            }

            composable(AppRoutes.HISTORIES) {
                HistoriesScreen(
                    fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
                    onBack = {
                        rootNavController.popBackStackWithBackInterstitial(context)
                    },
                    onOpenCharacterDetail = { slug ->
                        rootNavController.navigateWithHomeInterstitial(
                            context,
                            AppRoutes.characterDetail(slug)
                        )
                    }
                )
            }

        composable(
            route = AppRoutes.CHARACTER_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(AppRoutes.CHARACTER_SLUG_ARG) { type = NavType.StringType }
            )
        ) {
            CharacterDetailScreen(
                fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
                onBack = {
                    rootNavController.popBackStackWithBackInterstitial(context)
                },
                onOpenChat = { conversationId ->
                    rootNavController.navigate(AppRoutes.chatRoom(conversationId))
                }
            )
        }

        composable(
            route = AppRoutes.CHAT_ROOM_ROUTE,
            arguments = listOf(
                navArgument(AppRoutes.CONVERSATION_ID_ARG) { type = NavType.StringType }
            )
        ) {
            ChatRoomScreen(
                fromScreen = rootNavController.previousBackStackEntry?.destination?.route,
                onBack = {
                    rootNavController.popBackStackWithBackInterstitial(context)
                },
            )
        }
    }
}
}
