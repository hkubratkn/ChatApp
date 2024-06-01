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

package com.kapirti.ira.ui.presentation.register

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.ira.core.datastore.EditTypeRepository
import com.kapirti.ira.core.datastore.LangRepository
import com.kapirti.ira.common.ext.isValidEmail
import com.kapirti.ira.common.ext.isValidPassword
import com.kapirti.ira.common.ext.passwordMatches
import com.kapirti.ira.core.constants.EditType.PROFILE
import com.kapirti.ira.iraaa.ggoo.SignInResult
import com.kapirti.ira.iraaa.ggoo.SignInState
import com.kapirti.ira.iraaa.ggoo.UserData
import com.kapirti.ira.model.Feedback
import com.kapirti.ira.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.ui.presentation.QuickChatViewModel
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class RegisterViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val langRepository: LangRepository,
    private val editTypeRepository: EditTypeRepository,
): QuickChatViewModel(logService) {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()





    val langValue = Locale.getDefault().getDisplayLanguage()

    var uiState = mutableStateOf(RegisterUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val button
        get() = uiState.value.button




    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun googleRegisterDone(
        navigateAndPopUpRegisterToEdit: () -> Unit,
        userData: UserData?
    ) {
        launchCatching {
            firestoreService.saveUser(
                User(
                    photo = userData?.let { it.profilePictureUrl } ?: "",
                    displayName = userData?.let { it.username } ?: "",
                    language = langValue,
                    online = true,
                    uid = accountService.currentUserId,
                    date = Timestamp.now()
                )
            )

            firestoreService.saveLang(Feedback(langValue))
            editTypeRepository.saveEditTypeState(PROFILE)
            langRepository.saveLangState(langValue)
            resetState()
            navigateAndPopUpRegisterToEdit()
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }



    fun onEmailChange(newValue: String) { uiState.value = uiState.value.copy(email = newValue) }
    fun onPasswordChange(newValue: String) { uiState.value = uiState.value.copy(password = newValue) }
    fun onRepeatPasswordChange(newValue: String) { uiState.value = uiState.value.copy(repeatPassword = newValue) }


    fun onRegisterClick(
        snackbarHostState: SnackbarHostState,
        navigateAndPopUpRegisterToEdit: () -> Unit,
        email_error: String,
        password_error: String,
        password_match_error: String,
    ) {
        onButtonChange()
        if (!email.isValidEmail()) {
            launchCatching { snackbarHostState.showSnackbar(email_error) }
            onButtonChange()
            return
        }

        if (!password.isValidPassword()) {
            launchCatching { snackbarHostState.showSnackbar(password_error) }
            onButtonChange()
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            launchCatching { snackbarHostState.showSnackbar(password_match_error) }
            onButtonChange()
            return
        }

        launchCatching {
            try {
                accountService.linkAccount(email, password)
                firestoreService.saveUser(
                    User(
                        language = langValue,
                        online = true,
                        uid = accountService.currentUserId,
                        date = Timestamp.now()
                    )
                )
                firestoreService.saveLang(Feedback(langValue))
                editTypeRepository.saveEditTypeState(PROFILE)
                langRepository.saveLangState(langValue)
                navigateAndPopUpRegisterToEdit()
            } catch (ex: FirebaseAuthException) {
                launchCatching { snackbarHostState.showSnackbar(ex.localizedMessage ?: "") }
                onButtonChange()
                throw ex
            }
        }
    }

    fun onButtonChange() { uiState.value = uiState.value.copy(button = !button) }
}
