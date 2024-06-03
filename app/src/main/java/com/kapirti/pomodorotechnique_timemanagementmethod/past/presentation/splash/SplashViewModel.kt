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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.splash
/**
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.OnBoardingRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.ConfigurationService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.ConfigurationService,
    private val onBoardingRepository: OnBoardingRepository,
    private val accountService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService,
    logService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService,
): QuickChatViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUpSplashToHome: () -> Unit, openAndPopUpSplashToLogin: () -> Unit,) {
        showError.value = false
        checkState(openAndPopUpSplashToHome, openAndPopUpSplashToLogin)
    }

    private fun checkState(openAndPopUpSplashToHome: () -> Unit, openAndPopUpSplashToLogin: () -> Unit,) {
        launchCatching(snackbar = false) {
            try {
                if (accountService.hasUser) {
                    openAndPopUpSplashToHome()
                } else {
                    openAndPopUpSplashToLogin()

               // onBoardingRepository.readOnBoardingState().collect { completed ->
                 //   openAndPopUp(ZepiDestinations.HOME_ROUTE, ZepiDestinations.SPLASH_ROUTE)

                    /** if (completed) {
                        openAndPopUp(QChatDestinations.HOME_ROUTE, QChatDestinations.SPLASH_ROUTE)
                    } else {
                        openAndPopUp(WELCOME_SCREEN, SPLASH_SCREEN)
                    }*/
                }
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
        }
    }
}
*/
