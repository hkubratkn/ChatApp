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

package com.test.test.ui.presentation.settings

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
import com.test.test.R.string as AppText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.test.test.common.composable.DangerousCardEditor
import com.test.test.common.composable.DialogCancelButton
import com.test.test.common.composable.DialogConfirmButton
import com.test.test.common.composable.RegularCardEditor
import com.test.test.common.composable.ThemeCardEditor
import com.test.test.common.ext.card
import com.test.test.common.ext.spacer


@Composable
fun SettingsScreen(
    navigateLogin: () -> Unit,
    navigateRegister: () -> Unit,
    navigateEdit: () -> Unit,
    navigateBlockedUser: () -> Unit,
    restartApp: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))


    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isAnonymousAccount) {
            RegularCardEditor(stringResource(AppText.log_in),
                    Icons.AutoMirrored.Default.Login, "", Modifier.card()) { navigateLogin() }
            RegularCardEditor(stringResource(AppText.register),
                Icons.Default.PersonAdd, "", Modifier.card()) { navigateRegister() }
        } else {
            RegularCardEditor(stringResource(id = AppText.blocked_users_title),
                Icons.Default.Block, "", Modifier.card()) { navigateBlockedUser() }
            RegularCardEditor(stringResource(AppText.sign_out) + " (${uiState.email})",
                Icons.AutoMirrored.Default.Logout, "", Modifier.card()) { showSignOutDialog = true }
            DangerousCardEditor(stringResource(AppText.delete_my_account),
                Icons.Default.Delete, "", Modifier.card()) { viewModel.onDeleteClick()}
        }
    }

    if (showSignOutDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showSignOutDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    viewModel.onSignOutClick()
                    showSignOutDialog = false
                }
            },
            onDismissRequest = { showSignOutDialog = false },
        )
    }
}
