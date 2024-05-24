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

package com.kapirti.ira.ui.presentation.profile

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ProfileRoute(
    navigateEdit: () -> Unit,
    showInterstialAd: () -> Unit,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    // includeAssetViewModel: IncludeAssetViewModel,
    // navigateToAssetDetail: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
  //  viewModel: ProfileViewModel = hiltViewModel(),
) {}/**
    val user = viewModel.user.collectAsStateWithLifecycle()
    val selectedPhotos by viewModel.selectedPhotos.collectAsStateWithLifecycle()
    // val selectedAsset by viewModel.selectedAsset.collectAsStateWithLifecycle()
    val hasUser = viewModel.hasUser

    val tabContent = rememberTabContent(
        selectedPhotos = selectedPhotos,
        //selectedAssets = selectedAsset,
        onRefresh = viewModel::refresh,
        onAssetClick = {
            // includeAssetViewModel.addAsset(it)
            // navigateToAssetDetail()
        },
    )
    val (currentSection, updateSection) = rememberSaveable {
        mutableStateOf(tabContent.first().section)
    }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ads = ADS_PROFILE_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = AppText.profile_title,
                actionsIcon = Icons.Default.AccountCircle,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            ) },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            if (hasUser){
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Red),
                    onClick = {
                        viewModel.onAddClick(navigateEdit)
                    }
                ) {
                    Icon(Icons.Default.Add, null)
                }
            }
        }
    ) { innerPadding ->

        if(viewModel.hasUser) {
            ProfileScreen(
                profile = user.value,
                tabContent = tabContent,
                currentSection = currentSection,
                isExpandedScreen = isExpandedScreen,
                onTabChange = updateSection,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            EmptyContentProfile(
                label = AppText.profile_not_found,
                icon = Icons.Default.Login,
                onLoginClick = profileToLogin,
                onRegisterClick = profileToRegister,
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}
*/
