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

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.AskReviewDataStore
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.IsReviewDataStore
import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.SettingsRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import kotlinx.coroutines.Job

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val firestoreService: FirestoreService,
    private val isReviewDataStore: IsReviewDataStore,
    private val askReviewDataStore: AskReviewDataStore,
    private val settingsRepository: SettingsRepository
): PomodoroViewModel(logService) {
    var showReviewDialog = mutableStateOf(false)

    fun initialize(){
        launchCatching {
            askReviewDataStore.increaseScore()
            isReviewDataStore.readIsReviewState().collect{
                if(!it){
                    askReviewDataStore.getScore().collect{
                        if(it > 4){
                            showReviewDialog.value = true
                        }
                    }
                }
            }
        }
    }


    private var job: Job? = null

    fun showDialogTrue() {
        launchCatching {
            job?.cancel()
            job = launchCatching {
                try {
                    settingsRepository.rate()
                    showDialogFalse()
                    isReviewDataStore.saveIsReviewState(true)
                } catch (e: FirebaseFirestoreException) {
                }
            }
        }
    }


    fun showDialogFalse(){
        launchCatching {
            showReviewDialog.value = false
        }
    }
}
