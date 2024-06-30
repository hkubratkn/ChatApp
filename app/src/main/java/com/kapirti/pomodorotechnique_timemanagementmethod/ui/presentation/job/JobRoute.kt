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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.job

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_JOB_BANNER_ID
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeJobViewModel

@Composable
fun JobRoute(
    isExpandedScreen: Boolean,
    includeJobViewModel: IncludeJobViewModel,
    openDrawer: () -> Unit,
    navigateEdit: () -> Unit,
    navigateJobDetail: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: JobViewModel = hiltViewModel()
) {
    val country by viewModel.country.collectAsStateWithLifecycle()
    val jobs by viewModel.jobs.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ADS_JOB_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = country,
                actionsIcon = Icons.Default.Add,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                onActionClick = { viewModel.onAddClick(navigateEdit) }
            )
        }
    ) { innerPadding ->
        JobScreen(
            jobs = jobs,
            onItemClick = {
                includeJobViewModel.addJob(it)
                navigateJobDetail()
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}
