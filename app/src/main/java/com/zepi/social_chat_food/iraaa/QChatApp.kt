package com.zepi.social_chat_food.iraaa

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
import com.zepi.social_chat_food.iraaa.common.composable.AppNavRail
import com.zepi.social_chat_food.iraaa.core.data.NetworkMonitor
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeChatViewModel
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeUserUidViewModel
import com.zepi.social_chat_food.iraaa.ui.AppDrawer
import com.zepi.social_chat_food.iraaa.ui.QChatDestinations
import com.zepi.social_chat_food.iraaa.ui.QChatNavGraph
import com.zepi.social_chat_food.iraaa.ui.QChatNavigationActions
import com.zepi.social_chat_food.ui.theme.ZepiTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun QChatApp(
    widthSizeClass: WindowWidthSizeClass,
    networkMonitor: NetworkMonitor,
    isDarkTheme: Boolean,
    showInterstitialAds: () -> Unit,
    appState: QChatAppState = rememberAppState(
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
            navBackStackEntry?.destination?.route ?: QChatDestinations.HOME_ROUTE

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
                QChatNavGraph(
                    restartApp = navigationActions.clearAndNavigate,
                    popUpScreen = navigationActions.popUp,

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
                    openAndPopUpChatNopeToExist = navigationActions.openAndPopUpChatNopeToExist,

                    includeChatViewModel = includeChatViewModel,
                    includeUserUidViewModel = includeUserUidViewModel,

                    showInterstitialAds = showInterstitialAds,
                    isExpandedScreen = isExpandedScreen,
                    navController = navController,
                    openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },

                    onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = Short,
                        ) == SnackbarResult.ActionPerformed
                    }
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
): QChatAppState {
    return remember(coroutineScope, networkMonitor) {
        QChatAppState(coroutineScope, networkMonitor)
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
