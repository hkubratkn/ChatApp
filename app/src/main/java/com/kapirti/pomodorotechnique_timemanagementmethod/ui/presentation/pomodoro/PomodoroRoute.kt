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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.R
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BottomCard
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.HomeTopAppBar
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_HOME_BANNER_ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroRoute(
    openDrawer: () -> Unit,
    navigateTimeOver: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PomodoroViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            HomeTopAppBar(
                openDrawer = openDrawer,
            )
        },
        bottomBar = {
            BottomCard(
                start = R.string.start,
                finish = R.string.done,
                startBtnStatus = viewModel.startBtnStatus,
                finishBtnStatus = viewModel.finishBtnStatus,
                onStartClick = {
                    viewModel.onStartPressed(context = context, navigateTimeOver = navigateTimeOver)
                    //showInterstialAd()
                },
                onFinishClick = {
                    viewModel.onFinishClicked()
                    //showInterstialAd()
                }
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PomodoroScreen(
            modifier = Modifier.padding(innerPadding),
            navigateToHome = navigateToHome,
            showInterstialAd = {},
        )
    }
}
