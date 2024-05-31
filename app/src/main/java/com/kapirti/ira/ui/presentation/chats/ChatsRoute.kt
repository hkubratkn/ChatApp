package com.kapirti.ira.ui.presentation.chats

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
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.common.composable.MenuToolbar
import com.kapirti.ira.core.constants.ConsAds
import com.kapirti.ira.core.viewmodel.IncludeChatViewModel
import com.kapirti.ira.R.string as AppText

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
            tabContent = tabContent,
            currentSection = currentSection,
            isExpandedScreen = isExpandedScreen,
            updateSection = updateSection,
            modifier = modifier.padding(innerPadding)
        )
    }
}
