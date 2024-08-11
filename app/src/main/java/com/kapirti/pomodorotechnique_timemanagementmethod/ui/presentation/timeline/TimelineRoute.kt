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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.timeline

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.collectAsState
import com.kapirti.pomodorotechnique_timemanagementmethod.common.EmptyTimeline
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.TimelineToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_TIMELINE_BANNER_ID
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.settings.SettingsUiState

//import com.zepi.social_chat_food.ui.presentation.timeline.EmptyTimeline
//import com.zepi.social_chat_food.ui.presentation.timeline.TimelineVerticalPager

@Composable
fun TimelineRoute(
    openDrawer: () -> Unit,
    isExpandedScreen: Boolean,
    navigateLogin: () -> Unit,
    navigateEdit: () -> Unit,
    navigateUserProfile: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    modifier: Modifier = Modifier,
    viewModel: TimelineViewModel = hiltViewModel(),
) {
    val timelines by viewModel.timelines.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))

    Scaffold(
        topBar = {
            TimelineToolbar(
                openDrawer = openDrawer,
                isExpandedScreen = isExpandedScreen,
                onActionsClick = {
                    if (!uiState.isAnonymousAccount){
                        viewModel.onAddClick(navigateEdit)
                    } else {
                        navigateLogin()
                    }
                }
            )
        },
        bottomBar = { AdsBannerToolbar(ads = ADS_TIMELINE_BANNER_ID) },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        //val media = viewModel.media
        val player = viewModel.player
        val videoRatio = viewModel.videoRatio

        if (timelines.isEmpty()) {
            EmptyTimeline(modifier)
        } else {
            TimelineScreen(
                contentPadding = innerPadding,
                mediaItems = timelines, //media,
                player = player,
                onInitializePlayer = viewModel::initializePlayer,
                onReleasePlayer = viewModel::releasePlayer,
                onChangePlayerItem = viewModel::changePlayerItem,
                videoRatio = videoRatio,
                onUserClick = {
                    includeUserIdViewModel.addUserId(it)
                    navigateUserProfile()
                }
            )
        }
    }
}
