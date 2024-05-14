package com.zepi.social_chat_food.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import android.content.res.Resources
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zepi.social_chat_food.R.string as AppText
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zepi.social_chat_food.ZepiAppState
import com.zepi.social_chat_food.iraaa.common.composable.AppNavRail
import com.zepi.social_chat_food.core.data.NetworkMonitor
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeChatViewModel
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeUserUidViewModel
import com.zepi.social_chat_food.ui.theme.ZepiTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun ZepiApp(
    shortcutParams: ShortcutParams?,
    widthSizeClass: WindowWidthSizeClass,
    networkMonitor: NetworkMonitor,
    isDarkTheme: Boolean,
    showInterstitialAds: () -> Unit,
    appState: ZepiAppState = rememberAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val includeChatViewModel: IncludeChatViewModel = viewModel()
    val includeUserUidViewModel: IncludeUserUidViewModel = viewModel()

    ZepiTheme(isDarkTheme) {
        val isOffline by appState.isOffline.collectAsStateWithLifecycle()
        val snackbarHostState = remember { SnackbarHostState() }

        val notConnectedMessage = stringResource(AppText.not_connected)
        LaunchedEffect(isOffline) {
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    message = notConnectedMessage,
                    duration = Indefinite,
                )
            }
        }

        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            QChatNavigationActions(navController)
        }

        val coroutineScope = rememberCoroutineScope()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: ZepiDestinations.HOME_ROUTE

        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
        val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)



            ModalNavigationDrawer(
            drawerContent = {
                AppDrawer(
                    currentRoute = currentRoute,
                    navigateToHome = navigationActions.navigateToHome,
                    navigateToChats = navigationActions.navigateToChats,
                    navigateToProfile = navigationActions.navigateToProfile,
                    navigateToSettings = navigationActions.navigateToSettings,
                    navigateToSubscriptions = navigationActions.navigateToSubscriptions,
                    closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } }
                )
            },
            drawerState = sizeAwareDrawerState,
            gesturesEnabled = !isExpandedScreen
        ) {
            Row {
                if (isExpandedScreen) {
                    AppNavRail(
                        currentRoute = currentRoute,
                        navigateToHome = {}, //navigationActions.navigateToHome,
                        navigateToSettings = navigationActions.navigateToSettings,
                    )
                }

                ZepiNavGraph(
                    popUpScreen = navigationActions.popUp,
                    restartApp = navigationActions.clearAndNavigate,

                    splashToHome = navigationActions.navigateAndPopUpSplashToHome,
                    profileToRegister = navigationActions.navigateRegister,
                    profileToLogin = navigationActions.navigateLogin,

                    loginToRegister = navigationActions.navigateAndPopUpLoginToRegister,
                    registerToLogin = navigationActions.navigateAndPopUpRegisterToLogin,

                    chatsToArchive = navigationActions.navigateArchive,

                    userProfileToChatNope = navigationActions.navigateAndPopUpUserProfileToChatNope,
                    userProfileToChatExist = navigationActions.navigateAndPopUpUserProfileToChatExist,

                    openLoginScreen = navigationActions.navigateLogin,
                    openChatExistScreen = {},
                    chatsToChat = {},

                    navigateToSplash = {},
                    navigateToRegister = {},
                    navigateToLogIn = {},
                    navigateEdit = navigationActions.navigateEdit,
                    navigateUserProfile = navigationActions.navigateUserProfile,
                    navigateSearch = navigationActions.navigateSearch,
                    navigateToHome = {}, //navigationActions.navigateToHome,

                    navigateAndPopUpSearchToUserProfile = navigationActions.navigateAndPopUpSearchToUserProfile,
                    navigateAndPopUpRegisterToEdit = navigationActions.navigateAndPopUpRegisterToEdit,
                    openAndPopUpChatNopeToExist = navigationActions.openAndPopUpChatNopeToExist,

                    includeChatViewModel = includeChatViewModel,
                    includeUserUidViewModel = includeUserUidViewModel,

                    isExpandedScreen = isExpandedScreen,
                    showInterstitialAds = showInterstitialAds,
                    navController = navController,

                    onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = Short,
                        ) == SnackbarResult.ActionPerformed
                    },


                    openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },

                    shortcutParams = shortcutParams
                )

            }
        }
    }
}

@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        drawerState
    } else {
        DrawerState(DrawerValue.Closed)
    }
}

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor,
): ZepiAppState {
    return remember(coroutineScope, networkMonitor) {
        ZepiAppState(coroutineScope, networkMonitor)
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
