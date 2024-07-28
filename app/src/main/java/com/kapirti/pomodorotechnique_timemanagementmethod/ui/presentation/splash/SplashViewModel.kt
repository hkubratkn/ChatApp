package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.splash

import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.ConfigurationService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService,
): PomodoroViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUpSplashToHome: () -> Unit) {

        showError.value = false
        if (accountService.hasUser) openAndPopUpSplashToHome()
        else createAnonymousAccount(openAndPopUpSplashToHome)
    }

    private fun createAnonymousAccount(openAndPopUpSplashToHome: () -> Unit) {
        launchCatching() {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUpSplashToHome()
        }
    }
}

// onBoardingRepository.readOnBoardingState().collect { completed ->
//   openAndPopUp(ZepiDestinations.HOME_ROUTE, ZepiDestinations.SPLASH_ROUTE)

/** if (completed) {
openAndPopUp(QChatDestinations.HOME_ROUTE, QChatDestinations.SPLASH_ROUTE)
} else {
openAndPopUp(WELCOME_SCREEN, SPLASH_SCREEN)
}*/
