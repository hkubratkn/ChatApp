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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FireTruck
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.pomodorotechnique_timemanagementmethod.R
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.DialogCancelButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.DialogConfirmButton
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.Cons.SPLASH_TIMEOUT
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds
import kotlinx.coroutines.delay

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
           /** HomeTopAppBar(
                openDrawer = openDrawer,
                onSearchClick = navigateSearch,
                //onFilterClick = { filtersVisible = true },
            )*/
        },
        bottomBar = { AdsBannerToolbar(ads = ConsAds.ADS_HOME_BANNER_ID) },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Text("home screen")
    }

    if (viewModel.showReviewDialog.value) {
        AlertDialog(
            title = { Text(stringResource(R.string.review_title)) },
            text = { Text(stringResource(R.string.review_description)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { viewModel.showDialogFalse() } },
            confirmButton = {
                DialogConfirmButton(R.string.review_now) {
                    viewModel.showDialogTrue()
                 //   viewModel.onSignOutClick(restartApp = restartApp)
                  //  showSignOutDialog = false
                }
            },
            onDismissRequest = { viewModel.showDialogFalse() }
        )
    }

    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        viewModel.initialize()
    }
}
