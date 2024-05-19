/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.video_food_delivery_shopping.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object ZepiDestinations {
    const val SPLASH_ROUTE = "splash"
    const val LOG_IN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val EDIT_ROUTE = "edit"
    const val USER_PROFILE_ROUTE = "userProfile"
    const val SEARCH_ROUTE = "search"
    const val CHATNOPE_ROUTE = "chatnope"

    const val HOME_ROUTE = "home"
    const val TIMELINE_ROUTE = "timeline"
    const val CHATS_ROUTE = "chats"
    const val PROFILE_ROUTE = "profile"
    const val SETTINGS_ROUTE = "settings"
    const val SUBSCRIPTIONS_ROUTE = "subscriptions"

   /**
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

    val openAndPopUpSplashToHome: () -> Unit = {
        navController.navigate(ZepiDestinations.HOME_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.SPLASH_ROUTE){ inclusive = true }
        }
    }
    val openAndPopUpSplashToLogin: () -> Unit = {
        navController.navigate(ZepiDestinations.LOG_IN_ROUTE){
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
    val navigateAndPopUpRegisterToEdit: () -> Unit = {
        navController.navigate(ZepiDestinations.EDIT_ROUTE){
            launchSingleTop = true
            popUpTo(ZepiDestinations.REGISTER_ROUTE){ inclusive = true }
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


    val navigateToHome: () -> Unit = {
        navController.navigate(ZepiDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToTimeline: () -> Unit = {
        navController.navigate(ZepiDestinations.TIMELINE_ROUTE) {
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
