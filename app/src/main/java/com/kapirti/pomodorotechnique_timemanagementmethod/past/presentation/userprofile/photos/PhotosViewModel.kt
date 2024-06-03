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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.userprofile.photos
/**
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.EditType.PHOTOS
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val accountService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService,
    private val firestoreService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService,
    private val editTypeRepository: EditTypeRepository,
    logService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService
): QuickChatViewModel(logService) {
    private val _userPhotos = MutableStateFlow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>>(emptyList())
    val userPhotos: StateFlow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>> = _userPhotos

    val currentUserId = accountService.currentUserId

    fun initialize(userUid: String) {
        launchCatching {
            firestoreService.getUserPhotos(userUid).collect { itPhotos ->
                _userPhotos.value = itPhotos
            }
        }
    }
    fun onAddClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(PHOTOS)
            navigateEdit()
        }
    }
}
*/
