package com.zepi.social_chat_food.ui.presentation.register

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthException
import com.zepi.social_chat_food.core.constants.Cons.DEFAULT_LANGUAGE_CODE
import com.zepi.social_chat_food.core.constants.EditType.PROFILE
import com.zepi.social_chat_food.core.datastore.EditTypeRepository
import com.zepi.social_chat_food.core.datastore.LangRepository
import com.zepi.social_chat_food.iraaa.common.ext.isValidEmail
import com.zepi.social_chat_food.iraaa.common.ext.isValidPassword
import com.zepi.social_chat_food.iraaa.common.ext.passwordMatches
import com.zepi.social_chat_food.model.Feedback
import com.zepi.social_chat_food.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.zepi.social_chat_food.model.service.AccountService
import com.zepi.social_chat_food.model.service.FirestoreService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.ui.presentation.QChatViewModel

@HiltViewModel
class RegisterViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val langRepository: LangRepository,
    private val editTypeRepository: EditTypeRepository,
): QChatViewModel(logService) {
    var uiState = mutableStateOf(RegisterUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val button
        get() = uiState.value.button


    private val _lang = mutableStateOf<String?>(null)
    val lang: String?
        get() = _lang.value

    init {
        launchCatching {
            langRepository.readLangState().collect {
                _lang.value = it
            }
        }
    }


    fun onEmailChange(newValue: String) { uiState.value = uiState.value.copy(email = newValue) }
    fun onPasswordChange(newValue: String) { uiState.value = uiState.value.copy(password = newValue) }
    fun onRepeatPasswordChange(newValue: String) { uiState.value = uiState.value.copy(repeatPassword = newValue) }


    fun onRegisterClick(
        onShowSnackbar: suspend (String, String?) -> Boolean,
        navigateAndPopUpRegisterToEdit: () -> Unit,
        email_error: String,
        password_error: String,
        password_match_error: String,
    ) {
        onButtonChange()
        if (!email.isValidEmail()) {
            launchCatching { onShowSnackbar(email_error, "") }
            onButtonChange()
            return
        }

        if (!password.isValidPassword()) {
            launchCatching { onShowSnackbar(password_error, "") }
            onButtonChange()
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            launchCatching { onShowSnackbar(password_match_error, "") }
            onButtonChange()
            return
        }

        launchCatching {
            try {
                accountService.linkAccount(email, password)
                firestoreService.saveUser(
                    User(
                        language = _lang.value ?: DEFAULT_LANGUAGE_CODE,
                        online = true,
                        uid = accountService.currentUserId,
                        date = Timestamp.now()
                    )
                )
                firestoreService.saveLang(
                    Feedback(
                        _lang.value ?: DEFAULT_LANGUAGE_CODE
                    )
                )
                editTypeRepository.saveEditTypeState(PROFILE)
                navigateAndPopUpRegisterToEdit()
            } catch (ex: FirebaseAuthException) {
                launchCatching { onShowSnackbar(ex.localizedMessage ?: "", "") }
                onButtonChange()
                throw ex
            }
        }
    }

    fun onButtonChange() { uiState.value = uiState.value.copy(button = !button) }
}
