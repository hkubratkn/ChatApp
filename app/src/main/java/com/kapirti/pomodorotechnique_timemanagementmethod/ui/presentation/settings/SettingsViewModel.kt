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

import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DELETE
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.FEEDBACK
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.POMO
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.CountryRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.PomoService
import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.SettingsRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.GetThemePreferencesUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.GetThemeUpdateUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.PublishThemeUpdateUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.SaveThemePreferencesUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Theme
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val editTypeRepository: EditTypeRepository,
    private val countryRepository: CountryRepository,
    private val settingsRepository: SettingsRepository,
    private val pomoService: PomoService,
    private val saveThemePreferencesUseCase: SaveThemePreferencesUseCase,
    private val publishThemeUpdateUseCase: PublishThemeUpdateUseCase,
    private val getThemePreferencesUseCase: GetThemePreferencesUseCase,
    private val getThemeUpdateUseCase: GetThemeUpdateUseCase,
): PomodoroViewModel(logService) {
    val hasUser = accountService.hasUser

    private val _theme by lazy { mutableStateOf(Theme.Light) }
    val theme: State<Theme> by lazy { _theme.apply { updateTheme() } }

    private val _country = mutableStateOf<String?>(null)
    val country: String?
        get() = _country.value


    private val _pomo = mutableStateOf<Int?>(20)
    val pomo: Int?
        get() = _pomo.value


    init {
        launchCatching {
            countryRepository.readCountryState().collect { itLang ->
                _country.value = itLang
                pomoService.pomo().collect { scored ->
                    _pomo.value = scored
                    getThemeUpdateUseCase().collect { _theme.value = it }
                }
            }
        }
    }

    private fun updateTheme() {
        launchCatching {
            _theme.value = Theme.valueOf(getThemePreferencesUseCase())
        }
    }


    fun onThemeChanged(theme: Theme) {
        launchCatching {
            saveThemePreferencesUseCase(theme.name)
            publishThemeUpdateUseCase(theme)
        }
    }


    fun share() { launchCatching { settingsRepository.share() } }
    fun rate() { launchCatching { settingsRepository.rate() } }

    fun onFeedbackClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(FEEDBACK)
            navigateEdit()
        }
    }
    fun onCountryClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(COUNTRY)
            navigateEdit()
        }
    }
    fun onPomoClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(POMO)
            navigateEdit()
        }
    }

    fun onDeleteClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(DELETE)
            navigateEdit()
        }
    }
    fun onSignOutClick(restartApp: () -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp()
        }
    }

    fun privacyPolicy(snackbarHostState: SnackbarHostState,) {
        launchCatching {
            try {
                settingsRepository.privacyPolicy()
            } catch (e: Exception){
                snackbarHostState.showSnackbar(e.message ?: "")
            }
        }
    }
}

