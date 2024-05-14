package com.zepi.social_chat_food.iraaa.ui.presentation.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.zepi.social_chat_food.iraaa.common.EmptyContentChats
import com.zepi.social_chat_food.iraaa.common.composable.LoadingContent
import com.zepi.social_chat_food.model.Chat

@Composable
fun ChatsScreen(
    uiState: ChatsUiState,
    onRefresh: () -> Unit,
    onArchiveSwipe: (com.zepi.social_chat_food.model.Chat) -> Unit,
    onChatsClick: (com.zepi.social_chat_food.model.Chat) -> Unit,
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
    chats: List<com.zepi.social_chat_food.model.Chat>,
    onRefresh: () -> Unit,
    onArchive: (com.zepi.social_chat_food.model.Chat) -> Unit,
    onChatsClick: (com.zepi.social_chat_food.model.Chat) -> Unit,
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
