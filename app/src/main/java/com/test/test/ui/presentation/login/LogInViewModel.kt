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

package com.test.test.ui.presentation.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.test.test.common.ext.isValidEmail
import com.test.test.model.service.AccountService
import com.test.test.model.service.FirestoreService
import com.test.test.model.service.LogService
import com.test.test.ui.presentation.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService,
): AppViewModel(logService) {
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

    fun onLogInClick(
        restartApp: () -> Unit, snackbarHostState: SnackbarHostState,
        emailError: String, emptyPasswordError: String, wrongPasswordError: String,
        navigateAndPopUpLoginToRegister: () -> Unit
    ) {
        onButtonChange()
        if (!email.isValidEmail()) {
            launchCatching {
                onIsErrorEmailChange(true)
                snackbarHostState.showSnackbar(emailError)
                onButtonChange()
            }
            return
        }

        if (password.isBlank()) {
            launchCatching {
                onIsErrorPasswordChange(true)
                snackbarHostState.showSnackbar(emptyPasswordError)
                onButtonChange()
            }
            return
        }

        launchCatching {
            try {
                accountService.authenticate(email, password)
                restartApp()
            } catch (ex: FirebaseAuthException) {
                if (ex.errorCode == "ERROR_WRONG_PASSWORD"){
                    launchCatching {
                        onIsErrorPasswordChange(true)
                        snackbarHostState.showSnackbar(wrongPasswordError)
                        onButtonChange()
                    }
                } else if (ex.errorCode == "ERROR_USER_NOT_FOUND"){
                    navigateAndPopUpLoginToRegister()
                } else if (ex.errorCode == "ERROR_INVALID_CREDENTIAL") {
                    navigateAndPopUpLoginToRegister()
                }

                throw ex
            }
        }
    }

    fun onForgotPasswordClick(
        snackbarHostState: SnackbarHostState,
        emailError: String, recoveryEmailSent: String,
        context: Context
    ) {
        if (!email.isValidEmail()) {
            launchCatching {
                onIsErrorEmailChange(true)
                snackbarHostState.showSnackbar(emailError)
            }
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            onIsErrorEmailChange(false)
            snackbarHostState.showSnackbar(recoveryEmailSent)
        }
    }

    private fun onButtonChange() { uiState.value = uiState.value.copy(button = !button) }
    private fun onIsErrorEmailChange(newValue: Boolean) { uiState.value = uiState.value.copy(isErrorEmail = newValue) }
    private fun onIsErrorPasswordChange(newValue: Boolean) { uiState.value = uiState.value.copy(isErrorPassword = newValue) }

}

