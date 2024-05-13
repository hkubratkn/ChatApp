package com.zepi.social_chat_food.iraaa.ui.presentation.register

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.zepi.social_chat_food.iraaa.model.service.AccountService
import com.zepi.social_chat_food.iraaa.model.service.FirestoreService
import com.zepi.social_chat_food.iraaa.model.service.LogService
import com.zepi.social_chat_food.iraaa.ui.presentation.QChatViewModel

@HiltViewModel
class RegisterViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    //private val langRepository: LangRepository,
    //private val editTypeRepository: EditTypeRepository,
    //private val profileDao: ProfileDao
): QChatViewModel(logService){
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
          //  langRepository.readLangState().collect {
            //    _lang.value = it
            //}
        }
    }

    fun onEmailChange(newValue: String) { uiState.value = uiState.value.copy(email = newValue) }
    fun onPasswordChange(newValue: String) { uiState.value = uiState.value.copy(password = newValue) }
    fun onRepeatPasswordChange(newValue: String) { uiState.value = uiState.value.copy(repeatPassword = newValue) }
    fun onButtonChange() { uiState.value = uiState.value.copy(button = !button) }

}

   /** fun onRegisterClick(openAndPopUp: (String, String) -> Unit) {
        onButtonChange()
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            onButtonChange()
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            onButtonChange()
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            onButtonChange()
            return
        }

        launchCatching {
            try {
                accountService.linkAccount(email, password)
                firestoreService.save(
                    com.kapirti.ira.model.User(
                        language = _lang.value ?: DEFAULT_LANGUAGE_CODE,
                        online = true,
                        uid = accountService.currentUserId,
                        date = Timestamp.now()
                    )
                )
                firestoreService.saveLang(
                    com.kapirti.ira.model.Feedback(
                        _lang.value ?: DEFAULT_LANGUAGE_CODE
                    )
                )
                profileDao.insert(Profile())
                editTypeRepository.saveEditTypeState(PROFILE)
                openAndPopUp(EDIT_SCREEN, REGISTER_SCREEN)
            } catch (ex: FirebaseAuthException) {
                SnackbarManager.showMessageString(ex.localizedMessage ?: "")
                onButtonChange()
                throw ex
            }
        }
    }
}*/
