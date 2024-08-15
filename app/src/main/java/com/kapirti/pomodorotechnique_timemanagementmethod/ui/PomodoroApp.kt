package com.kapirti.pomodorotechnique_timemanagementmethod.ui

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
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.PomodoroAppState
import com.kapirti.pomodorotechnique_timemanagementmethod.core.data.NetworkMonitor
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeChatViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeJobViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.theme.PomodoroTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun PomodoroApp(
   // shortcutParams: ShortcutParams?,
    widthSizeClass: WindowWidthSizeClass,
    networkMonitor: NetworkMonitor,
    isDarkTheme: Boolean,
    appState: PomodoroAppState = rememberAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val includeUserIdViewModel: IncludeUserIdViewModel = viewModel()
    val includeChatViewModel: IncludeChatViewModel = viewModel()
    val includeJobViewModel: IncludeJobViewModel = viewModel()

    PomodoroTheme(isDarkTheme) {
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
            PomodoroNavigationActions(navController)
        }

        val coroutineScope = rememberCoroutineScope()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: PomodoroDestinations.PRODUCTIVITY_ROUTE

        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
        val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)



        PomodoroNavGraph(
            isExpandedScreen = isExpandedScreen,
            navController = navController,
            openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
            closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } },
            currentRoute = currentRoute,
            sizeAwareDrawerState = sizeAwareDrawerState,

            includeUserIdViewModel = includeUserIdViewModel,
            includeChatViewModel = includeChatViewModel,
            includeJobViewModel = includeJobViewModel,

            popUpScreen = navigationActions.popUp,
            restartApp = navigationActions.clearAndNavigate,

            navigateLogin = navigationActions.navigateLogin,
            navigateRegister = navigationActions.navigateRegister,
            navigateToTimeline = navigationActions.navigateToTimeline,
            navigateToHome = navigationActions.navigateToHome,
            navigateToProductivity = navigationActions.navigateToProductivity,
            navigateToJob = navigationActions.navigateToJob,
            navigateToEmployee = navigationActions.navigateToEmployee,
            navigateToChats = navigationActions.navigateToChats,
            navigateToProfile = navigationActions.navigateToProfile,
            navigateToSettings = navigationActions.navigateToSettings,
            navigateToSubscriptions = navigationActions.navigateToSubscriptions,
            navigateUserProfile = navigationActions.navigateUserProfile,
            navigateBlockedUser = navigationActions.navigateBlockedUsers,


            navigateAndPopUpSplashToTimeline = navigationActions.navigateAndPopUpSplashToTimeline,
            navigateAndPopUpRegisterToEdit = navigationActions.navigateAndPopUpRegisterToEdit,

            navigateEdit = navigationActions.navigateEdit,
            navigateTimeOver = navigationActions.navigateTimeOver,
            navigateJobDetail = navigationActions.navigateJobDetail,

            loginToRegister = navigationActions.navigateAndPopUpLoginToRegister,
            registerToLogin = navigationActions.navigateAndPopUpRegisterToLogin,

            /**
            openAndPopUpChatNopeToExist = navigationActions.openAndPopUpChatNopeToExist,


            userProfileToChatNope = navigationActions.navigateAndPopUpUserProfileToChatNope,

            openLoginScreen = navigationActions.navigateLogin,

            navigateSearch = navigationActions.navigateSearch,
            navigateUserProfile = navigationActions.navigateUserProfile,
            navigatePhotos = navigationActions.navigatePhotos,
            navigateVideoCall = navigationActions.navigateVideoCall,
            navigateBlockUser = navigationActions.navigateBlockUser,

            navigateAndPopUpSearchToUserProfile = navigationActions.navigateAndPopUpSearchToUserProfile,



            navigateChatsToChatExist = navigationActions.navigateChatExist,


            navigateToTimeline = navigationActions.navigateToTimeline,

            shortcutParams = shortcutParams*/
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
): PomodoroAppState {
    return remember(coroutineScope, networkMonitor) {
        PomodoroAppState(coroutineScope, networkMonitor)
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
