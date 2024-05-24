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
/**
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.kapirti.video_food_delivery_shopping.common.EmptyContentChats
import com.kapirti.video_food_delivery_shopping.common.composable.LoadingContent
import com.kapirti.video_food_delivery_shopping.model.Chat

@Composable
fun ChatsScreen(
   // uiState: ChatsUiState,
    onRefresh: () -> Unit,
    onArchiveSwipe: (Chat) -> Unit,
    onChatsClick: (Chat) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    ChatsContent(
        loading = uiState.isLoading,
        chats = uiState.items,
        onRefresh = onRefresh,
        onArchive = onArchiveSwipe,
        onChatsClick = onChatsClick,
        modifier = modifier,
        scrollState = scrollState
    )
}

@Composable
private fun ChatsContent(
    loading: Boolean,
    chats: List<Chat>,
    onRefresh: () -> Unit,
    onArchive: (Chat) -> Unit,
    onChatsClick: (Chat) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState
) {
    LoadingContent(
        loading = loading,
        empty = chats.isEmpty() && !loading,
        emptyContent = { EmptyContentChats(modifier = modifier) },
        onRefresh = onRefresh
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(state = scrollState) {
                items(chats, key = { it.chatId }) { chatItem ->
                    //onArchive = { onArchive(chatItem) }
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
*/
