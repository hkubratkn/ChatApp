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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.productivity

import androidx.compose.material.icons.filled.Close
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.MenuToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_PRODUCTIVITY_BANNER_ID
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.ui.platform.LocalContext

@Composable
fun ProductivityRoute(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    navigateToProductivity: () -> Unit,
    navigateTimeOver: () -> Unit,
    showInterstitialAds: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: ProductivityViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val tabContent = rememberTabContent(
        pomo = viewModel.pomo.toString(),
        finishClick = viewModel.finishClick,
        navigateToPomodoro = navigateToProductivity,
        showInterstialAd = showInterstitialAds,
        startBtnStatus = viewModel.startBtnStatus,
        finishBtnStatus = viewModel.finishBtnStatus,
        onStartPressed = { viewModel.onStartPressed(context = context, navigateTimeOver = navigateTimeOver)},
        onFinishClicked = { viewModel.onFinishClicked()}
    )
    val (currentSection, updateSection) = rememberSaveable {
        mutableStateOf(tabContent.first().section)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ADS_PRODUCTIVITY_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = AppText.productivity_title,
                actionsIcon = Icons.Default.Close,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                onActionClick = {}
            )
        }
    ) { innerPadding ->
        ProductivityScreen(
            tabContent = tabContent,
            currentSection = currentSection,
            isExpandedScreen = isExpandedScreen,
            updateSection = updateSection,
            modifier = modifier.padding(innerPadding)
        )
    }
}







/**











import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.R
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BottomCard
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.HomeTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductivityRoute(
    openDrawer: () -> Unit,
    navigateTimeOver: () -> Unit,
    navigateToProductivity: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductivityViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            HomeTopAppBar(
                openDrawer = openDrawer,
            )
        },
        bottomBar = {

        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        ProductivityScreen(
            modifier = Modifier.padding(innerPadding),
            navigateToPomodoro = navigateToProductivity,
            showInterstialAd = {},
        )
    }
}
*/
