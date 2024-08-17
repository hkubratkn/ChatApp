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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.welcome

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_WELCOME_BANNER_ID

@Composable
fun WelcomeRoute(
    navigateAndPopUpWelcomeToTimeline: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { AdsBannerToolbar(ads = ADS_WELCOME_BANNER_ID) },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        WelcomeScreen(
            navigateAndPopUpWelcomeToTimeline = navigateAndPopUpWelcomeToTimeline,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
