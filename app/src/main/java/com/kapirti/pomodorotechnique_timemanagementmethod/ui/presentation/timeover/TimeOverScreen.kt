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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.timeover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.HomeTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsLargeBanner
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BottomCard
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.TextPomo
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.smallSpacer
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_TIMEOVER_BANNER_ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimeOverScreen(
    openDrawer: () -> Unit,
    navigateToProductivity: () -> Unit,
    showInterstialAd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimeOverViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = { HomeTopAppBar(openDrawer) },
        bottomBar = {
            BottomCard(
                start = AppText.start,
                finish = AppText.done,
                startBtnStatus = viewModel.startBtnStatus,
                finishBtnStatus = viewModel.finishBtnStatus,
                onStartClick = {
                    viewModel.onStartPressed(context = context, navigateToPomodoro = navigateToProductivity)
                    showInterstialAd()
                },
                onFinishClick = {
                    viewModel.onFinishClicked()
                    showInterstialAd()
                }
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextPomo(title = viewModel.score.toString())
            Spacer(modifier = Modifier.smallSpacer())
            AdsLargeBanner(ads = ADS_TIMEOVER_BANNER_ID)
        }
    }
}
