package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.chats

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
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeChatViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.kapirti.pomodorotechnique_timemanagementmethod.common.AnonContent
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.settings.SettingsUiState

@Composable
fun ChatsRoute(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    includeChatViewModel: IncludeChatViewModel,
    navigateLogin: () -> Unit,
    navigateRegister: () -> Unit,
    navigateChatsToChatExist: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val chats by viewModel.chats.collectAsStateWithLifecycle()
    val archives by viewModel.archives.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))

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
                text = stringResource(AppText.chats_title),
                actionsIcon = Icons.Default.Search,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                {}
            )
        }
    ) { innerPadding ->
        if (uiState.isAnonymousAccount) {
            AnonContent(
                navigateLogin = navigateLogin,
                navigateRegister = navigateRegister,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            ChatsScreen(
                tabContent = tabContent,
                currentSection = currentSection,
                isExpandedScreen = isExpandedScreen,
                updateSection = updateSection,
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}
