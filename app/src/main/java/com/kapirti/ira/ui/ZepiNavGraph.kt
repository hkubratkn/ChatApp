package com.kapirti.ira.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
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
import com.kapirti.ira.common.composable.AppNavRail
import com.kapirti.ira.core.viewmodel.IncludeChatViewModel
import com.kapirti.ira.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.ira.ui.presentation.edit.EditRoute
import com.kapirti.ira.ui.presentation.userprofile.UserProfileRoute
import com.kapirti.ira.ui.presentation.profile.ProfileRoute
import com.kapirti.ira.ui.presentation.settings.SettingsRoute
import com.kapirti.ira.ui.presentation.subscribe.SubscribeScreen
import com.kapirti.ira.soci.model.extractChatId
import com.kapirti.ira.soci.ui.camera.Camera
import com.kapirti.ira.soci.ui.camera.Media
import com.kapirti.ira.soci.ui.camera.MediaType
//import com.zepi.social_chat_food.soci.ui.home.Home
//import com.zepi.social_chat_food.soci.ui.photopicker.navigation.navigateToPhotoPicker
//import com.zepi.social_chat_food.soci.ui.photopicker.navigation.photoPickerScreen
import com.kapirti.ira.soci.ui.player.VideoPlayerScreen
import com.kapirti.ira.soci.ui.videoedit.VideoEditScreen
import com.kapirti.ira.ui.presentation.blockusers.BlockUsersRoute
import com.kapirti.ira.ui.presentation.chat.chatexist.ChatExistScreen
//import com.kapirti.video_food_delivery_shopping.ui.presentation.chat.chatexist.ChatScreen
import com.kapirti.ira.ui.presentation.chat.chatnope.ChatNopeScreen
import com.kapirti.ira.ui.presentation.chats.ChatsRoute
import com.kapirti.ira.ui.presentation.home.HomeRoute
import com.kapirti.ira.ui.presentation.login.LogInScreen
import com.kapirti.ira.ui.presentation.register.RegisterScreen
import com.kapirti.ira.ui.presentation.search.SearchScreen
import com.kapirti.ira.ui.presentation.splash.SplashScreen
import com.kapirti.ira.ui.presentation.timeline.Timeline


@Composable
fun ZepiNavGraph(
    restartApp : () -> Unit,
    popUpScreen: () -> Unit,

    openAndPopUpSplashToHome: () -> Unit,
    openAndPopUpSplashToLogin: () -> Unit,


    loginToRegister: () -> Unit,
    registerToLogin: () -> Unit,

    userProfileToChatNope: () -> Unit,

    openLoginScreen: () -> Unit,

    navigateEdit: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigateSearch: () -> Unit,
    navigateBlockUser: () -> Unit,

    navigateAndPopUpSearchToUserProfile: () -> Unit,
    navigateAndPopUpRegisterToEdit: () -> Unit,

    includeUserIdViewModel: IncludeUserIdViewModel,
    includeChatViewModel: IncludeChatViewModel,

    navigateChatsToChatExist: () -> Unit,

    onShowSnackbar: suspend (String, String?) -> Boolean,
    showInterstitialAds: () -> Unit,
    isExpandedScreen: Boolean,

    openDrawer: () -> Unit = {},
    closeDrawer: () -> Unit = {},
    sizeAwareDrawerState: DrawerState,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ZepiDestinations.SPLASH_ROUTE,
    currentRoute: String,


    navigateToSubscriptions: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToChats: () -> Unit,
    navigateToTimeline: () -> Unit,
    navigateToHome: () -> Unit,

    hasUser: Boolean,
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





    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToHome = navigateToHome,
                navigateToTimeline = navigateToTimeline,
                navigateToChats = navigateToChats,
                navigateToProfile = navigateToProfile,
                navigateToSettings = navigateToSettings,
                navigateToSubscriptions = navigateToSubscriptions,
                closeDrawer = closeDrawer,
                hasUser = hasUser,
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
                    navigateToSettings = navigateToSettings,
                )
            }

            NavHost(
                navController = navController,
                startDestination = ZepiDestinations.SPLASH_ROUTE,
                modifier = modifier,
            ) {
                composable(ZepiDestinations.HOME_ROUTE) {
                    HomeRoute(
                        openDrawer = openDrawer,
                        navigateSearch = navigateSearch,
                        navigateUserProfile = navigateUserProfile,
                        includeUserIdViewModel = includeUserIdViewModel,
                    )
                }
                composable(
                    route = ZepiDestinations.TIMELINE_ROUTE,
                ) {
                    Timeline(
                        modifier = modifier,
                    )
                }
                composable(
                    route = ZepiDestinations.CHATS_ROUTE//"home",
                ) {
                    ChatsRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                     //   modifier = Modifier.fillMaxSize(),
                       // includeChatViewModel = includeChatViewModel,
                       // navigateChatsToChatExist = navigateChatsToChatExist
                    )
                }
                composable(ZepiDestinations.PROFILE_ROUTE) {
                    ProfileRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        navigateEdit = navigateEdit,
                        showInterstialAd = showInterstitialAds
                    )
                }
                composable(ZepiDestinations.SETTINGS_ROUTE) {
                    SettingsRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        navigateEdit = navigateEdit,
                        restartApp = restartApp,
                        navigateBlockUser = navigateBlockUser
                    )
                }
                composable(ZepiDestinations.SUBSCRIPTIONS_ROUTE) {
                    SubscribeScreen()
                }


                /**   composable(
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
                ChatScreen(
                chatId = chatId,
                foreground = true,
                onBackPressed = { navController.popBackStack() },
                onCameraClick = { navController.navigate("chat/$chatId/camera") },
                onPhotoPickerClick = {},//{ navController.navigateToPhotoPicker(chatId) },
                onVideoClick = { uri -> navController.navigate("videoPlayer?uri=$uri") },
                prefilledText = text,
                modifier = Modifier.fillMaxSize(),
                )
                }*/
                /**   composable(
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
                ChatScreen(
                chatId = chatId,
                foreground = true,
                onBackPressed = { navController.popBackStack() },
                onCameraClick = { navController.navigate("chat/$chatId/camera") },
                onPhotoPickerClick = {},//{ navController.navigateToPhotoPicker(chatId) },
                onVideoClick = { uri -> navController.navigate("videoPlayer?uri=$uri") },
                prefilledText = text,
                modifier = Modifier.fillMaxSize(),
                )
                }*/
                /**   composable(
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
                ChatScreen(
                chatId = chatId,
                foreground = true,
                onBackPressed = { navController.popBackStack() },
                onCameraClick = { navController.navigate("chat/$chatId/camera") },
                onPhotoPickerClick = {},//{ navController.navigateToPhotoPicker(chatId) },
                onVideoClick = { uri -> navController.navigate("videoPlayer?uri=$uri") },
                prefilledText = text,
                modifier = Modifier.fillMaxSize(),
                )
                }*/

                /**   composable(
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
                ChatScreen(
                chatId = chatId,
                foreground = true,
                onBackPressed = { navController.popBackStack() },
                onCameraClick = { navController.navigate("chat/$chatId/camera") },
                onPhotoPickerClick = {},//{ navController.navigateToPhotoPicker(chatId) },
                onVideoClick = { uri -> navController.navigate("videoPlayer?uri=$uri") },
                prefilledText = text,
                modifier = Modifier.fillMaxSize(),
                )
                }*/
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
                /**        photoPickerScreen(
                onPhotoPicked = navController::popBackStack,
                )*/
                /**        photoPickerScreen(
                onPhotoPicked = navController::popBackStack,
                )*/
                /**        photoPickerScreen(
                onPhotoPicked = navController::popBackStack,
                )*/
                /**        photoPickerScreen(
                onPhotoPicked = navController::popBackStack,
                )*/

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



                composable(ZepiDestinations.SPLASH_ROUTE) {
                    SplashScreen(
                        openAndPopUpSplashToHome = openAndPopUpSplashToHome,
                        openAndPopUpSplashToLogin = openAndPopUpSplashToLogin,
                        showInterstialAd = showInterstitialAds
                    )
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
                composable(ZepiDestinations.EDIT_ROUTE) {
                    EditRoute(
                        popUp = popUpScreen,
                        restartApp = restartApp,
                        onShowSnackbar = onShowSnackbar,
                    )
                }
                composable(ZepiDestinations.SEARCH_ROUTE) {
                    SearchScreen(
                        navigateAndPopUpSearchToUserProfile = navigateAndPopUpSearchToUserProfile,
                        includeUserIdViewModel = includeUserIdViewModel
                    )
                }
                composable(ZepiDestinations.USER_PROFILE_ROUTE) {
                    UserProfileRoute(
                        popUpScreen = popUpScreen,
                        onLoginClick = openLoginScreen,
                        onChatExistClick = {}, //userProfileToChatExist,
                        onChatNopeClick = userProfileToChatNope,
                        showInterstitialAds = showInterstitialAds,
                        includeUserIdViewModel = includeUserIdViewModel
                    )
                }
                composable(ZepiDestinations.CHATNOPE_ROUTE) {
                    ChatNopeScreen(
                        popUp = popUpScreen,
                        openAndPopUpChatNopeToExist = {},//openAndPopUpChatNopeToExist,
                        includeUserIdViewModel = includeUserIdViewModel,
                        showInterstialAd = showInterstitialAds,
                        //userUid = userId,
                        foreground = true,
                        onCameraClick = {}, //{ navController.navigate("chat/$chatId/camera") },
                        onPhotoPickerClick = {},//{ navController.navigateToPhotoPicker(chatId) },
                        onVideoClick = { uri -> navController.navigate("videoPlayer?uri=$uri") },
                        prefilledText = "",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                composable(
                    route = ZepiDestinations.CHATEXIST_ROUTE
                ) {
                    ChatExistScreen(
                        foreground = true,
                        onCameraClick = {}, //{ navController.navigate("chat/$chatId/camera") },
                        onPhotoPickerClick = {},//{ navController.navigateToPhotoPicker(chatId) },
                        onVideoClick = { uri -> navController.navigate("videoPlayer?uri=$uri") },
                        prefilledText = "",
                        popUp = popUpScreen,
                        navigateUserProfile = navigateUserProfile,
                        includeChatViewModel = includeChatViewModel,
                        includeUserIdViewModel = includeUserIdViewModel,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                composable(
                    route = ZepiDestinations.BLOCK_USERS_ROUTE
                ) {
                    BlockUsersRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        )
                }



     /**           composable(
     route = "chat/{chatId}?text={text}",
     arguments = listOf(
     navArgument("chatId") { type = NavType.LongType },
     //                navArgument("userId") { type = NavType.StringType },
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
     //  val userId = backStackEntry.arguments?.getString("userId") ?: "userid"
     val text = backStackEntry.arguments?.getString("text")
     ChatScreen(
     chatId = chatId,
     //userUid = userId,
     foreground = true,
     onBackPressed = { navController.popBackStack() },
     onCameraClick = { navController.navigate("chat/$chatId/camera") },
     onPhotoPickerClick = {},//{ navController.navigateToPhotoPicker(chatId) },
     onVideoClick = { uri -> navController.navigate("videoPlayer?uri=$uri") },
     prefilledText = text,
     modifier = Modifier.fillMaxSize(),
     )
     }*/
            }
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


 /**

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

