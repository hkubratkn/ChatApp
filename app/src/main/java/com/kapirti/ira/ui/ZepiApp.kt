package com.kapirti.ira.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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
import com.kapirti.ira.R.string as AppText
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kapirti.ira.ZepiAppState
import com.kapirti.ira.core.data.NetworkMonitor
import com.kapirti.ira.core.viewmodel.IncludeChatViewModel
import com.kapirti.ira.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.ira.ui.theme.ZepiTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun ZepiApp(
    hasUser: Boolean,
    shortcutParams: ShortcutParams?,
    widthSizeClass: WindowWidthSizeClass,
    networkMonitor: NetworkMonitor,
    isDarkTheme: Boolean,
    showInterstitialAds: () -> Unit,
    appState: ZepiAppState = rememberAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val includeUserIdViewModel: IncludeUserIdViewModel = viewModel()
    val includeChatViewModel: IncludeChatViewModel = viewModel()

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



        ZepiNavGraph(
            popUpScreen = navigationActions.popUp,
            restartApp = navigationActions.clearAndNavigate,


            openAndPopUpSplashToHome = navigationActions.openAndPopUpSplashToHome,
            openAndPopUpSplashToLogin = navigationActions.openAndPopUpSplashToLogin,

            loginToRegister = navigationActions.navigateAndPopUpLoginToRegister,
            registerToLogin = navigationActions.navigateAndPopUpRegisterToLogin,


            userProfileToChatNope = navigationActions.navigateAndPopUpUserProfileToChatNope,

            openLoginScreen = navigationActions.navigateLogin,

            navigateEdit = navigationActions.navigateEdit,
            navigateUserProfile = navigationActions.navigateUserProfile,
            navigateSearch = navigationActions.navigateSearch,
            navigateBlockUser = navigationActions.navigateBlockUser,

            navigateAndPopUpSearchToUserProfile = navigationActions.navigateAndPopUpSearchToUserProfile,
            navigateAndPopUpRegisterToEdit = navigationActions.navigateAndPopUpRegisterToEdit,

            includeUserIdViewModel = includeUserIdViewModel,
            includeChatViewModel = includeChatViewModel,

            navigateChatsToChatExist = navigationActions.navigateChatExist,

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
            closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } },
            currentRoute = currentRoute,
            sizeAwareDrawerState = sizeAwareDrawerState,

            navigateToSettings = navigationActions.navigateToSettings,
            navigateToHome = navigationActions.navigateToHome,
            navigateToTimeline = navigationActions.navigateToTimeline,
            navigateToChats = navigationActions.navigateToChats,
            navigateToProfile = navigationActions.navigateToProfile,
            navigateToSubscriptions = navigationActions.navigateToSubscriptions,

            hasUser = hasUser,
            shortcutParams = shortcutParams
        )

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
