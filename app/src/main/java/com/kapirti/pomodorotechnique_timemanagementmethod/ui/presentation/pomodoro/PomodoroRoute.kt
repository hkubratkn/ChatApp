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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.pomodoro

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.HomeTopAppBar
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_HOME_BANNER_ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroRoute(
    openDrawer: () -> Unit,
    navigateSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PomodoroViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            HomeTopAppBar(
                openDrawer = openDrawer,
            )
        },
        bottomBar = { AdsBannerToolbar(ads = ADS_HOME_BANNER_ID) },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PomodoroScreen()
    }
}
