package com.kapirti.ira.ui.presentation.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.ira.common.ext.isValidEmail
import com.kapirti.ira.core.datastore.LangRepository
import com.kapirti.ira.iraaa.ggoo.SignInResult
import com.kapirti.ira.iraaa.ggoo.SignInState
import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val langRepository: LangRepository,
    logService: LogService,
): ZepiViewModel(logService) {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }
    fun googleLoginDone(restartApp: () -> Unit,) {
        launchCatching {
            val user = firestoreService.getUser(accountService.currentUserId)
            langRepository.saveLangState(
                user?.let { it.language } ?: Locale.getDefault().getDisplayLanguage()
            )
            resetState()
            restartApp()
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }









    //try

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
        restartApp: () -> Unit, snackbarHostState: SnackbarHostState,
        emailError: String, emptyPasswordError: String
        ) {
        onButtonChange()
        if (!email.isValidEmail()) {
            launchCatching {
                snackbarHostState.showSnackbar(emailError)
                onButtonChange()
            }
            return
        }

        if (password.isBlank()) {
            launchCatching {
                snackbarHostState.showSnackbar(emptyPasswordError)
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
                    snackbarHostState.showSnackbar(ex.localizedMessage ?: "")
                    onButtonChange()
                }
                throw ex
            }
        }
    }

    fun onForgotPasswordClick(
        snackbarHostState: SnackbarHostState,
        emailError: String, recoveryEmailSent: String
    ) {
        if (!email.isValidEmail()) {
            launchCatching {
                snackbarHostState.showSnackbar(emailError)
            }
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            snackbarHostState.showSnackbar(recoveryEmailSent)
        }
    }
}
