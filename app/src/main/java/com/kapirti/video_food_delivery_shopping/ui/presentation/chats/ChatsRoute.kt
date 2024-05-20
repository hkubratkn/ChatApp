package com.kapirti.video_food_delivery_shopping.ui.presentation.chats

import com.kapirti.video_food_delivery_shopping.common.composable.AdsBannerToolbar
import com.kapirti.video_food_delivery_shopping.common.composable.MenuToolbar
import com.kapirti.video_food_delivery_shopping.core.constants.ConsAds
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.video_food_delivery_shopping.R.string as AppText
import com.kapirti.video_food_delivery_shopping.common.EmptyContentChats
import com.kapirti.video_food_delivery_shopping.core.viewmodel.IncludeChatViewModel

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

        if (chats.isEmpty()) {
            EmptyContentChats(modifier)
        } else {
            ChatsScreen(
                chats = chats,
                contentPadding = innerPadding,
                includeChatViewModel = includeChatViewModel,
                navigateChatsToChatExist = navigateChatsToChatExist
            )
        }
    }
}
