package com.zepi.social_chat_food.iraaa.ui.presentation.archive

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import com.zepi.social_chat_food.R.string as AppText
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import com.zepi.social_chat_food.iraaa.common.EmptyContent
import com.zepi.social_chat_food.iraaa.common.composable.AdsBannerToolbar
import com.zepi.social_chat_food.iraaa.common.composable.JumpToButton
import com.zepi.social_chat_food.iraaa.common.composable.LoadingContent
import com.zepi.social_chat_food.core.constants.ConsAds.ADS_ARCHIVE_BANNER_ID
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeChatViewModel
import com.zepi.social_chat_food.iraaa.ui.presentation.chats.ChatItem

@Composable
fun ArchiveScreen(
    openChatExistScreen: () -> Unit,
    includeChatViewModel: IncludeChatViewModel,
    modifier: Modifier = Modifier,
    viewModel: ArchiveViewModel = hiltViewModel(),
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
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
    archives: List<com.zepi.social_chat_food.model.Chat>,
    onRefresh: () -> Unit,
    onArchive: (com.zepi.social_chat_food.model.Chat) -> Unit,
    onChatsClick: (com.zepi.social_chat_food.model.Chat) -> Unit,
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
