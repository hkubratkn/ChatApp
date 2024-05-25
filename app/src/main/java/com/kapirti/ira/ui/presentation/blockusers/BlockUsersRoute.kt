package com.kapirti.ira.ui.presentation.blockusers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.ira.common.EmptyContent
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.core.constants.ConsAds.ADS_BLOCK_USERS_BANNER_ID
import com.kapirti.ira.R.string as AppText
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Search
import com.kapirti.ira.common.composable.MenuToolbar

@Composable
fun BlockUsersRoute(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
    viewModel: BlockUsersViewModel = hiltViewModel(),
) {
    val blockUsers by viewModel.blockUsers.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ADS_BLOCK_USERS_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = AppText.block_users_title,
                actionsIcon = Icons.Default.Search,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
    ) { innerPadding ->

        if (blockUsers.isEmpty()) {
            EmptyContent(
                icon = Icons.Default.Block,
                label = AppText.no_block_users_all,
                modifier.padding(innerPadding)
            )
        } else {
            BlockUsersScreen(
                blockUsers = blockUsers,
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}
