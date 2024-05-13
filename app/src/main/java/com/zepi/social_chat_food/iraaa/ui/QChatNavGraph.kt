package com.zepi.social_chat_food.iraaa.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeChatViewModel
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeUserUidViewModel
import com.zepi.social_chat_food.iraaa.ui.presentation.archive.ArchiveScreen
import com.zepi.social_chat_food.iraaa.ui.presentation.chat.ChatScreen
import com.zepi.social_chat_food.iraaa.ui.presentation.chat.nope.ChatScreenNope
import com.zepi.social_chat_food.iraaa.ui.presentation.chats.ChatsRoute
import com.zepi.social_chat_food.iraaa.ui.presentation.edit.EditRoute
import com.zepi.social_chat_food.iraaa.ui.presentation.home.HomeRoute
import com.zepi.social_chat_food.iraaa.ui.presentation.login.LogInScreen
import com.zepi.social_chat_food.iraaa.ui.presentation.profile.ProfileRoute
import com.zepi.social_chat_food.iraaa.ui.presentation.register.RegisterScreen
import com.zepi.social_chat_food.iraaa.ui.presentation.search.SearchScreen
import com.zepi.social_chat_food.iraaa.ui.presentation.settings.SettingsRoute
import com.zepi.social_chat_food.iraaa.ui.presentation.splash.SplashScreen
import com.zepi.social_chat_food.iraaa.ui.presentation.subscribe.SubscribeScreen
import com.zepi.social_chat_food.iraaa.ui.presentation.userprofile.UserProfileRoute

const val POST_ID = "postId"

@SuppressLint("NewApi")
@Composable
fun QChatNavGraph(
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
    openAndPopUpChatNopeToExist: () -> Unit,

    includeChatViewModel: IncludeChatViewModel,
    includeUserUidViewModel: IncludeUserUidViewModel,

    openDrawer: () -> Unit = {},
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = QChatDestinations.SPLASH_ROUTE,

    onShowSnackbar: suspend (String, String?) -> Boolean,
    showInterstitialAds: () -> Unit,
    isExpandedScreen: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(QChatDestinations.SPLASH_ROUTE){
            SplashScreen(
                splashToHome = splashToHome,
                showInterstialAd = showInterstitialAds
            )
        }
        composable(QChatDestinations.LOG_IN_ROUTE) {
            LogInScreen(
                restartApp = restartApp,
                loginToRegister = loginToRegister,
                showInterstialAd = showInterstitialAds,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(QChatDestinations.REGISTER_ROUTE) {
            RegisterScreen(
                registerToLogin = registerToLogin,
                showInterstitialAd = showInterstitialAds
            )
        }
        composable(QChatDestinations.EDIT_ROUTE){
            EditRoute(
                popUp = popUpScreen,
                restartApp = restartApp,
                onShowSnackbar = onShowSnackbar,
            )
        }
        composable(QChatDestinations.USER_PROFILE_ROUTE){
            UserProfileRoute(
                popUpScreen = popUpScreen,
                onLoginClick = openLoginScreen,
                onChatExistClick = userProfileToChatExist,
                onChatNopeClick = userProfileToChatNope,
                showInterstitialAds = showInterstitialAds,
                includeUserUidViewModel = includeUserUidViewModel
            )
        }
        composable(QChatDestinations.SEARCH_ROUTE){
            SearchScreen(
                navigateAndPopUpSearchToUserProfile = navigateAndPopUpSearchToUserProfile,
                includeUserUidViewModel = includeUserUidViewModel
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


        composable(QChatDestinations.HOME_ROUTE) {
            HomeRoute(
                openDrawer = openDrawer,
                navigateSearch = navigateSearch,
                navigateUserProfile = navigateUserProfile,
                includeUserUidViewModel = includeUserUidViewModel,
            )
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
        composable(QChatDestinations.PROFILE_ROUTE) {
            ProfileRoute(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                profileToLogin = profileToLogin,
                profileToRegister = profileToRegister,
                navigateEdit = navigateEdit,
                showInterstialAd = showInterstitialAds
            ) }
        composable(QChatDestinations.SETTINGS_ROUTE) {
            SettingsRoute(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                navigateEdit = navigateEdit,
                restartApp = restartApp,
            )
        }
        composable(QChatDestinations.SUBSCRIPTIONS_ROUTE){
            SubscribeScreen()
        }
    }
}
