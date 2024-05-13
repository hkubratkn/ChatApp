package com.zepi.social_chat_food.iraaa.ui.presentation.chats

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
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.common.composable.AdsBannerToolbar
import com.zepi.social_chat_food.iraaa.common.composable.MenuToolbar
import com.zepi.social_chat_food.iraaa.core.constants.ConsAds
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeChatViewModel

@Composable
fun ChatsRoute(
    chatsToArchive: () -> Unit,
    chatsToChat: () -> Unit,
    includeChatViewModel: IncludeChatViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
