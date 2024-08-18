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


package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.userprofile

import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.UserIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.settings.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val userIdRepository: UserIdRepository,
    logService: LogService,
): PomodoroViewModel(logService) {
    val uid = accountService.currentUserId
    val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous) }

    private val _user = MutableStateFlow<User>(User())
    var user: StateFlow<User> = _user

    fun initialize(userUid: String) {
        launchCatching {
            userIdRepository.saveUserIdState(userUid)
            firestoreService.getUser(userUid)?.let { itUser ->
                _user.value = itUser
            }
        }
    }
}
