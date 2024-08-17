package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.splash

import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.OnBoardingRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.ConfigurationService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    private val onBoardingRepository: OnBoardingRepository,
    configurationService: ConfigurationService,
    logService: LogService,
): PomodoroViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(
        navigateAndPopUpSplashToTimeline: () -> Unit,
        navigateAndPopUpSplashToWelcome: () -> Unit,
    ) {

        showError.value = false
        if (accountService.hasUser){
            launchCatching {
                onBoardingRepository.readOnBoardingState().collect {
                    if (it) {
                        navigateAndPopUpSplashToTimeline()
                    } else {
                        navigateAndPopUpSplashToWelcome()
                    }
                }
            }
        } else {
            createAnonymousAccount(navigateAndPopUpSplashToTimeline = navigateAndPopUpSplashToTimeline,
                navigateAndPopUpSplashToWelcome = navigateAndPopUpSplashToWelcome)
        }
    }

    private fun createAnonymousAccount(
        navigateAndPopUpSplashToTimeline: () -> Unit,
        navigateAndPopUpSplashToWelcome: () -> Unit,
    ) {
        launchCatching() {
            try {
                accountService.createAnonymousAccount()
                onBoardingRepository.readOnBoardingState().collect{
                    if (it) {
                        navigateAndPopUpSplashToTimeline()
                    } else {
                        navigateAndPopUpSplashToWelcome()
                    }
                }
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
        }
    }
}
