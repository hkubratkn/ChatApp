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

package com.kapirti.video_food_delivery_shopping.ui.presentation.settings

import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import com.kapirti.video_food_delivery_shopping.core.constants.EditType.DELETE
import com.kapirti.video_food_delivery_shopping.core.constants.EditType.FEEDBACK
import com.kapirti.video_food_delivery_shopping.core.constants.EditType.LANG
import com.kapirti.video_food_delivery_shopping.core.constants.EditType.PROFILE
import com.kapirti.video_food_delivery_shopping.core.datastore.EditTypeRepository
import com.kapirti.video_food_delivery_shopping.core.datastore.LangRepository
import com.kapirti.video_food_delivery_shopping.core.repository.SettingsRepository
import com.kapirti.video_food_delivery_shopping.core.usecase.GetThemePreferencesUseCase
import com.kapirti.video_food_delivery_shopping.core.usecase.GetThemeUpdateUseCase
import com.kapirti.video_food_delivery_shopping.core.usecase.PublishThemeUpdateUseCase
import com.kapirti.video_food_delivery_shopping.core.usecase.SaveThemePreferencesUseCase
import com.kapirti.video_food_delivery_shopping.model.Theme
import com.kapirti.video_food_delivery_shopping.model.service.AccountService
import com.kapirti.video_food_delivery_shopping.model.service.LogService
import com.kapirti.video_food_delivery_shopping.ui.presentation.ZepiViewModel

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val editTypeRepository: EditTypeRepository,
    private val langRepository: LangRepository,
    private val settingsRepository: SettingsRepository,
    private val saveThemePreferencesUseCase: SaveThemePreferencesUseCase,
    private val publishThemeUpdateUseCase: PublishThemeUpdateUseCase,
    private val getThemePreferencesUseCase: GetThemePreferencesUseCase,
    private val getThemeUpdateUseCase: GetThemeUpdateUseCase,
): ZepiViewModel(logService) {
    val hasUser = accountService.hasUser

    private val _theme by lazy { mutableStateOf(Theme.Light) }
    val theme: State<Theme> by lazy { _theme.apply { updateTheme() } }

    private val _lang = mutableStateOf<String?>(null)
    val lang: String?
        get() = _lang.value

    init {
        launchCatching {
            langRepository.readLangState().collect{ itLang ->
                _lang.value = itLang
                getThemeUpdateUseCase().collect { _theme.value = it }
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
    fun onLangClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(LANG)
            navigateEdit()
        }
    }
    fun onEditProfileClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(PROFILE)
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

    fun privacyPolicy() {
        launchCatching {
            try {
                settingsRepository.privacyPolicy()
            } catch (e: Exception){
                // onShowSnackbar(genericError, "")
            }
        }
    }
}