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

package com.kapirti.ira.ui.presentation.userprofile

import com.kapirti.ira.model.User
import com.kapirti.ira.model.UserPhotos
import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


data class UserPhotosUiState(
    val items: List<UserPhotos> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService,
): ZepiViewModel(logService) {
    val hasUser = accountService.hasUser
    val uid = accountService.currentUserId

    private val _user = MutableStateFlow<User>(User())
    var user: StateFlow<User> = _user

    private val _userPhotos = MutableStateFlow<List<UserPhotos>>(emptyList())
    var userPhotos: StateFlow<List<UserPhotos>> = _userPhotos

    fun initialize(userUid: String) {
        launchCatching {
            firestoreService.getUser(userUid)?.let { itUser ->
                _user.value = itUser
              //  firestoreService.userPhotos.collect{ up ->
                //    _userPhotos.value = up
               // }
            }
        }
    }

    /**
    private val _userMessagePhotos: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoadingPhotos = MutableStateFlow(false)
    private val _isTaskDeletedPhotos = MutableStateFlow(false)
    private val _taskAsyncPhotos = firestoreService.userPhotos
    .map { handleUserPhotos(it) }
    .catch { emit(Async.Error(R.string.loading_user_photos_error)) }


    val selectedPhotos: StateFlow<UserPhotosUiState> = combine(
    _userMessagePhotos, _isLoadingPhotos, _isTaskDeletedPhotos, _taskAsyncPhotos
    ) { userMessage, isLoading, isTaskDeleted, taskAsync ->
    when (taskAsync) {
    Async.Loading -> {
    UserPhotosUiState(isLoading = true)
    }

    is Async.Error -> {
    UserPhotosUiState(
    userMessage = taskAsync.errorMessage,
    )
    }

    is Async.Success -> {
    UserPhotosUiState(
    items = taskAsync.data,
    isLoading = isLoading,
    userMessage = userMessage,
    )
    }
    }
    }
    .stateIn(
    scope = viewModelScope,
    started = WhileUiSubscribed,
    initialValue = UserPhotosUiState(isLoading = true)
    )
    private fun handleUserPhotos(task: List<UserPhotos>?): Async<List<UserPhotos>> {
    if (task == null) {
    return Async.Error(R.string.user_photos_not_found)
    }
    return Async.Success(task)
    }
     */



}
