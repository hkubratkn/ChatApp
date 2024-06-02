package com.kapirti.ira.ui.presentation.userprofile.photos

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.ira.R.string as AppText
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Modifier
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.common.composable.BackToolbar
import com.kapirti.ira.common.composable.MenuToolbar
import com.kapirti.ira.core.constants.ConsAds
import com.kapirti.ira.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.ira.ui.presentation.chats.ChatsScreen

@Composable
fun PhotosRoute(
    popUp: () -> Unit,
    navigateEdit: () -> Unit,
    isExpandedScreen: Boolean,
    includeUserIdViewModel: IncludeUserIdViewModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel = hiltViewModel(),
){
    LaunchedEffect(includeUserIdViewModel) {
        viewModel.initialize(
            userUid = includeUserIdViewModel.partnerId ?: "",
        )
    }

    val currentUserId = viewModel.currentUserId
    val userPhotos = viewModel.userPhotos.collectAsStateWithLifecycle()
    val actionsIcon = if(currentUserId == includeUserIdViewModel.partnerId){
        Icons.Default.Add
    } else {
        Icons.Default.Star
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ConsAds.ADS_PHOTOS_BANNER_ID) },
        topBar = {
            BackToolbar(
                text = AppText.photos_title,
                actionsIcon = actionsIcon,
                isExpandedScreen = isExpandedScreen,
                popUp = popUp,
                onActionsClick = {
                    if(currentUserId == includeUserIdViewModel.partnerId){
                        viewModel.onAddClick(navigateEdit)
                    }
                }
            )
        }
    ) { innerPadding ->
        PhotosScreen(
            userPhotos = userPhotos.value,
            modifier = modifier.padding(innerPadding)
        )
    }
}