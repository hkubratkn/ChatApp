package com.test.test.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.test.test.ui.presentation.calls.CallsRoute
import com.test.test.ui.presentation.chats.ChatScreen
import com.test.test.ui.presentation.chats.ChatsRoute
import com.test.test.ui.presentation.home.HomeRoute
import com.test.test.ui.presentation.login.LogInScreen
import com.test.test.ui.presentation.register.RegisterScreen
import com.test.test.ui.presentation.settings.SettingsRoute
import com.test.test.ui.presentation.settings.SettingsScreen
import com.test.test.ui.presentation.splash.SplashScreen
import com.test.test.ui.presentation.userprofile.UserProfileRoute
import com.test.test.ui.theme.TestTheme

@Composable
fun TestApp(
    shortcutParams: ShortcutParams?,
) {
    val modifier = Modifier.fillMaxSize()
    TestTheme() {
        MainNavigation(modifier, shortcutParams)
    }
}

@Composable
private fun MainNavigation(
    modifier: Modifier,
    shortcutParams: ShortcutParams?,
) {
    val activity = LocalContext.current as Activity
    val navController = rememberNavController()

    navController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
        // Lock the layout of the Camera screen to portrait so that the UI layout remains
        // constant, even on orientation changes. Note that the camera is still aware of
        // orientation, and will assign the correct edge as the bottom of the photo or video.
//        if (destination.hasRoute<Route.Camera>()) {
//            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
//        } else {
//            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//        }
    }

    SocialiteNavSuite(
        navController = navController,
        modifier = modifier,
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.Splash,
            popExitTransition = {
                scaleOut(
                    targetScale = 0.9f,
                    transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f),
                )
            },
            popEnterTransition = {
                EnterTransition.None
            },
        ) {
            composable<Route.Chats> {
                ChatsRoute(
//                    onChatClicked = { chatId -> navController.navigate(Route.ChatThread(chatId)) },
//                    modifier = Modifier.fillMaxSize(),
                )
            }

            composable<Route.Home> { HomeRoute(
                onItemClicked = {
                    navController.navigate(Route.UserProfile(it.id))
                },
            ) }
            composable<Route.Calls> { CallsRoute() }

            composable<Route.Settings> {
                SettingsRoute(
                    navigateLogin = { /*TODO*/ },
                    navigateRegister = { /*TODO*/ },
                    navigateEdit = { /*TODO*/ },
                    restartApp = { /*TODO*/ },
                    navigateBlockedUser = { /*TODO*/ },
                    isExpandedScreen = false,
                    openDrawer = { /*TODO*/ },
                )
            }

            composable<Route.Splash> {
                SplashScreen(
                    navigateAndPopUpSplashToHome = {
                        navController.navigate(Route.Home){
                            launchSingleTop = true
                            popUpTo(Route.Splash){ inclusive = true }
                        }
                    },
                    navigateAndPopUpSplashToLogin = {
                        navController.navigate(Route.Login) {
                            launchSingleTop = true
                            popUpTo(Route.Splash) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable<Route.Login> {
                LogInScreen(
                    navigateAndPopUpLoginToRegister = {
                        navController.navigate(Route.Register) {
                            launchSingleTop = true
                            popUpTo(Route.Login) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable<Route.Register> {
                RegisterScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable<Route.UserProfile> {
                val userId = it.toRoute<Route.UserProfile>().userId
                val myId = "auB1JJ5RUHyZVfD5G9AP" // Hardcoded document id, say I'm name1

                UserProfileRoute(
                    userId,
                    onChatClicked = {
                        navController.navigate(Route.SingleChat(myId, userId))
                    }
                )
            }
            composable<Route.SingleChat> {
                val firstId = it.toRoute<Route.SingleChat>().firstId
                val secondId = it.toRoute<Route.SingleChat>().secondId
                ChatScreen(firstId, secondId)
            }
        }
    }
}

data class ShortcutParams(
    val shortcutId: String,
    val text: String,
)
