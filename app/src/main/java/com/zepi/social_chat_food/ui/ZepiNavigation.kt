package com.zepi.social_chat_food.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object ZepiDestinations {
    const val SPLASH_ROUTE = "splash"
    const val LOG_IN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val EDIT_ROUTE = "edit"
    const val USER_PROFILE_ROUTE = "userProfile"
    const val SEARCH_ROUTE = "search"
    const val CHAT_ROUTE_EXIST = "chatExist"
    const val CHAT_ROUTE_NOPE = "chatNope"
    const val ARCHIVE_ROUTE = "archive"

    const val HOME_ROUTE = "home"
    const val CHATS_ROUTE = "chats"
    const val PROFILE_ROUTE = "profile"
    const val SETTINGS_ROUTE = "settings"
    const val SUBSCRIPTIONS_ROUTE = "subscriptions"

   /**
    const val EDIT_ROUTE = "edit"
    const val ASSET_DETAIL_SCREEN = "assetDetail"

    const val INTEREST_ROUTE = "interest"
    const val ADD_ROUTE = "add"

    const val WELCOME_SCREEN = "WelcomeScreen"

    */
}


class QChatNavigationActions(navController: NavHostController) {
    val clearAndNavigate: () -> Unit = {
        navController.navigate(ZepiDestinations.SPLASH_ROUTE){
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
    val popUp: () -> Unit = {
        navController.popBackStack()
    }

    val navigateAndPopUpSplashToHome: () -> Unit = {
        navController.navigate(ZepiDestinations.HOME_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.SPLASH_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpLoginToRegister: () -> Unit = {
        navController.navigate(ZepiDestinations.REGISTER_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.LOG_IN_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpRegisterToLogin: () -> Unit = {
        navController.navigate(ZepiDestinations.LOG_IN_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.REGISTER_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpUserProfileToChatExist: () -> Unit = {
        navController.navigate(ZepiDestinations.CHAT_ROUTE_EXIST){
            launchSingleTop = true
            popUpTo(ZepiDestinations.USER_PROFILE_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpRegisterToEdit: () -> Unit = {
        navController.navigate(ZepiDestinations.EDIT_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.REGISTER_ROUTE){ inclusive = true }
        }
    }


    val navigateAndPopUpUserProfileToChatNope: () -> Unit = {
        navController.navigate(ZepiDestinations.CHAT_ROUTE_NOPE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.USER_PROFILE_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpSearchToUserProfile: () -> Unit = {
        navController.navigate(ZepiDestinations.USER_PROFILE_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.SEARCH_ROUTE){ inclusive = true }
        }
    }
    val openAndPopUpChatNopeToExist: () -> Unit = {
        navController.navigate(ZepiDestinations.CHAT_ROUTE_EXIST){
            launchSingleTop = true
            popUpTo(ZepiDestinations.CHAT_ROUTE_NOPE){ inclusive = true }
        }
    }



    val navigateLogin: () -> Unit = {
        navController.navigate(ZepiDestinations.LOG_IN_ROUTE){ launchSingleTop = true }
    }
    val navigateRegister: () -> Unit = {
        navController.navigate(ZepiDestinations.REGISTER_ROUTE){ launchSingleTop = true }
    }
    val navigateEdit: () -> Unit = {
        navController.navigate(ZepiDestinations.EDIT_ROUTE){ launchSingleTop = true }
    }
    val navigateUserProfile: () -> Unit = {
        navController.navigate(ZepiDestinations.USER_PROFILE_ROUTE){ launchSingleTop = true }
    }
    val navigateSearch: () -> Unit = {
        navController.navigate(ZepiDestinations.SEARCH_ROUTE){ launchSingleTop = true }
    }
    val navigateArchive: () -> Unit = {
        navController.navigate(ZepiDestinations.ARCHIVE_ROUTE){ launchSingleTop = true }
    }
    val navigateChatExist: () -> Unit = {
        navController.navigate(ZepiDestinations.CHAT_ROUTE_EXIST){ launchSingleTop = true }
    }


    val navigateToHome: () -> Unit = {
        navController.navigate(ZepiDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToChats: () -> Unit = {
        navController.navigate(ZepiDestinations.CHATS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToProfile: () -> Unit = {
        navController.navigate(ZepiDestinations.PROFILE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(ZepiDestinations.SETTINGS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSubscriptions: () -> Unit = {
        navController.navigate(ZepiDestinations.SUBSCRIPTIONS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
