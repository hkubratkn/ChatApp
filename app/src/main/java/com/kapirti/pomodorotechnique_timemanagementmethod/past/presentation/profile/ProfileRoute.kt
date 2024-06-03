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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.profile
/**
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.MenuToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.ConsAds.ADS_PROFILE_BANNER_ID
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeUserIdViewModel

@Composable
fun ProfileRoute(
    navigateEdit: () -> Unit,
    navigatePhotos: () -> Unit,
    showInterstialAd: () -> Unit,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val user = viewModel.user.collectAsStateWithLifecycle()
    val photos by viewModel.userPhotos.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ads = ADS_PROFILE_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = AppText.profile_title,
                actionsIcon = Icons.Default.AccountCircle,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        ProfileScreen(
            user = user.value,
            modifier = Modifier.padding(innerPadding),
            photos = photos,
            onProfilePhotoClick = { viewModel.onProfilePhotoClick(navigateEdit) },
            onLongClickDisplayName = { viewModel.onDisplayNameClick(navigateEdit) },
            onLongClickNameSurname = { viewModel.onNameSurnameClick(navigateEdit) },
            onLongClickGender = { viewModel.onGenderClick(navigateEdit) },
            onLongClickDescription= { viewModel.onDescriptionClick(navigateEdit) },
            onLongClickBirthday = { viewModel.onBirthdayClick(navigateEdit) },
            navigatePhotos = {
                includeUserIdViewModel.addPartnerId(user.value.uid)
                navigatePhotos()
            }
        )
    }
}
*/
