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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.userprofile
/**
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.UserIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val accountService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService,
    private val firestoreService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService,
    private val userIdRepository: UserIdRepository,
    logService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService,
): QuickChatViewModel(logService) {
    val hasUser = accountService.hasUser
    val uid = accountService.currentUserId

    private val _user = MutableStateFlow<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User>(
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User()
    )
    var user: StateFlow<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User> = _user

    private val _userPhotos = MutableStateFlow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>>(emptyList())
    val userPhotos: StateFlow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>> = _userPhotos

    fun initialize(userUid: String) {
        launchCatching {
            userIdRepository.saveUserIdState(userUid)
            firestoreService.getUser(userUid)?.let { itUser ->
                _user.value = itUser
                firestoreService.getUserPhotos(itUser.uid).collect{ itPhotos ->
                    _userPhotos.value = itPhotos
                }
            }
        }
    }
}
*/
