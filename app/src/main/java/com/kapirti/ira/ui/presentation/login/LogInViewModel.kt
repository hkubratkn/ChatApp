package com.kapirti.ira.ui.presentation.login

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.ira.common.ext.isValidEmail
import com.kapirti.ira.core.datastore.LangRepository
import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val langRepository: LangRepository,
    logService: LogService,
): ZepiViewModel(logService) {
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
                val user = firestoreService.getUser(accountService.currentUserId)
                langRepository.saveLangState(
                    user?.let { it.language } ?:
                    Locale.getDefault().getDisplayLanguage()
                )
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
