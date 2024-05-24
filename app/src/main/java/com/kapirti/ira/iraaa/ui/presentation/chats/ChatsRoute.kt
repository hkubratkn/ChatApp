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

package com.kapirti.ira.iraaa.ui.presentation.chats

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ChatsRoute(
    chatsToArchive: () -> Unit,
    chatsToChat: () -> Unit,
   // includeChatViewModel: IncludeChatViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
    //viewModel: ChatsViewModel = hiltViewModel(),
) {}
  /**  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ConsAds.ADS_CHATS_BANNER_ID) },
        floatingActionButton = {
            Button(onClick = chatsToArchive){
                Text(stringResource(AppText.archive))
            }
        },
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
            onArchiveSwipe = viewModel::onArchiveSwipe,
            onChatsClick = {
                viewModel.onChatsClick(it)
                includeChatViewModel.addChat(it)
                chatsToChat()
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}
*/
