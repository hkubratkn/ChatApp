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

package com.zepi.social_chat_food.ui.presentation.settings

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
import com.zepi.social_chat_food.core.constants.ConsAds

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
