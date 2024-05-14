package com.zepi.social_chat_food.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeChatViewModel
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeUserUidViewModel
import com.zepi.social_chat_food.ui.presentation.edit.EditRoute
import com.zepi.social_chat_food.ui.presentation.userprofile.UserProfileRoute
import com.zepi.social_chat_food.ui.presentation.profile.ProfileRoute
import com.zepi.social_chat_food.ui.presentation.settings.SettingsRoute
import com.zepi.social_chat_food.ui.presentation.subscribe.SubscribeScreen
import com.zepi.social_chat_food.soci.model.extractChatId
import com.zepi.social_chat_food.soci.ui.camera.Camera
import com.zepi.social_chat_food.soci.ui.camera.Media
import com.zepi.social_chat_food.soci.ui.camera.MediaType
import com.zepi.social_chat_food.soci.ui.home.Home
import com.zepi.social_chat_food.soci.ui.photopicker.navigation.navigateToPhotoPicker
import com.zepi.social_chat_food.soci.ui.photopicker.navigation.photoPickerScreen
import com.zepi.social_chat_food.soci.ui.player.VideoPlayerScreen
import com.zepi.social_chat_food.soci.ui.videoedit.VideoEditScreen
import com.zepi.social_chat_food.ui.presentation.home.HomeRoute
import com.zepi.social_chat_food.ui.presentation.login.LogInScreen
import com.zepi.social_chat_food.ui.presentation.register.RegisterScreen
import com.zepi.social_chat_food.ui.presentation.search.SearchScreen


@Composable
fun ZepiNavGraph(
    restartApp : () -> Unit,
    popUpScreen: () -> Unit,

    splashToHome: () -> Unit,
    profileToLogin: () -> Unit,
    profileToRegister: () -> Unit,

    loginToRegister: () -> Unit,
    registerToLogin: () -> Unit,

    chatsToArchive: () -> Unit,
    chatsToChat: () -> Unit,

    userProfileToChatExist: () -> Unit,
    userProfileToChatNope: () -> Unit,

    openChatExistScreen: () -> Unit,
    openLoginScreen: () -> Unit,

    navigateToSplash: () -> Unit,
    navigateToLogIn: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateEdit: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigateSearch: () -> Unit,
    navigateToHome: () -> Unit,

    navigateAndPopUpSearchToUserProfile: () -> Unit,
    navigateAndPopUpRegisterToEdit: () -> Unit,
    openAndPopUpChatNopeToExist: () -> Unit,

    includeChatViewModel: IncludeChatViewModel,
    includeUserUidViewModel: IncludeUserUidViewModel,


    onShowSnackbar: suspend (String, String?) -> Boolean,
    showInterstitialAds: () -> Unit,
    isExpandedScreen: Boolean,

    openDrawer: () -> Unit = {},
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ZepiDestinations.HOME_ROUTE,

    shortcutParams: ShortcutParams?,
) {
    val activity = LocalContext.current as Activity
    // val navController = rememberNavController()

    navController.addOnDestinationChangedListener { _: NavController, navDestination: NavDestination, _: Bundle? ->
        // Lock the layout of the Camera screen to portrait so that the UI layout remains
        // constant, even on orientation changes. Note that the camera is still aware of
        // orientation, and will assign the correct edge as the bottom of the photo or video.
        if (navDestination.route?.contains("camera") == true) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,//"home",
        popEnterTransition = {
            scaleIn(initialScale = 1.1F) + fadeIn()
        },
        popExitTransition = {
            scaleOut(targetScale = 0.9F) + fadeOut()
        },
        modifier = modifier,
    ) {
        composable(
            route = ZepiDestinations.CHATS_ROUTE//"home",
        ) {
            Home(
                modifier = Modifier.fillMaxSize(),
                onChatClicked = { chatId -> navController.navigate("chat/$chatId") },
            )
        }
        composable(
            route = "chat/{chatId}?text={text}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.LongType },
                navArgument("text") { defaultValue = "" },
            ),
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = "https://socialite.google.com/chat/{chatId}"
                },
            ),
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getLong("chatId") ?: 0L
            val text = backStackEntry.arguments?.getString("text")
            com.zepi.social_chat_food.soci.ui.chat.ChatScreen(
                chatId = chatId,
                foreground = true,
                onBackPressed = { navController.popBackStack() },
                onCameraClick = { navController.navigate("chat/$chatId/camera") },
                onPhotoPickerClick = { navController.navigateToPhotoPicker(chatId) },
                onVideoClick = { uri -> navController.navigate("videoPlayer?uri=$uri") },
                prefilledText = text,
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable(
            route = "chat/{chatId}/camera",
            arguments = listOf(
                navArgument("chatId") { type = NavType.LongType },
            ),
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getLong("chatId") ?: 0L
            Camera(
                onMediaCaptured = { capturedMedia: Media? ->
                    when (capturedMedia?.mediaType) {
                        MediaType.PHOTO -> {
                            navController.popBackStack()
                        }

                        MediaType.VIDEO -> {
                            navController.navigate("videoEdit?uri=${capturedMedia.uri}&chatId=$chatId")
                        }

                        else -> {
                            // No media to use.
                            navController.popBackStack()
                        }
                    }
                },
                chatId = chatId,
                modifier = Modifier.fillMaxSize(),
            )
        }

        // Invoke PhotoPicker to select photo or video from device gallery
        photoPickerScreen(
            onPhotoPicked = navController::popBackStack,
        )

        composable(
            route = "videoEdit?uri={videoUri}&chatId={chatId}",
            arguments = listOf(
                navArgument("videoUri") { type = NavType.StringType },
                navArgument("chatId") { type = NavType.LongType },
            ),
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getLong("chatId") ?: 0L
            val videoUri = backStackEntry.arguments?.getString("videoUri") ?: ""
            VideoEditScreen(
                chatId = chatId,
                uri = videoUri,
                onCloseButtonClicked = { navController.popBackStack() },
                navController = navController,
            )
        }
        composable(
            route = "videoPlayer?uri={videoUri}",
            arguments = listOf(
                navArgument("videoUri") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val videoUri = backStackEntry.arguments?.getString("videoUri") ?: ""
            VideoPlayerScreen(
                uri = videoUri,
                onCloseButtonClicked = { navController.popBackStack() },
            )
        }



        //iraa navigation
        composable(ZepiDestinations.HOME_ROUTE) {
            HomeRoute(
                openDrawer = openDrawer,
                navigateSearch = navigateSearch,
                navigateUserProfile = navigateUserProfile,
                includeUserUidViewModel = includeUserUidViewModel,
            )
        }
        composable(ZepiDestinations.PROFILE_ROUTE) {
            ProfileRoute(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                profileToLogin = profileToLogin,
                profileToRegister = profileToRegister,
                navigateEdit = navigateEdit,
                showInterstialAd = showInterstitialAds
            ) }
        composable(ZepiDestinations.SETTINGS_ROUTE) {
            SettingsRoute(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                navigateEdit = navigateEdit,
                restartApp = restartApp,
            )
        }
        composable(ZepiDestinations.SUBSCRIPTIONS_ROUTE){
            SubscribeScreen()
        }

        composable(ZepiDestinations.LOG_IN_ROUTE) {
            LogInScreen(
                restartApp = restartApp,
                loginToRegister = loginToRegister,
                showInterstialAd = showInterstitialAds,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(ZepiDestinations.REGISTER_ROUTE) {
            RegisterScreen(
                navigateAndPopUpRegisterToEdit = navigateAndPopUpRegisterToEdit,
                registerToLogin = registerToLogin,
                showInterstitialAds = showInterstitialAds,
                onShowSnackbar = onShowSnackbar,
            )
        }
        composable(ZepiDestinations.EDIT_ROUTE){
            EditRoute(
                popUp = popUpScreen,
                restartApp = restartApp,
                onShowSnackbar = onShowSnackbar,
            )
        }
        composable(ZepiDestinations.SEARCH_ROUTE){
            SearchScreen(
                navigateAndPopUpSearchToUserProfile = navigateAndPopUpSearchToUserProfile,
                includeUserUidViewModel = includeUserUidViewModel
            )
        }
        composable(ZepiDestinations.USER_PROFILE_ROUTE){
            UserProfileRoute(
                popUpScreen = popUpScreen,
                onLoginClick = openLoginScreen,
                onChatExistClick = userProfileToChatExist,
                onChatNopeClick = userProfileToChatNope,
                showInterstitialAds = showInterstitialAds,
                includeUserUidViewModel = includeUserUidViewModel
            )
        }
    }

    if (shortcutParams != null) {
        val chatId = extractChatId(shortcutParams.shortcutId)
        val text = shortcutParams.text
        navController.navigate("chat/$chatId?text=$text")
    }
}

data class ShortcutParams(
    val shortcutId: String,
    val text: String,
)
















/**
const val POST_ID = "postId"

@SuppressLint("NewApi")
@Composable
fun (

) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        */






 /**       composable(QChatDestinations.SPLASH_ROUTE){
            SplashScreen(
                splashToHome = splashToHome,
                showInterstialAd = showInterstitialAds
            )
        }

        composable(QChatDestinations.ARCHIVE_ROUTE){
            ArchiveScreen(openChatExistScreen, includeChatViewModel = includeChatViewModel)
        }
        composable(QChatDestinations.CHAT_ROUTE_EXIST){
            ChatScreen(
                popUp = popUpScreen,
                navigateUserProfile = navigateUserProfile,
                includeUserUidViewModel = includeUserUidViewModel,
                includeChatViewModel = includeChatViewModel,
                showInterstialAd = showInterstitialAds)
        }
        composable(QChatDestinations.CHAT_ROUTE_NOPE){
            ChatScreenNope(
                popUp = popUpScreen,
                openAndPopUpChatNopeToExist = openAndPopUpChatNopeToExist,
                includeUserUidViewModel = includeUserUidViewModel,
                includeChatViewModel = includeChatViewModel,
                showInterstialAd = showInterstitialAds)
        }



        composable(QChatDestinations.CHATS_ROUTE){
            ChatsRoute(
                chatsToArchive = chatsToArchive,
                chatsToChat = chatsToChat,
                includeChatViewModel = includeChatViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }

*/

