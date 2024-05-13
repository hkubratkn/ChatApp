package com.zepi.social_chat_food.soci.ui.photopicker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavController.navigateToPhotoPicker(chatId: Long, navOptions: NavOptions? = null) {
    this.navigate("chat/$chatId/photoPicker", navOptions)
}

fun NavGraphBuilder.photoPickerScreen(
    onPhotoPicked: () -> Unit,
) {
    composable(
        route = "chat/{chatId}/photoPicker",
        arguments = listOf(
            navArgument("chatId") { type = NavType.LongType },
        ),
    ) {
        PhotoPickerRoute(
            onPhotoPicked = onPhotoPicked,
        )
    }
}
