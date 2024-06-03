package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.MenuToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds

@Composable
fun SettingsRoute(
    navigateEdit: () -> Unit,
    restartApp: () -> Unit,
    navigateBlockUser: () -> Unit,
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
            snackbarHostState = snackbarHostState,
            modifier = modifier.padding(innerPadding),
            navigateBlockUser = navigateBlockUser,
        )
    }
}
