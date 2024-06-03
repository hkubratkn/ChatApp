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
    //private val onBoardingRepository: OnBoardingRepository,
    private val accountService: AccountService,
    logService: LogService,
): PomodoroViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUpSplashToHome: () -> Unit, openAndPopUpSplashToLogin: () -> Unit,) {
        showError.value = false
        checkState(openAndPopUpSplashToHome, openAndPopUpSplashToLogin)
    }

    private fun checkState(openAndPopUpSplashToPomodoro: () -> Unit, openAndPopUpSplashToLogin: () -> Unit,) {
        launchCatching(snackbar = false) {
            try {
                if (accountService.hasUser) {
                    openAndPopUpSplashToPomodoro()
                } else {
                    openAndPopUpSplashToLogin()

               // onBoardingRepository.readOnBoardingState().collect { completed ->
                 //   openAndPopUp(ZepiDestinations.HOME_ROUTE, ZepiDestinations.SPLASH_ROUTE)

                    /** if (completed) {
                        openAndPopUp(QChatDestinations.HOME_ROUTE, QChatDestinations.SPLASH_ROUTE)
                    } else {
                        openAndPopUp(WELCOME_SCREEN, SPLASH_SCREEN)
                    }*/
                }
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
        }
    }
}
