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

package com.kapirti.ira.iraaa.ui.presentation.archive

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection

@Composable
fun ArchiveScreen(
    openChatExistScreen: () -> Unit,
   // includeChatViewModel: IncludeUserIdViewModel,
    modifier: Modifier = Modifier,
  //  viewModel: ArchiveViewModel = hiltViewModel(),
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {}
/**
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollInteropConnection)
            .systemBarsPadding()
    ) {
        Scaffold(
            topBar = { AdsBannerToolbar(ads = ADS_ARCHIVE_BANNER_ID) },
            modifier = modifier.fillMaxSize(),
        ) { paddingValues ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ArchivesContent(
                loading = uiState.isLoading,
                archives = uiState.items,
                onRefresh = viewModel::refresh,
                onArchive = viewModel::onChatSwipe,
                onChatsClick = {
                    includeChatViewModel.addChat(it)
                    openChatExistScreen()
                },
                modifier = Modifier.padding(paddingValues),
                scrollState = scrollState
            )
        }

        val jumpThreshold = with(LocalDensity.current) { JumpToBottomThreshold.toPx() }
        val jumpToTopButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex != 0 ||
                        scrollState.firstVisibleItemScrollOffset > jumpThreshold
            }
        }
        JumpToButton(
            text = AppText.jump_top,
            icon = Icons.Default.ArrowUpward,
            enabled = jumpToTopButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArchivesContent(
    loading: Boolean,
    archives: List<Chat>,
    onRefresh: () -> Unit,
    onArchive: (Chat) -> Unit,
    onChatsClick: (Chat) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState
) {
    LoadingContent(
        loading = loading,
        empty = archives.isEmpty() && !loading,
        emptyContent = {
            EmptyContent(
                icon = Icons.Default.Chat,
                label = AppText.no_archives_all,
                modifier
            )
        },
        onRefresh = onRefresh
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(state = scrollState) {
                items(archives, key = { it.chatId }) { chatItem ->

                    //      onArchive = { onArchive(chatItem) }

                    ChatItem(
                        chat = chatItem,
                        onChatsClick = {
                            onChatsClick(chatItem)
                        }
                    )
                }
            }
        }
    }
}

private val JumpToBottomThreshold = 56.dp
*/
