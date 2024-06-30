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

import com.kapirti.pomodorotechnique_timemanagementmethod.common.stateInUi
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.Cons.DEFAULT_COUNTRY
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.JOB
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.CountryRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val editTypeRepository: EditTypeRepository,
    private val firestoreService: FirestoreService,
    logService: LogService
): PomodoroViewModel(logService){
    val country = countryRepository.readCountryState().stateInUi(DEFAULT_COUNTRY)
    val jobs = firestoreService.jobs.stateInUi(emptyList())

    fun onAddClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(JOB)
            navigateEdit()
        }
    }

}
