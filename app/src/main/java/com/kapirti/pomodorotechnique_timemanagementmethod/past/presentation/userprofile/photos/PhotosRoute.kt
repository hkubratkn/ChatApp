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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.userprofile.photos
/**
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Modifier
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BackToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeUserIdViewModel

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
        bottomBar = { AdsBannerToolbar(com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_PHOTOS_BANNER_ID) },
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
*/
