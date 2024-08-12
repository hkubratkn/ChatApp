package com.kapirti.pomodorotechnique_timemanagementmethod.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object PomodoroDestinations {
    const val SPLASH_ROUTE = "splash"
    const val LOG_IN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val EDIT_ROUTE = "edit"
    const val TIME_OVER_ROUTE = "timeOver"
    const val JOB_DETAIL_ROUTE = "jobDetail"
    const val BLOCKED_USERS_ROUTE = "blockedUsers"

    const val TIMELINE_ROUTE = "timeline"
    const val HOME_ROUTE = "home"
    const val JOB_ROUTE = "job"
    const val EMPLOYEE_ROUTE = "employee"
    const val PRODUCTIVITY_ROUTE = "productivity"
    const val CHATS_ROUTE = "chats"
    const val PROFILE_ROUTE = "profile"
    const val SETTINGS_ROUTE = "settings"
    const val SUBSCRIPTIONS_ROUTE = "subscriptions"
    const val USER_PROFILE_ROUTE = "userProfile"
}


class PomodoroNavigationActions(navController: NavHostController) {
    val clearAndNavigate: () -> Unit = {
        navController.navigate(PomodoroDestinations.SPLASH_ROUTE){
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
    val popUp: () -> Unit = {
        navController.popBackStack()
    }


    val navigateToTimeline: () -> Unit = {
        navController.navigate(PomodoroDestinations.TIMELINE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToHome: () -> Unit = {
        navController.navigate(PomodoroDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToProductivity: () -> Unit = {
        navController.navigate(PomodoroDestinations.PRODUCTIVITY_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToJob: () -> Unit = {
        navController.navigate(PomodoroDestinations.JOB_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToEmployee: () -> Unit = {
        navController.navigate(PomodoroDestinations.EMPLOYEE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToChats: () -> Unit = {
        navController.navigate(PomodoroDestinations.CHATS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToProfile: () -> Unit = {
        navController.navigate(PomodoroDestinations.PROFILE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(PomodoroDestinations.SETTINGS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSubscriptions: () -> Unit = {
        navController.navigate(PomodoroDestinations.SUBSCRIPTIONS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateLogin: () -> Unit = {
        navController.navigate(PomodoroDestinations.LOG_IN_ROUTE){ launchSingleTop = true }
    }
    val navigateRegister: () -> Unit = {
        navController.navigate(PomodoroDestinations.REGISTER_ROUTE){ launchSingleTop = true }
    }
    val navigateEdit: () -> Unit = {
        navController.navigate(PomodoroDestinations.EDIT_ROUTE){ launchSingleTop = true }
    }
    val navigateTimeOver: () -> Unit = {
        navController.navigate(PomodoroDestinations.TIME_OVER_ROUTE){ launchSingleTop = true }
    }
    val navigateJobDetail: () -> Unit = {
        navController.navigate(PomodoroDestinations.JOB_DETAIL_ROUTE){ launchSingleTop = true }
    }
    val navigateUserProfile: () -> Unit = {
        navController.navigate(PomodoroDestinations.USER_PROFILE_ROUTE){ launchSingleTop = true }
    }
    val navigateBlockedUsers: () -> Unit = {
        navController.navigate(PomodoroDestinations.BLOCKED_USERS_ROUTE){ launchSingleTop = true }
    }



    val navigateAndPopUpSplashToTimeline: () -> Unit = {
        navController.navigate(PomodoroDestinations.TIMELINE_ROUTE){
            launchSingleTop = true
            popUpTo(PomodoroDestinations.SPLASH_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpLoginToRegister: () -> Unit = {
        navController.navigate(PomodoroDestinations.REGISTER_ROUTE){
            launchSingleTop = true
            popUpTo(PomodoroDestinations.LOG_IN_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpRegisterToLogin: () -> Unit = {
        navController.navigate(PomodoroDestinations.LOG_IN_ROUTE){
            launchSingleTop = true
            popUpTo(PomodoroDestinations.REGISTER_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpRegisterToEdit: () -> Unit = {
        navController.navigate(PomodoroDestinations.EDIT_ROUTE){
            launchSingleTop = true
            popUpTo(PomodoroDestinations.REGISTER_ROUTE){ inclusive = true }
        }
    }
}

/**

    const val SEARCH_ROUTE = "search"
    const val CHATNOPE_ROUTE = "chatnope"
    const val CHATEXIST_ROUTE = "chatexist"
    const val VIDEO_CALL_ROUTE = "videoCall"
    const val PHOTOS_ROUTE = "photos"

    const val HOME_ROUTE = "home"

   /**
    const val ASSET_DETAIL_SCREEN = "assetDetail"

    const val INTEREST_ROUTE = "interest"
    const val ADD_ROUTE = "add"

    const val WELCOME_SCREEN = "WelcomeScreen"
    */
}




    val openAndPopUpChatNopeToExist: () -> Unit = {
        navController.navigate(ZepiDestinations.CHATEXIST_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.CHATNOPE_ROUTE){ inclusive = true }
        }
    }


    val navigateAndPopUpSearchToUserProfile: () -> Unit = {
        navController.navigate(ZepiDestinations.USER_PROFILE_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.SEARCH_ROUTE){ inclusive = true }
        }
    }
    val navigateAndPopUpUserProfileToChatNope: () -> Unit = {
        navController.navigate(ZepiDestinations.CHATNOPE_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.USER_PROFILE_ROUTE){ inclusive = true }
        }
    }


    val navigateRegister: () -> Unit = {
        navController.navigate(ZepiDestinations.REGISTER_ROUTE){ launchSingleTop = true }
    }

    val navigateSearch: () -> Unit = {
        navController.navigate(ZepiDestinations.SEARCH_ROUTE){ launchSingleTop = true }
    }

    val navigatePhotos: () -> Unit = {
        navController.navigate(ZepiDestinations.PHOTOS_ROUTE){ launchSingleTop = true }
    }
    val navigateChatExist: () -> Unit = {
        navController.navigate(ZepiDestinations.CHATEXIST_ROUTE){ launchSingleTop = true }
    }
    val navigateVideoCall: () -> Unit = {
        navController.navigate(ZepiDestinations.VIDEO_CALL_ROUTE){ launchSingleTop = true }
    }
    val navigateBlockUser: () -> Unit = {
        navController.navigate(ZepiDestinations.BLOCK_USERS_ROUTE){ launchSingleTop = true }
    }

}
*/
