package com.zepi.social_chat_food.iraaa.ui.presentation.login

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.zepi.social_chat_food.iraaa.common.ext.isValidEmail
import com.zepi.social_chat_food.iraaa.core.room.profile.Profile
import com.zepi.social_chat_food.iraaa.core.room.profile.ProfileDao
import com.zepi.social_chat_food.iraaa.model.service.AccountService
import com.zepi.social_chat_food.iraaa.model.service.FirestoreService
import com.zepi.social_chat_food.iraaa.model.service.LogService
import com.zepi.social_chat_food.iraaa.ui.presentation.QChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val profileDao: ProfileDao,
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
                getCompletedState(restartApp)
            } catch (ex: FirebaseAuthException) {
                launchCatching {
                    onShowSnackbar(ex.localizedMessage ?: "", "")
                    onButtonChange()
                }
                throw ex
            }
        }
    }

    private fun getCompletedState(restartApp: () -> Unit,) {
        launchCatching {
            val user = firestoreService.getUser(accountService.currentUserId)
            if (user != null) {
                profileDao.insert(
                    Profile(
                        namedb = user.name,
                        surnamedb = user.surname,
                        genderdb = user.gender,
                        birthdaydb = user.birthday,
                        photodb = user.photo,
                    )
                )
                restartApp()
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
