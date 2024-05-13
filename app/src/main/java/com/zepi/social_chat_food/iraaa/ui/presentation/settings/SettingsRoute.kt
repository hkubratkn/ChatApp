package com.zepi.social_chat_food.iraaa.ui.presentation.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.common.composable.AdsBannerToolbar
import com.zepi.social_chat_food.iraaa.common.composable.MenuToolbar
import com.zepi.social_chat_food.iraaa.core.constants.ConsAds

@Composable
fun SettingsRoute(
    navigateEdit: () -> Unit,
    restartApp: () -> Unit,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ConsAds.ADS_SETTINGS_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = AppText.settings_title,
                actionsIcon = Icons.Default.Stars,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
    ) { innerPadding ->
        SettingsScreen(
            navigateEdit = navigateEdit,
            restartApp = restartApp,
            modifier = modifier.padding(innerPadding)
        )
    }
}
