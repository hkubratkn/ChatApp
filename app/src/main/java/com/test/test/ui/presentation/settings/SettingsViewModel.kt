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

import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.test.test.model.service.AccountService
import com.test.test.model.service.LogService
import com.test.test.ui.presentation.AppViewModel
import kotlinx.coroutines.flow.map

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
): AppViewModel(logService) {
    val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous, it.email) }


    fun onDeleteClick(){
        launchCatching {

        }
    }
    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }

}

