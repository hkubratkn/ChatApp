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

package com.zepi.social_chat_food.ui.presentation.login

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.zepi.social_chat_food.iraaa.common.ext.isValidEmail
import com.zepi.social_chat_food.model.service.AccountService
import com.zepi.social_chat_food.model.service.FirestoreService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.ui.presentation.QChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService,
): QChatViewModel(logService) {
    var uiState = mutableStateOf(LogInUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val button
        get() = uiState.value.button

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onButtonChange() {
        uiState.value = uiState.value.copy(button = !button)
    }

    fun onLogInClick(
        restartApp: () -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,
        emailError: String, emptyPasswordError: String
        ) {
        onButtonChange()
        if (!email.isValidEmail()) {
            launchCatching {
                onShowSnackbar(emailError, "")
                onButtonChange()
            }
            return
        }

        if (password.isBlank()) {
            launchCatching {
                onShowSnackbar(emptyPasswordError, "")
                onButtonChange()
            }
            return
        }

        launchCatching {
            try {
                accountService.authenticate(email, password)
                restartApp()
            } catch (ex: FirebaseAuthException) {
                launchCatching {
                    onShowSnackbar(ex.localizedMessage ?: "", "")
                    onButtonChange()
                }
                throw ex
            }
        }
    }

    fun onForgotPasswordClick(
        onShowSnackbar: suspend (String, String?) -> Boolean,
        emailError: String, recoveryEmailSent: String
    ) {
        if (!email.isValidEmail()) {
            launchCatching {
                onShowSnackbar(emailError, "")
            }
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            onShowSnackbar(recoveryEmailSent, "")
        }
    }
}
