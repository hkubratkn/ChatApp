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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.chats
/**
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.MenuToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.ConsAds
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeChatViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText

@Composable
fun ChatsRoute(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    includeChatViewModel: IncludeChatViewModel,
    navigateChatsToChatExist: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val chats by viewModel.chats.collectAsStateWithLifecycle()
    val archives by viewModel.archives.collectAsStateWithLifecycle()

    val tabContent = rememberTabContent(
        chats = chats,
        archives = archives,
        onChatClick = {
            viewModel.saveChatId(it.chatId)
            includeChatViewModel.addChat(it)
            navigateChatsToChatExist()
        },
        onLongClickChats = {
            viewModel.onLongClickChats(it)
        },
        onLongClickArchives = {
            viewModel.onLongClickArchives(it)
        }
    )
    val (currentSection, updateSection) = rememberSaveable {
        mutableStateOf(tabContent.first().section)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.ConsAds.ADS_CHATS_BANNER_ID) },
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
            tabContent = tabContent,
            currentSection = currentSection,
            isExpandedScreen = isExpandedScreen,
            updateSection = updateSection,
            modifier = modifier.padding(innerPadding)
        )
    }
}
*/
