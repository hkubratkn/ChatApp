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
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeChatViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeJobViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.blockedusers.BlockedUsersRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.chats.ChatsRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit.EditRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.employee.EmployeeScreen
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.home.HomeRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.job.JobRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.login.LogInScreen
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.productivity.ProductivityRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.profile.ProfileRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.register.RegisterScreen
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.settings.SettingsRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.splash.SplashScreen
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.subscribe.SubscribeScreen
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.timeline.TimelineRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.timeover.TimeOverScreen
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.userprofile.UserProfileRoute
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.welcome.WelcomeRoute


@Composable
fun PomodoroNavGraph(
    sizeAwareDrawerState: DrawerState,
    currentRoute: String,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    closeDrawer: () -> Unit,

    restartApp : () -> Unit,
    popUpScreen: () -> Unit,

    navigateLogin: () -> Unit,
    navigateRegister: () -> Unit,
    navigateToProductivity: () -> Unit,
    navigateToTimeline: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToJob: () -> Unit,
    navigateToEmployee: () -> Unit,
    navigateToChats: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToSubscriptions: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigateBlockedUser: () -> Unit,

    navigateAndPopUpSplashToTimeline: () -> Unit,
    navigateAndPopUpSplashToWelcome: () -> Unit,
    navigateAndPopUpWelcomeToTimeline: () -> Unit,
    navigateAndPopUpLoginToRegister: () -> Unit,
    navigateAndPopUpRegisterToLogin: () -> Unit,
    navigateAndPopUpRegisterToEdit: () -> Unit,

    navigateEdit: () -> Unit,
    navigateTimeOver: () -> Unit,
    navigateJobDetail: () -> Unit,

    includeUserIdViewModel: IncludeUserIdViewModel,
    includeChatViewModel: IncludeChatViewModel,
    includeJobViewModel: IncludeJobViewModel,

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
                navigateToProductivity = navigateToProductivity,
                navigateToTimeline = navigateToTimeline,
                navigateToHome = navigateToHome,
                navigateToJob = navigateToJob,
                navigateToEmployee = navigateToEmployee,
                navigateToChats = navigateToChats,
                navigateToProfile = navigateToProfile,
                navigateToSettings = navigateToSettings,
                navigateToSubscriptions = navigateToSubscriptions,
                closeDrawer = closeDrawer,
            )
        },
        drawerState = sizeAwareDrawerState,
        gesturesEnabled = !isExpandedScreen
    ) {
        Row {
            if (isExpandedScreen) {
                AppNavRail(
                    currentRoute = currentRoute,
                    navigateToProductivity = navigateToProductivity,
                    navigateToSettings = navigateToSettings,
                )
            }

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier,
            ) {
                composable(route = PomodoroDestinations.TIMELINE_ROUTE,) {
                    TimelineRoute(
                        openDrawer = openDrawer,
                        includeUserIdViewModel = includeUserIdViewModel,
                        isExpandedScreen = isExpandedScreen,
                        navigateLogin = navigateLogin,
                        navigateEdit = navigateEdit,
                        navigateUserProfile = navigateUserProfile,
                    )
                }
                composable(route = PomodoroDestinations.HOME_ROUTE,) {
                    HomeRoute(
                    )
                }
                composable(PomodoroDestinations.PRODUCTIVITY_ROUTE) {
                    ProductivityRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        navigateTimeOver = navigateTimeOver,
                        navigateToProductivity = navigateToProductivity,
                    )
                }
                composable(PomodoroDestinations.JOB_ROUTE){
                    JobRoute(
                        isExpandedScreen = isExpandedScreen,
                        includeJobViewModel = includeJobViewModel,
                        openDrawer = openDrawer,
                        navigateEdit = navigateEdit,
                        navigateJobDetail = navigateJobDetail,
                    )
                }
                composable(PomodoroDestinations.EMPLOYEE_ROUTE){ EmployeeScreen() }
                composable(PomodoroDestinations.CHATS_ROUTE){
                    ChatsRoute(
                        includeChatViewModel = includeChatViewModel,
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        navigateLogin = navigateLogin,
                        navigateRegister = navigateRegister,
                        navigateChatsToChatExist = {}, //navigateChatsToChatExist,
                    )
                }
                composable(PomodoroDestinations.PROFILE_ROUTE) {
                    ProfileRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        navigateEdit = navigateEdit,
                        navigateRegister = navigateRegister,
                        navigateLogin = navigateLogin
                    )
                }
                composable(PomodoroDestinations.SETTINGS_ROUTE) {
                    SettingsRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                        navigateEdit = navigateEdit,
                        restartApp = restartApp,
                        navigateRegister = navigateRegister,
                        navigateLogin = navigateLogin,
                        navigateBlockedUser = navigateBlockedUser,
                    )
                }
                composable(PomodoroDestinations.SUBSCRIPTIONS_ROUTE) {
                    SubscribeScreen()
                }


                composable(PomodoroDestinations.SPLASH_ROUTE) {
                    SplashScreen(
                        navigateAndPopUpSplashToTimeline = navigateAndPopUpSplashToTimeline,
                        navigateAndPopUpSplashToWelcome = navigateAndPopUpSplashToWelcome,
                        )
                }
                composable(PomodoroDestinations.WELCOME_ROUTE) {
                    WelcomeRoute(
                        navigateAndPopUpWelcomeToTimeline = navigateAndPopUpWelcomeToTimeline,
                    )
                }
                composable(PomodoroDestinations.LOG_IN_ROUTE) {
                    LogInScreen(
                        restartApp = restartApp,
                        navigateAndPopUpLoginToRegister = navigateAndPopUpLoginToRegister,
                    )
                }
                composable(PomodoroDestinations.REGISTER_ROUTE) {
                    RegisterScreen(
                        navigateAndPopUpRegisterToEdit = navigateAndPopUpRegisterToEdit,
                        navigateAndPopUpRegisterToLogin = navigateAndPopUpRegisterToLogin,
                    )
                }
                composable(PomodoroDestinations.EDIT_ROUTE) {
                    EditRoute(
                        popUp = popUpScreen,
                        restartApp = restartApp,
                    )
                }
                composable(PomodoroDestinations.TIME_OVER_ROUTE){
                    TimeOverScreen(
                        openDrawer = openDrawer,
                        navigateToProductivity = navigateToProductivity,
                    )
                }
                composable(PomodoroDestinations.USER_PROFILE_ROUTE) {
                    UserProfileRoute(
                        popUpScreen = popUpScreen,
                        navigateLogin = navigateLogin,
                        onChatExistClick = {}, //userProfileToChatExist,
                        onChatNopeClick = {}, //userProfileToChatNope,
                        navigatePhotos = {}, //navigatePhotos,
                        navigateVideoCall = {}, //navigateVideoCall,
                        includeUserIdViewModel = includeUserIdViewModel
                    )
                }
                composable(
                    route = PomodoroDestinations.BLOCKED_USERS_ROUTE
                ) {
                    BlockedUsersRoute(
                        isExpandedScreen = isExpandedScreen,
                        openDrawer = openDrawer,
                    )
                }
            }
        }
    }
}
 /**

    openAndPopUpChatNopeToExist: () -> Unit,

    userProfileToChatNope: () -> Unit,

    openLoginScreen: () -> Unit,

    navigateSearch: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigatePhotos: () -> Unit,
    navigateVideoCall: () -> Unit,
    navigateBlockUser: () -> Unit,

    navigateAndPopUpSearchToUserProfile: () -> Unit,


    navigateChatsToChatExist: () -> Unit,



    navigateToTimeline: () -> Unit,

    shortcutParams: ShortcutParams?,
) {


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




                composable(ZepiDestinations.SEARCH_ROUTE) {
                    SearchScreen(
                        navigateAndPopUpSearchToUserProfile = navigateAndPopUpSearchToUserProfile,
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


*/

*/
