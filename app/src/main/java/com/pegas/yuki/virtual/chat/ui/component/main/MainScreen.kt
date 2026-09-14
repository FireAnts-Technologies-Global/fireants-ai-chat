package com.pegas.yuki.virtual.chat.ui.component.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pegas.yuki.virtual.chat.ui.ads.navigateWithHomeInterstitial
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.CommonTopBar
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.appSplashBackground
import com.pegas.yuki.virtual.chat.ui.bases.navigation.AppRoutes
import com.pegas.yuki.virtual.chat.ui.bases.navigation.MainNavHost
import com.pegas.yuki.virtual.chat.ui.component.main.view.MainBottomBar

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val bottomNavController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in mainTabs.map { it.route }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .appSplashBackground()
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                topBar = {
                    if (showBottomBar) {
                        MainTopBar(
                            rootNavController = rootNavController,
                            viewModel = viewModel
                        )
                    }
                },
                bottomBar = {
                    if (showBottomBar) {
                        Column(
                            modifier = Modifier.padding(
                                start = SdpR_12,
                                end = SdpR_12,
                                bottom = SdpR_12
                            )
                        ) {
                            MainBottomBar(
                                tabs = mainTabs,
                                currentRoute = currentRoute,
                                onTabSelected = { tab ->
                                    if (tab.route == AppRoutes.CREATE_CHARACTER) {
                                        rootNavController.navigateWithHomeInterstitial(
                                            context,
                                            tab.route
                                        )
                                    } else {
                                        bottomNavController.navigate(tab.route) {
                                            popUpTo(
                                                bottomNavController.graph
                                                    .findStartDestination().id
                                            ) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    MainNavHost(
                        navController = bottomNavController,
                        rootNavController = rootNavController
                    )
                }
            }
        }
    }
}

@Composable
private fun MainTopBar(
    rootNavController: NavHostController,
    viewModel: MainViewModel
) {
    val coinBalance = viewModel.coinBalance.collectAsStateWithLifecycle().value

    CommonTopBar(
        title = "Yuki",
        coinCount = coinBalance,
        onAddCoinClick = {
            rootNavController.navigate(AppRoutes.MAIN_STORE)
        },
        onTrophyClick = {
            rootNavController.navigate(AppRoutes.SUBSCRIPTION)
        }
    )
}

@Preview(
    name = "Main Screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(
        rootNavController = navController,
    )
}
