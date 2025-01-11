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
import com.test.test.model.extractChatId
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
import com.test.test.webrtc.ui.WebRtcActivity

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

//            composable<Route.ChatsList> {
//                ChatList(
//                    onChatClicked = { chatId -> navController.navigate(Route.ChatThread(chatId)) },
//                    modifier = Modifier.fillMaxSize(),
//                )
//            }
            composable<Route.Chats> {
                ChatsRoute(
                    onChatClicked = { chatId ->
                        //navController.navigate(Route.SingleChat(firstId, secondId, name))
                        navController.navigate(Route.SingleChat(chatId))
                    },
                )
            }

            composable<Route.Home> { HomeRoute(
                onItemClicked = { user, myId ->
                    navController.navigate(Route.UserProfile(user.id, myId))
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
                val myId = it.toRoute<Route.UserProfile>().myId

                UserProfileRoute(
                    userId,
                    onChatClicked = { roomId ->
                        //val chatId = extractChatId(shortcutParams.shortcutId)
                        navController.navigate(Route.SingleChat(roomId))
                    },
                    onVideoCallClicked = {
                        activity.startActivity(Intent(activity, WebRtcActivity::class.java))
                    }
                )
            }
            composable<Route.SingleChat>(
                deepLinks = listOf(
                    navDeepLink {
                        action = Intent.ACTION_VIEW
                        uriPattern = "https://socialite.google.com/chat/{roomId}"
                    },
                )
            ) { backStackEntry ->
                val route: Route.SingleChat = backStackEntry.toRoute()
                val roomId = route.roomId
                val prefilledText = route.text
                //val firstId = route.firstId
                //val secondId = route.secondId
                //val name = route.name
                ChatScreen(
                    chatId = roomId,
                    prefilledText = prefilledText,
                    onBackPressed = {navController.navigateUp()}
                )
            }

            //            composable<Route.ChatThread>(
//                deepLinks = listOf(
//                    navDeepLink {
//                        action = Intent.ACTION_VIEW
//                        uriPattern = "https://socialite.google.com/chat/{chatId}"
//                    },
//                ),
//            ) { backStackEntry ->
//                val route: Route.ChatThread = backStackEntry.toRoute()
//                val chatId = route.chatId
//                ChatScreen(
//                    chatId = chatId,
//                    foreground = true,
//                    onBackPressed = { navController.popBackStack() },
//                    onCameraClick = { navController.navigate(Route.Camera(chatId)) },
//                    onPhotoPickerClick = { navController.navigateToPhotoPicker(chatId) },
//                    onVideoClick = { uri -> navController.navigate(Route.VideoPlayer(uri)) },
//                    prefilledText = route.text,
//                    modifier = Modifier.fillMaxSize(),
//                )
//            }
        }
    }

    if (shortcutParams != null) {

        val chatId = extractChatId(shortcutParams.shortcutId)
        val text = shortcutParams.text
        android.util.Log.d("myTag", "shortcutId : ${shortcutParams.shortcutId}")
        android.util.Log.d("myTag", "nav controller, chat id : $chatId")
        android.util.Log.d("myTag", "nav controller, text : $text")
        navController.navigate(Route.SingleChat(chatId, text))
    }
}

data class ShortcutParams(
    val shortcutId: String,
    val text: String,
)
