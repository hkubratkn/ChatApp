package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.register

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.isValidEmail
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.isValidPassword
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.passwordMatches
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Feedback
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.model.ggoo.SignInResult
import com.kapirti.pomodorotechnique_timemanagementmethod.model.ggoo.SignInState
import com.kapirti.pomodorotechnique_timemanagementmethod.model.ggoo.UserData
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.PROFILE
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class RegisterViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val countryRepository: CountryRepository,
    private val editTypeRepository: EditTypeRepository,
): PomodoroViewModel(logService) {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()





    val countryValue = Locale.getDefault().getDisplayCountry()

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
                    country = countryValue,
                    online = true,
                    date = Timestamp.now()
                )
            )

            firestoreService.saveCountry(
                Feedback(
                    countryValue
                )
            )
            editTypeRepository.saveEditTypeState(PROFILE)
            countryRepository.saveCountryState(countryValue)
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
                        country = countryValue,
                        online = true,
                        date = Timestamp.now()
                    )
                )
                firestoreService.saveCountry(
                    Feedback(
                        countryValue
                    )
                )
                editTypeRepository.saveEditTypeState(PROFILE)
                countryRepository.saveCountryState(countryValue)
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

