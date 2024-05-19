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

package com.kapirti.video_food_delivery_shopping.ui.presentation.chats

import com.kapirti.video_food_delivery_shopping.common.composable.AdsBannerToolbar
import com.kapirti.video_food_delivery_shopping.common.composable.MenuToolbar
import com.kapirti.video_food_delivery_shopping.core.constants.ConsAds
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.video_food_delivery_shopping.R.string as AppText

@Composable
fun ChatsRoute(
    onChatClicked: (chatId: String) -> Unit,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ConsAds.ADS_CHATS_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = AppText.chats_title,
                actionsIcon = Icons.Default.Search,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
    ) { innerPadding ->
        ChatsScreen(
            uiState = uiState,
            onRefresh = viewModel::refresh,
            contentPadding = innerPadding,
            onChatClicked = onChatClicked,
        )
    }
}
