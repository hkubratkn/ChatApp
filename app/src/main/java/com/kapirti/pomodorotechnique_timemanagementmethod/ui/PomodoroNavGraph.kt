package com.kapirti.pomodorotechnique_timemanagementmethod.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AppNavRail
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.pomodoro.PomodoroRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.settings.SettingsRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.splash.SplashScreen


@Composable
fun ZepiNavGraph(
    hasUser: Boolean,
    sizeAwareDrawerState: DrawerState,
    currentRoute: String,
    isExpandedScreen: Boolean,
    showInterstitialAds: () -> Unit,
    openDrawer: () -> Unit,
    closeDrawer: () -> Unit,

    navigateToPomodoro: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToSubscriptions: () -> Unit,

    openAndPopUpSplashToPomodoro: () -> Unit,
    openAndPopUpSplashToLogin: () -> Unit,

    navController: NavHostController = rememberNavController(),
    startDestination: String = PomodoroDestinations.SPLASH_ROUTE,
    modifier: Modifier = Modifier,
) {
    val activity = LocalContext.current as Activity

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
                navigateToPomodoro = navigateToPomodoro,
               // navigateToTimeline = navigateToTimeline,
               // navigateToChats = navigateToChats,
               // navigateToProfile = navigateToProfile,
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
                    navigateToPomodoro = navigateToPomodoro,
                    navigateToSettings = navigateToSettings,
                )
            }

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier,
            ) {
                composable(PomodoroDestinations.POMODORO_ROUTE) {
                    PomodoroRoute(
                        openDrawer = openDrawer,
                        navigateSearch = {}
                    )
                }
                composable(PomodoroDestinations.SETTINGS_ROUTE) {
                    SettingsRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        navigateEdit = {}, //navigateEdit,
                        restartApp = {}, //restartApp,
                        navigateBlockUser = {}, //navigateBlockUser
                    )
                }

                composable(PomodoroDestinations.SPLASH_ROUTE) {
                    SplashScreen(
                        openAndPopUpSplashToPomodoro = openAndPopUpSplashToPomodoro,
                        openAndPopUpSplashToLogin = openAndPopUpSplashToLogin,
                        showInterstialAd = showInterstitialAds
                    )
                }
            }
        }
    }
}
 /**   restartApp : () -> Unit,
    popUpScreen: () -> Unit,

    openAndPopUpChatNopeToExist: () -> Unit,

    loginToRegister: () -> Unit,
    registerToLogin: () -> Unit,

    userProfileToChatNope: () -> Unit,

    openLoginScreen: () -> Unit,

    navigateEdit: () -> Unit,
    navigateSearch: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigatePhotos: () -> Unit,
    navigateVideoCall: () -> Unit,
    navigateBlockUser: () -> Unit,

    navigateAndPopUpSearchToUserProfile: () -> Unit,
    navigateAndPopUpRegisterToEdit: () -> Unit,

    includeUserIdViewModel: IncludeUserIdViewModel,
    includeChatViewModel: IncludeChatViewModel,

    navigateChatsToChatExist: () -> Unit,



    navigateToProfile: () -> Unit,
    navigateToChats: () -> Unit,
    navigateToTimeline: () -> Unit,

    shortcutParams: ShortcutParams?,
) {

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
                        modifier = Modifier.fillMaxSize(),
                        includeChatViewModel = includeChatViewModel,
                        navigateChatsToChatExist = navigateChatsToChatExist
                    )
                }
                composable(ZepiDestinations.PROFILE_ROUTE) {
                    ProfileRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        navigateEdit = navigateEdit,
                        navigatePhotos = navigatePhotos,
                        showInterstialAd = showInterstitialAds,
                        includeUserIdViewModel = includeUserIdViewModel,
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




                composable(ZepiDestinations.LOG_IN_ROUTE) {
                    LogInScreen(
                        restartApp = restartApp,
                        loginToRegister = loginToRegister,
                        showInterstialAd = showInterstitialAds,
                    )
                }
                composable(ZepiDestinations.REGISTER_ROUTE) {
                    RegisterScreen(
                        navigateAndPopUpRegisterToEdit = navigateAndPopUpRegisterToEdit,
                        registerToLogin = registerToLogin,
                        showInterstitialAds = showInterstitialAds,
                    )
                }
                composable(ZepiDestinations.EDIT_ROUTE) {
                    EditRoute(
                        popUp = popUpScreen,
                        restartApp = restartApp,
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
                        navigatePhotos = navigatePhotos,
                        navigateVideoCall = navigateVideoCall,
                        showInterstitialAds = showInterstitialAds,
                        includeUserIdViewModel = includeUserIdViewModel
                    )
                }
                composable(ZepiDestinations.CHATNOPE_ROUTE) {
                    ChatNopeScreen(
                        popUp = popUpScreen,
                        openAndPopUpChatNopeToExist = openAndPopUpChatNopeToExist,
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
                composable(ZepiDestinations.VIDEO_CALL_ROUTE) { VideoCallScreen(popUp = popUpScreen) }
                composable(ZepiDestinations.PHOTOS_ROUTE){ PhotosRoute(
                    popUp = popUpScreen,
                    navigateEdit = navigateEdit,
                    isExpandedScreen = isExpandedScreen,
                    includeUserIdViewModel = includeUserIdViewModel,
                ) }
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

*/
