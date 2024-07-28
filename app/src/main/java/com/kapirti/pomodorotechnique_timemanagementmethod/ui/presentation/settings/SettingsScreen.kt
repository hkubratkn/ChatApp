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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.DangerousCardEditor
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.DialogCancelButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.DialogConfirmButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.RegularCardEditor
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.ThemeCardEditor
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.card
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.spacer
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.Cons.DEFAULT_COUNTRY
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Theme


@Composable
fun SettingsScreen(
    navigateEdit: () -> Unit,
    navigateBlockUser: () -> Unit,
    restartApp: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val theme by viewModel.theme
    var showSignOutDialog by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RegularCardEditor(
            stringResource(AppText.share),
            Icons.Default.Share,
            "",
            Modifier.card()
        ) { viewModel.share() }
        RegularCardEditor(
            stringResource(AppText.rate),
            Icons.Default.StarRate,
            "",
            Modifier.card()
        ) { viewModel.rate() }
        RegularCardEditor(
            stringResource(AppText.privacy_policy),
            Icons.Default.PrivacyTip,
            "",
            Modifier.card()
        ) { viewModel.privacyPolicy(snackbarHostState) }
        RegularCardEditor(
            stringResource(AppText.feedback),
            Icons.Default.Feedback,
            "",
            Modifier.card(),
        ) { viewModel.onFeedbackClick(navigateEdit) }
        RegularCardEditor(
            viewModel.pomo.toString(),
            Icons.Default.Timer,
            "",
            Modifier.card()
        ) { viewModel.onPomoClick(navigateEdit) }
        RegularCardEditor(
            viewModel.country ?: DEFAULT_COUNTRY,
            Icons.Default.Flag,
            "",
            Modifier.card()
        ) { viewModel.onCountryClick(navigateEdit) }
        ThemeCardEditor(
            title = AppText.dark_mode,
            content = "",
            checked = theme == Theme.Dark,
            onCheckedChange = {
                viewModel.onThemeChanged(if (it) Theme.Dark else Theme.Light)
            },
            modifier = Modifier.card()
        )
        Spacer(modifier = Modifier.spacer())

        if (viewModel.hasUser) {
            RegularCardEditor(
                stringResource(id = AppText.blocked_users_title),
                Icons.Default.Block,
                "", Modifier.card()
            ) { navigateBlockUser() }
            RegularCardEditor(
                stringResource(AppText.sign_out),
                Icons.AutoMirrored.Filled.Logout,
                "",
                Modifier.card()
            ) { showSignOutDialog = true }
            DangerousCardEditor(
                stringResource(AppText.delete_my_account),
                Icons.Default.Delete,
                "",
                Modifier.card()
            ) { viewModel.onDeleteClick(navigateEdit)}
        }
    }

    if (showSignOutDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showSignOutDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    viewModel.onSignOutClick(restartApp = restartApp)
                    showSignOutDialog = false
                }
            },
            onDismissRequest = { showSignOutDialog = false }
        )
    }
}
